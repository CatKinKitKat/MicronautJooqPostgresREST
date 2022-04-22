package com.optiply.endpoint.controllers;


import com.optiply.endpoint.controllers.shared.BaseController;
import com.optiply.endpoint.models.WebshopBodyModel;
import com.optiply.endpoint.models.WebshopEmailsModel;
import com.optiply.endpoint.models.WebshopSettingsModel;
import com.optiply.endpoint.models.WebshopSimpleModel;
import com.optiply.infrastructure.data.models.tables.pojos.Webshop;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.validation.Validated;
import lombok.extern.java.Log;
import org.jooq.Condition;
import org.jooq.SortField;
import reactor.core.publisher.Mono;

/**
 * The type Endpoint controller.
 */
@Log
@Validated
@Controller("/")
public class EndpointController extends BaseController {

	/**
	 * Gets webshops.
	 *
	 * @param q the q
	 * @param s the s
	 * @param o the o
	 * @return the webshops
	 */
	@Get(value = "/get", produces = "application/json", consumes = "application/json")
	public Mono<MutableHttpResponse<WebshopBodyModel[]>> getWebshops(@QueryValue String[] q, @Nullable @QueryValue String s, @Nullable @QueryValue String o) {

		if (s == null || s.isEmpty()) {
			s = "handle";
		}

		if (o == null || o.isEmpty()) {
			o = "asc";
		}

		SortField<?> sortField = sortParserWebshop(s, o);
		Condition condition = parseParamsWebshop(q);


		return webshopRepository.findVarious(condition, sortField).collectList().flatMap(webshops -> {
			log.info("webshop found");
			WebshopBodyModel[] webshopBodyModels = new WebshopBodyModel[webshops.size()];
			for (Webshop webshop : webshops) {
				webshopBodyModels[webshops.indexOf(webshop)] = new WebshopBodyModel(webshop);
			}
			return Mono.just(webshopBodyModels);
		}).flatMap(webshopBodyModels -> Mono.just(HttpResponse.ok(webshopBodyModels)));

	}

	/**
	 * Gets all webshops.
	 *
	 * @return the all webshops
	 */
	@Get(value = "/get/all", produces = "application/json", consumes = "application/json")
	public Mono<MutableHttpResponse<WebshopBodyModel[]>> getAllWebshops() {

		return webshopRepository.findAll().collectList().flatMap(webshops -> {
			log.info("webshop found");
			WebshopBodyModel[] webshopBodyModels = new WebshopBodyModel[webshops.size()];
			for (Webshop webshop : webshops) {
				webshopBodyModels[webshops.indexOf(webshop)] = new WebshopBodyModel(webshop);
			}
			return Mono.just(webshopBodyModels);
		}).flatMap(webshopBodyModels -> Mono.just(HttpResponse.ok(webshopBodyModels)));

	}

	/**
	 * Gets webshop.
	 *
	 * @param handle the handle
	 * @return the webshop
	 */
	@Get(value = "/get/{handle}", produces = "application/json", consumes = "application/json")
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
	@Get(value = "/get/{handle}/emails", produces = "application/json", consumes = "application/json")
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
	@Get(value = "/get/{handle}/settings", produces = "application/json", consumes = "application/json")
	public Mono<MutableHttpResponse<WebshopSettingsModel>> getWebshopSettings(String handle) {

		return webshopRepository.find(handle).flatMap(webshop -> {
					log.info("webshop found");
					return Mono.just(new WebshopSettingsModel(webshop));
				}).flatMap(webshopSettingsModel -> Mono.just(HttpResponse.ok(webshopSettingsModel)))
				.switchIfEmpty(Mono.just(HttpResponse.notFound())).onErrorReturn(HttpResponse.serverError());

	}

	/**
	 * Create webshop simple mono.
	 *
	 * @param webshopModel the webshop model
	 * @return the mono
	 */
	@Post(value = "/create/simple", produces = "application/json", consumes = "application/json")
	public Mono<MutableHttpResponse<String>> createWebshopSimple(@Body WebshopSimpleModel webshopModel) {

		if (!webshopModel.isValid()) {
			return Mono.just(HttpResponse.badRequest());
		}

		return webshopRepository.create(
						webshopModel.getHandle(), webshopModel.getUrl(),
						webshopModel.getA(), webshopModel.getB(), webshopModel.getC()
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
	 * Create webshop mono.
	 *
	 * @param webshopModel the webshop model
	 * @return the mono
	 */
	@Post(value = "/create", produces = "application/json", consumes = "application/json")
	public Mono<MutableHttpResponse<String>> createWebshop(@Body WebshopBodyModel webshopModel) {

		if (!webshopModel.isValid()) {
			return Mono.just(HttpResponse.badRequest());
		}

		return webshopRepository.create(
						webshopModel.getHandle(), webshopModel.getUrl(),
						webshopModel.getA(), webshopModel.getB(), webshopModel.getC(),
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
	@Post(value = "/add/email/{handle}/{email}", produces = "application/json", consumes = "application/json")
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
	@Delete(value = "/remove/email/{handle}/{email}", produces = "application/json", consumes = "application/json")
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
	@Delete(value = "/delete/{handle}", produces = "application/json", consumes = "application/json")
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
	@Put(value = "/update", produces = "application/json", consumes = "application/json")
	public Mono<MutableHttpResponse<String>> updateWebshop(@Body WebshopBodyModel webshopModel) {

		return webshopRepository.updateWebshop(
						webshopModel.getHandle(), webshopModel.getUrl(),
						webshopModel.getA(), webshopModel.getB(), webshopModel.getC(),
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

}


