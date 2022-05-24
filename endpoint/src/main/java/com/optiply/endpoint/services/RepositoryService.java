package com.optiply.endpoint.services;

import com.optiply.endpoint.models.*;
import com.optiply.infrastructure.data.repositories.WebshopRepository;
import com.optiply.infrastructure.data.repositories.WebshopemailsRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import jakarta.inject.Inject;
import org.jooq.Condition;
import org.jooq.SortField;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

/**
 * Repository service (middleware to remove business logic from controller).
 */
public class RepositoryService {


	/**
	 * The Webshop repository.
	 */
	@Inject
	public WebshopRepository webshopRepository;
	/**
	 * The Webshopemails repository.
	 */
	@Inject
	public WebshopemailsRepository webshopemailsRepository;

	/**
	 * Finds webshops via a conditionally defined query sorted by a defined sort field.
	 *
	 * @param condition the condition
	 * @param sortField the sort field
	 * @return the webshops
	 */
	public Mono<MutableHttpResponse<List<WebshopModel>>> getWebshops(Condition condition, SortField<?> sortField) {

		return webshopRepository.findVarious(condition, sortField).flatMapSequential(this::getWebshopPriv)
				.collectList().flatMap(webshops -> {
					if (webshops.isEmpty()) {
						return Mono.empty();
					}
					return Mono.just(HttpResponse.ok(webshops));
				}).switchIfEmpty(Mono.just(HttpResponse.notFound()))
				.onErrorReturn(HttpResponse.serverError());


	}

	/**
	 * Helper method for getWebshops(), works exaclty like the getWebshop() method, but without the
	 * HttpResponse wrapping.
	 *
	 * @param handle the handle
	 * @return the webshop
	 */
	private Mono<WebshopModel> getWebshopPriv(String handle) {

		return webshopRepository.find(handle).flatMap(webshop -> Mono.just(new WebshopModel(webshop)))
				.flatMap(webshopModel -> webshopemailsRepository.findEmails(handle)
						.flatMap(webshopemails -> {
							webshopModel.setEmails(webshopemails);
							return Mono.just(webshopModel);
						}).flatMap(Mono::just));
	}

	/**
	 * Gets webshop by handle.
	 *
	 * @param handle the handle
	 * @return the webshop
	 */
	public Mono<MutableHttpResponse<WebshopModel>> getWebshop(String handle) {

		return webshopRepository.find(handle).flatMap(webshop -> Mono.just(new WebshopModel(webshop)))
				.flatMap(webshopModel -> webshopemailsRepository.findEmails(handle)
						.flatMap(webshopemails -> {
							webshopModel.setEmails(webshopemails);
							return Mono.just(webshopModel);
						}).flatMap(webshopModelWithEmails ->
								Mono.just(HttpResponse.ok(webshopModelWithEmails))))
				.switchIfEmpty(Mono.just(HttpResponse.notFound()))
				.onErrorReturn(HttpResponse.serverError());
	}

	/**
	 * Gets webshop settings by handle.
	 *
	 * @param handle the handle
	 * @return the webshop settings
	 */
	public Mono<MutableHttpResponse<WebshopSettingsModel>> getWebshopSettings(String handle) {

		return webshopRepository.find(handle)
				.flatMap(webshop ->
						Mono.just(new WebshopSettingsModel(webshop)))
				.flatMap(webshopSettingsModel ->
						Mono.just(HttpResponse.ok(webshopSettingsModel)))
				.switchIfEmpty(Mono.just(HttpResponse.notFound()))
				.onErrorReturn(HttpResponse.serverError());

	}

