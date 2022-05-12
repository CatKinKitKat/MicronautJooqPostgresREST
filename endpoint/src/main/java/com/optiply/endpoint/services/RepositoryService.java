package com.optiply.endpoint.services;

import com.optiply.endpoint.models.*;
import com.optiply.infrastructure.data.models.tables.pojos.Webshop;
import com.optiply.infrastructure.data.repositories.WebshopRepository;
import com.optiply.infrastructure.data.repositories.WebshopemailsRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import jakarta.inject.Inject;
import lombok.extern.java.Log;
import org.jooq.Condition;
import org.jooq.SortField;
import reactor.core.publisher.Mono;

/**
 * The type Repository service.
 */
@Log
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
	 * Gets webshops.
	 *
	 * @param condition the condition
	 * @param sortField the sort fields
	 * @return the webshops
	 */
	public Mono<MutableHttpResponse<WebshopModel[]>> getWebshops(Condition condition, SortField<?> sortField) {

		return webshopRepository.findVarious(condition, sortField).collectList().flatMap(webshops -> {
			log.info("webshop found");
			WebshopModel[] webshopModels = new WebshopModel[webshops.size()];
			for (Webshop webshop : webshops) {
				webshopModels[webshops.indexOf(webshop)] = new WebshopModel(webshop);
			}
			return Mono.just(webshopModels);
		}).flatMap(webshopBodyModels -> Mono.just(HttpResponse.ok(webshopBodyModels)));

	}

	/**
	 * Gets webshop.
	 *
	 * @param handle the handle
	 * @return the webshop
	 */
	public Mono<MutableHttpResponse<WebshopSimpleModel>> getWebshop(String handle) {

		return webshopRepository.find(handle).flatMap(webshop -> {
					log.info("webshop found");
					return Mono.just(new WebshopSimpleModel(webshop));
				}).flatMap(webshopModel -> Mono.just(HttpResponse.ok(webshopModel)))
				.switchIfEmpty(Mono.just(HttpResponse.notFound())).onErrorReturn(HttpResponse.serverError());

	}

	/**
	 * Gets webshop emails.
	 *
	 * @param handle the handle
	 * @return the webshop emails
	 */
	public Mono<MutableHttpResponse<WebshopEmailsModel>> getWebshopEmails(String handle) {

		return Mono.from(webshopemailsRepository.findEmails(handle).collectList()).flatMap(emails -> {
					if (emails.isEmpty()) {
						log.info("emails for webshop" + handle + " not found");
						return Mono.empty();
					}
					log.info("emails for webshop" + handle + " found\n" + "emails: " + emails);
					return Mono.just(new WebshopEmailsModel(handle, emails));
				}).flatMap(webshopEmailsModel -> Mono.just(HttpResponse.ok(webshopEmailsModel)))
				.switchIfEmpty(Mono.just(HttpResponse.notFound())).onErrorReturn(HttpResponse.serverError());
	}

	/**
	 * Gets webshop settings.
	 *
	 * @param handle the handle
	 * @return the webshop settings
	 */
	public Mono<MutableHttpResponse<WebshopSettingsModel>> getWebshopSettings(String handle) {

		return webshopRepository.find(handle).flatMap(webshop -> {
					log.info("webshop found");
					return Mono.just(new WebshopSettingsModel(webshop));
				}).flatMap(webshopSettingsModel -> Mono.just(HttpResponse.ok(webshopSettingsModel)))
				.switchIfEmpty(Mono.just(HttpResponse.notFound())).onErrorReturn(HttpResponse.serverError());

	}

	/**
	 * Create webshop mono.
	 *
	 * @param webshopModel the webshop model
	 * @return the mono
	 */
	public Mono<MutableHttpResponse<String>> createWebshop(WebshopModel webshopModel) {

		return webshopRepository.create(
						webshopModel.getHandle(), webshopModel.getUrl(),
						webshopModel.getServiceLevelA(), webshopModel.getServiceLevelB(), webshopModel.getServiceLevelC(),
						webshopModel.getInterestRate(), webshopModel.getCurrency(),
						webshopModel.getRunJobs(), webshopModel.getMultiSupplier()
				).flatMap(response -> {
					if (response) {
						log.info("webshop created");
						return Mono.just(HttpResponse.created("Webshop created."));
					}
					log.info("webshop not created");
					return Mono.empty();
				})
				.switchIfEmpty(Mono.just(HttpResponse.badRequest())).onErrorReturn(HttpResponse.serverError());
	}

	/**
	 * Add email mono.
	 *
	 * @param handle the handle
	 * @param email  the email
	 * @return the mono
	 */
	public Mono<MutableHttpResponse<String>> addEmail(String handle, String email) {

		return webshopemailsRepository.create(handle, email).flatMap(response -> {
					if (response) {
						log.info("email added");
						return Mono.just(HttpResponse.created("Email added."));
					}
					log.info("email not added");
					return Mono.empty();
				})
				.switchIfEmpty(Mono.just(HttpResponse.badRequest())).onErrorReturn(HttpResponse.serverError());
	}

	/**
	 * Remove email mono.
	 *
	 * @param handle the handle
	 * @param email  the email
	 * @return the mono
	 */
	public Mono<MutableHttpResponse<String>> removeEmail(String handle, String email) {

		return webshopemailsRepository.delete(handle, email).flatMap(response -> {
					if (response) {
						log.info("email removed");
						return Mono.just(HttpResponse.created("Email removed."));
					}
					log.info("email not removed");
					return Mono.empty();
				})
				.switchIfEmpty(Mono.just(HttpResponse.badRequest())).onErrorReturn(HttpResponse.serverError());
	}


	/**
	 * Delete webshop mono.
	 *
	 * @param handle the handle
	 * @return the mono
	 */
	public Mono<MutableHttpResponse<Object>> deleteWebshop(String handle) {

		return webshopRepository.deleteWebshop(handle).flatMap(response -> {
					if (response) {
						log.info("webshop deleted");
						return Mono.just(HttpResponse.noContent());
					}
					log.info("webshop not deleted");
					return Mono.empty();
				})
				.switchIfEmpty(Mono.just(HttpResponse.badRequest()));
	}


	/**
	 * Update webshop mono.
	 *
	 * @param webshopModel the webshop model
	 * @return the mono
	 */
	public Mono<MutableHttpResponse<String>> updateWebshop(String handle, WebshopModel webshopModel) {

		return webshopRepository.updateWebshop(handle, webshopModel.getHandle(),
						webshopModel.getUrl(), webshopModel.getServiceLevelA(),
						webshopModel.getServiceLevelB(), webshopModel.getServiceLevelC(),
						webshopModel.getInterestRate(), webshopModel.getCurrency(),
						webshopModel.getRunJobs(), webshopModel.getMultiSupplier()
				).flatMap(response -> {
					if (response) {
						log.info("webshop updated");
						return Mono.just(HttpResponse.ok("Webshop updated."));
					}
					log.info("webshop not updated");
					return Mono.empty();
				})
				.switchIfEmpty(Mono.just(HttpResponse.notFound())).onErrorReturn(HttpResponse.serverError());
	}

	/**
	 * Update webshop handle mono.
	 *
	 * @param handle    the handle
	 * @param newHandle the new handle
	 * @return the mono
	 */
	public Mono<MutableHttpResponse<String>> updateWebshopHandle(String handle, String newHandle) {

		return webshopRepository.updateWebshopHandle(handle, newHandle)
				.flatMap(response -> {
					if (response) {
						log.info("webshop updated");
						return Mono.just(HttpResponse.ok("Webshop updated."));
					}
					log.info("webshop not updated");
					return Mono.empty();
				})
				.switchIfEmpty(Mono.just(HttpResponse.notFound())).onErrorReturn(HttpResponse.serverError());
	}

	/**
	 * Update webshop url mono.
	 *
	 * @param handle the handle
	 * @param url    the url
	 * @return the mono
	 */
	public Mono<MutableHttpResponse<String>> updateWebshopUrl(String handle, String url) {

		return webshopRepository.updateWebshopUrl(handle, url)
				.flatMap(response -> {
					if (response) {
						log.info("webshop updated");
						return Mono.just(HttpResponse.ok("Webshop updated."));
					}
					log.info("webshop not updated");
					return Mono.empty();
				})
				.switchIfEmpty(Mono.just(HttpResponse.notFound())).onErrorReturn(HttpResponse.serverError());
	}


	/**
	 * Update webshop interest rate mono.
	 *
	 * @param handle       the handle
	 * @param interestRate the interest rate
	 * @return the mono
	 */
	public Mono<MutableHttpResponse<String>> updateWebshopInterestRate(String handle, Short interestRate) {

		return webshopRepository.updateWebshopInterestRate(handle, interestRate)
				.flatMap(response -> {
					if (response) {
						log.info("webshop updated");
						return Mono.just(HttpResponse.ok("Webshop updated."));
					}
					log.info("webshop not updated");
					return Mono.empty();
				})
				.switchIfEmpty(Mono.just(HttpResponse.notFound())).onErrorReturn(HttpResponse.serverError());
	}


	/**
	 * Update webshop settings mono.
	 *
	 * @param settingsModel the webshop settings model
	 * @return the mono
	 */
	public Mono<MutableHttpResponse<String>> updateWebshopSettings(String handle, SettingsModel settingsModel) {

		return webshopRepository.updateWebshopSettings(handle,
						settingsModel.getCurrency(), settingsModel.getRunJobs(),
						settingsModel.getMultiSupplier())
				.flatMap(response -> {
					if (response) {
						log.info("webshop updated");
						return Mono.just(HttpResponse.ok("Webshop updated."));
					}
					log.info("webshop not updated");
					return Mono.empty();
				})
				.switchIfEmpty(Mono.just(HttpResponse.notFound())).onErrorReturn(HttpResponse.serverError());
	}


	/**
	 * Update webshop service levels mono.
	 *
	 * @param serviceLevelsModel the webshop service levels model
	 * @return the mono
	 */
	public Mono<MutableHttpResponse<String>> updateWebshopServiceLevels(String handle, ServiceLevelsModel serviceLevelsModel) {

		return webshopRepository.updateWebshopServiceLevels(
						handle, serviceLevelsModel.getServiceLevelA(),
						serviceLevelsModel.getServiceLevelB(), serviceLevelsModel.getServiceLevelC())
				.flatMap(response -> {
					if (response) {
						log.info("webshop updated");
						return Mono.just(HttpResponse.ok("Webshop updated."));
					}
					log.info("webshop not updated");
					return Mono.empty();
				})
				.switchIfEmpty(Mono.just(HttpResponse.notFound())).onErrorReturn(HttpResponse.serverError());
	}


}
