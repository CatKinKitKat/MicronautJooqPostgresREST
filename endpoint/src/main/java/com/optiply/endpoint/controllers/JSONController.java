package com.optiply.endpoint.controllers;


import com.optiply.endpoint.controllers.shared.BaseController;
import com.optiply.endpoint.models.*;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.validation.Validated;
import org.jooq.Condition;
import org.jooq.SortField;
import reactor.core.publisher.Mono;

/**
 * The type Endpoint controller.
 */
@Validated
@Controller("/")
public class JSONController extends BaseController {

	/**
	 * Gets webshops.
	 *
	 * @param q the q
	 * @param s the s
	 * @param o the o
	 * @return the webshops
	 */
	@Get(value = "/find/{q*}", produces = "application/json", consumes = "application/json")
	public Mono<MutableHttpResponse<WebshopBodyModel[]>> getWebshops(String[] q,
	                                                                 @Nullable @QueryValue String s,
	                                                                 @Nullable @QueryValue String o) {

		if (s == null || s.isEmpty()) {
			s = "handle";
		}

		if (o == null || o.isEmpty()) {
			o = "asc";
		}

		SortField<?> sortField = sortParserWebshop(s, o);
		Condition condition = parseParamsWebshop(q);


		return repositoryService.getWebshops(condition, sortField);

	}

	/**
	 * Gets all webshops.
	 *
	 * @return the all webshops
	 */
	@Get(value = "/all", produces = "application/json", consumes = "application/json")
	public Mono<MutableHttpResponse<WebshopBodyModel[]>> getAllWebshops() {

		return repositoryService.getAllWebshops();

	}

	/**
	 * Gets webshop.
	 *
	 * @param handle the handle
	 * @return the webshop
	 */
	@Get(value = "/{handle}", produces = "application/json", consumes = "application/json")
	public Mono<MutableHttpResponse<WebshopSimpleModel>> getWebshop(String handle) {

		return repositoryService.getWebshop(handle);

	}

	/**
	 * Gets webshop emails.
	 *
	 * @param handle the handle
	 * @return the webshop emails
	 */
	@Get(value = "/{handle}/emails", produces = "application/json", consumes = "application/json")
	public Mono<MutableHttpResponse<WebshopEmailsModel>> getWebshopEmails(String handle) {

		return repositoryService.getWebshopEmails(handle);
	}

	/**
	 * Gets webshop settings.
	 *
	 * @param handle the handle
	 * @return the webshop settings
	 */
	@Get(value = "/{handle}/settings", produces = "application/json", consumes = "application/json")
	public Mono<MutableHttpResponse<WebshopSettingsModel>> getWebshopSettings(String handle) {

		return repositoryService.getWebshopSettings(handle);

	}

	/**
	 * Create webshop simple mono.
	 *
	 * @param webshopModel the webshop model
	 * @return the mono
	 */
	@Post(value = "/simple", produces = "application/json", consumes = "application/json")
	public Mono<MutableHttpResponse<String>> createWebshopSimple(@Body WebshopSimpleModel webshopModel) {

		if (!webshopModel.isValid()) {
			return Mono.just(HttpResponse.badRequest());
		}

		return repositoryService.createWebshopSimple(webshopModel);
	}

	/**
	 * Create webshop mono.
	 *
	 * @param webshopModel the webshop model
	 * @return the mono
	 */
	@Post(value = "/", produces = "application/json", consumes = "application/json")
	public Mono<MutableHttpResponse<String>> createWebshop(@Body WebshopBodyModel webshopModel) {

		return repositoryService.createWebshop(webshopModel);
	}

	/**
	 * Add email mono.
	 *
	 * @param handle the handle
	 * @return the mono
	 */
	@Post(value = "/email/{handle}", produces = "application/json", consumes = "application/json")
	public Mono<MutableHttpResponse<String>> addEmail(String handle, @Body EmailModel emailModel) {

		if (!emailModel.isValid()) {
			return Mono.just(HttpResponse.badRequest());
		}

		return repositoryService.addEmail(handle, emailModel.getEmail());
	}

	/**
	 * Remove email mono.
	 *
	 * @param handle the handle
	 * @return the mono
	 */
	@Delete(value = "/email/{handle}", produces = "application/json", consumes = "application/json")
	public Mono<MutableHttpResponse<String>> removeEmail(String handle, @Body EmailModel emailModel) {

		if (!emailModel.isValid()) {
			return Mono.just(HttpResponse.badRequest());
		}

		return repositoryService.removeEmail(handle, emailModel.getEmail());
	}


	/**
	 * Delete webshop mono.
	 *
	 * @param handle the handle
	 * @return the mono
	 */
	@Delete(value = "/{handle}", produces = "application/json", consumes = "application/json")
	public Mono<MutableHttpResponse<Object>> deleteWebshop(String handle) {

		return repositoryService.deleteWebshop(handle);
	}


	/**
	 * Update webshop mono.
	 *
	 * @param webshopModel the webshop model
	 * @return the mono
	 */
	@Put(value = "/", produces = "application/json", consumes = "application/json")
	public Mono<MutableHttpResponse<String>> updateWebshop(@Body WebshopBodyModel webshopModel) {

		return repositoryService.updateWebshop(webshopModel);
	}

}