	/**
	 * Creates a single webshop via a webshop full model (webshop model + settings).
	 *
	 * @param webshopModel the webshop model
	 * @return the mono
	 */
	public Mono<MutableHttpResponse<String>> createWebshop(WebshopFullModel webshopModel) {

		if (!webshopModel.isValid()) {
			return Mono.just(HttpResponse.badRequest());
		}

		return webshopRepository.create(
						webshopModel.getHandle(), webshopModel.getUrl(),
						webshopModel.getServiceLevelA(), webshopModel.getServiceLevelB(), webshopModel.getServiceLevelC(),
						webshopModel.getInterestRate(), webshopModel.getCurrency(),
						webshopModel.getRunJobs(), webshopModel.getMultiSupplier()
				).flatMap(webshopResponse -> {
					if (webshopResponse) {
						return webshopemailsRepository.createVarious(webshopModel.getHandle(), webshopModel.getEmails())
								.flatMap(emailsResponse -> {
									if (emailsResponse) {
										return Mono.just(HttpResponse.ok("Webshop created."));
									}
									return Mono.empty();
								});
					}
					return Mono.empty();
				}).switchIfEmpty(Mono.just(HttpResponse.notFound()))
				.onErrorReturn(HttpResponse.serverError());
	}

	/**
	 * Creates various webshops via a list of webshop full models (webshop model + settings).
	 *
	 * @param webshopModels the webshop models
	 * @return the mono
	 */
	public Mono<MutableHttpResponse<String>> createWebshops(List<WebshopFullModel> webshopModels) {

		for (WebshopFullModel webshopFullModel : webshopModels) {
			if (!webshopFullModel.isValid()) {
				return Mono.just(HttpResponse.badRequest());
			}
		}

		return Mono.just(webshopModels).publishOn(Schedulers.boundedElastic()).flatMap(webshops -> {
					if (webshops.isEmpty()) {
						return Mono.empty();
					}
					for (WebshopFullModel webshopModel : webshops) {
						webshopRepository.create(
										webshopModel.getHandle(), webshopModel.getUrl(),
										webshopModel.getServiceLevelA(), webshopModel.getServiceLevelB(), webshopModel.getServiceLevelC(),
										webshopModel.getInterestRate(), webshopModel.getCurrency(),
										webshopModel.getRunJobs(), webshopModel.getMultiSupplier()
								).subscribeOn(Schedulers.boundedElastic())
								.then(webshopemailsRepository.createVarious(webshopModel.getHandle(), webshopModel.getEmails()))
								.subscribe();
					}
					return Mono.just(HttpResponse.ok("Webshops created."));
				}).switchIfEmpty(Mono.just(HttpResponse.badRequest()))
				.onErrorReturn(HttpResponse.serverError());

	}

	/**
	 * Deletes a single webshop by handle.
	 *
	 * @param handle the handle
	 * @return the mono
	 */
	public Mono<MutableHttpResponse<Object>> deleteWebshop(String handle) {

		return webshopRepository.deleteWebshop(handle).flatMap(response -> {
					if (response) {
						return Mono.just(HttpResponse.noContent());
					}
					return Mono.empty();
				}).switchIfEmpty(Mono.just(HttpResponse.badRequest()))
				.onErrorReturn(HttpResponse.serverError());
	}


	/**
	 * Fully updates a single webshop by getting the webshop full model.
	 *
	 * @param handle       the handle
	 * @param webshopModel the webshop model
	 * @return the mono
	 */
	public Mono<MutableHttpResponse<String>> updateWebshop(String
			                                                       handle, WebshopFullModel webshopModel) {

		return webshopRepository.updateWebshop(handle, webshopModel.getHandle(),
						webshopModel.getUrl(), webshopModel.getServiceLevelA(),
						webshopModel.getServiceLevelB(), webshopModel.getServiceLevelC(),
						webshopModel.getInterestRate(), webshopModel.getCurrency(),
						webshopModel.getRunJobs(), webshopModel.getMultiSupplier()
				).flatMap(response -> {
					if (response) {
						return webshopemailsRepository.deleteAll(webshopModel.getHandle())
								.flatMap(deleteResponse ->
										webshopemailsRepository.createVarious(webshopModel.getHandle(), webshopModel.getEmails())
												.flatMap(emailsResponse -> {
													if (emailsResponse) {
														return Mono.just(HttpResponse.ok("Webshop updated."));
													}
													return Mono.just(HttpResponse.ok("Webshop updated without emails."));
												}));
					}
					return Mono.empty();
				}).switchIfEmpty(Mono.just(HttpResponse.notFound()))
				.onErrorReturn(HttpResponse.serverError());
	}

	/**
	 * Updates a single webshop's handle.
	 *
	 * @param handle    the handle
	 * @param newHandle the new handle
	 * @return the mono
	 */
	public Mono<MutableHttpResponse<String>> updateWebshopHandle(String handle, String newHandle) {

		return webshopRepository.updateWebshopHandle(handle, newHandle)
				.flatMap(response -> {
					if (response) {
						return Mono.just(HttpResponse.ok("Webshop updated."));
					}
					return Mono.empty();
				}).switchIfEmpty(Mono.just(HttpResponse.notFound()))
				.onErrorReturn(HttpResponse.serverError());
	}

	/**
	 * Updates a single webshop's url.
	 *
	 * @param handle the handle
	 * @param url    the url
	 * @return the mono
	 */
	public Mono<MutableHttpResponse<String>> updateWebshopUrl(String handle, String url) {

		return webshopRepository.updateWebshopUrl(handle, url)
				.flatMap(response -> {
					if (response) {
						return Mono.just(HttpResponse.ok("Webshop updated."));
					}
					return Mono.empty();
				}).switchIfEmpty(Mono.just(HttpResponse.notFound()))
				.onErrorReturn(HttpResponse.serverError());
	}


	/**
	 * Updates a single webshop's interest rate.
	 *
	 * @param handle       the handle
	 * @param interestRate the interest rate
	 * @return the mono
	 */
	public Mono<MutableHttpResponse<String>> updateWebshopInterestRate(String handle, Short interestRate) {

		return webshopRepository.updateWebshopInterestRate(handle, interestRate)
				.flatMap(response -> {
					if (response) {
						return Mono.just(HttpResponse.ok("Webshop updated."));
					}
					return Mono.empty();
				}).switchIfEmpty(Mono.just(HttpResponse.notFound()))
				.onErrorReturn(HttpResponse.serverError());
	}


	/**
	 * Updates a single webshop's settings.
	 *
	 * @param handle        the handle
	 * @param settingsModel the webshop settings model
	 * @return the mono
	 */
	public Mono<MutableHttpResponse<String>> updateWebshopSettings(String handle, SettingsModel settingsModel) {

		return webshopRepository.updateWebshopSettings(handle,
						settingsModel.getCurrency(), settingsModel.getRunJobs(),
						settingsModel.getMultiSupplier())
				.flatMap(response -> {
					if (response) {
						return Mono.just(HttpResponse.ok("Webshop updated."));
					}
					return Mono.empty();
				}).switchIfEmpty(Mono.just(HttpResponse.notFound()))
				.onErrorReturn(HttpResponse.serverError());
	}


	/**
	 * Updates a single webshop's service levels.
	 *
	 * @param handle             the handle
	 * @param serviceLevelsModel the webshop service levels model
	 * @return the mono
	 */
	public Mono<MutableHttpResponse<String>> updateWebshopServiceLevels
	(String handle, ServiceLevelsModel serviceLevelsModel) {

		return webshopRepository.updateWebshopServiceLevels(
						handle, serviceLevelsModel.getServiceLevelA(),
						serviceLevelsModel.getServiceLevelB(), serviceLevelsModel.getServiceLevelC())
				.flatMap(response -> {
					if (response) {
						return Mono.just(HttpResponse.ok("Webshop updated."));
					}
					return Mono.empty();
				}).switchIfEmpty(Mono.just(HttpResponse.notFound()))
				.onErrorReturn(HttpResponse.serverError());
	}


	/**
	 * Updates a single webshop's emails.
	 *
	 * @param handle      the handle
	 * @param emailsModel the emails model
	 * @return the mono
	 */
	public Mono<MutableHttpResponse<String>> updateWebshopEmails(String handle, EmailListModel emailsModel) {

		return webshopemailsRepository.updateWebshopEmails(handle, emailsModel.getEmails())
				.flatMap(response -> {
					if (response) {
						return Mono.just(HttpResponse.ok("Webshop emails updated."));
					}
					return Mono.empty();
				}).switchIfEmpty(Mono.just(HttpResponse.notFound()))
				.onErrorReturn(HttpResponse.serverError());
	}
}
