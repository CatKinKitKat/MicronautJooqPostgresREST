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
	public Mono<MutableHttpResponse<WebshopModel[]>> getWebshops(String[] q,
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
	 * Create webshop mono.
	 *
	 * @param webshopModel the webshop model
	 * @return the mono
	 */
	@Post(value = "/", produces = "application/json", consumes = "application/json")
	public Mono<MutableHttpResponse<String>> createWebshop(@Body WebshopModel webshopModel) {

		return repositoryService.createWebshop(webshopModel);
	}

	/**
	 * Add email mono.
	 *
	 * @param handle     the handle
	 * @param emailModel the email model
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
	 * @param handle     the handle
	 * @param emailModel the email model
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
	 * @param handle       the handle
	 * @param webshopModel the webshop model
	 * @return the mono
	 */
	@Put(value = "/{handle}", produces = "application/json", consumes = "application/json")
	public Mono<MutableHttpResponse<String>> updateWebshop(String handle, @Body WebshopModel webshopModel) {

		return repositoryService.updateWebshop(handle, webshopModel);
	}

	/**
	 * Update webshop mono.
	 *
	 * @param handle      the handle
	 * @param handleModel the handle model
	 * @return the mono
	 */
	@Put(value = "/{handle}/handle", produces = "application/json", consumes = "application/json")
	public Mono<MutableHttpResponse<String>> updateWebshop(String handle, @Body HandleModel handleModel) {

		if (!handleModel.isValid()) {
			return Mono.just(HttpResponse.badRequest());
		}

		return repositoryService.updateWebshopHandle(handle, handleModel.getHandle());
	}

	/**
	 * Update webshop mono.
	 *
	 * @param handle   the handle
	 * @param urlModel the url model
	 * @return the mono
	 */
	@Put(value = "/{handle}/url", produces = "application/json", consumes = "application/json")
	public Mono<MutableHttpResponse<String>> updateWebshop(String handle, @Body UrlModel urlModel) {

		if (!urlModel.isValid()) {
			return Mono.just(HttpResponse.badRequest());
		}

		return repositoryService.updateWebshopUrl(handle, urlModel.getUrl());
	}

	/**
	 * Update webshop mono.
	 *
	 * @param handle            the handle
	 * @param interestRateModel the interest rate model
	 * @return the mono
	 */
	@Put(value = "/{handle}/interestRate", produces = "application/json", consumes = "application/json")
	public Mono<MutableHttpResponse<String>> updateWebshop(String handle, @Body InterestRateModel interestRateModel) {

		if (!interestRateModel.isValid()) {
			return Mono.just(HttpResponse.badRequest());
		}

		return repositoryService.updateWebshopInterestRate(handle, interestRateModel.getInterestRate());
	}

	/**
	 * Update webshop mono.
	 *
	 * @param handle             the handle
	 * @param serviceLevelsModel the webshop service levels model
	 * @return the mono
	 */
	@Put(value = "/{handle}/serviceLevels", produces = "application/json", consumes = "application/json")
	public Mono<MutableHttpResponse<String>> updateWebshop(String handle, @Body ServiceLevelsModel serviceLevelsModel) {

		if (!serviceLevelsModel.isValid()) {
			return Mono.just(HttpResponse.badRequest());
		}

		return repositoryService.updateWebshopServiceLevels(handle, serviceLevelsModel);
	}

	/**
	 * Update webshop mono.
	 *
	 * @param handle        the handle
	 * @param settingsModel the webshop settings model
	 * @return the mono
	 */
	@Put(value = "/{handle}/settings", produces = "application/json", consumes = "application/json")
	public Mono<MutableHttpResponse<String>> updateWebshop(String handle, @Body SettingsModel settingsModel) {

		if (!settingsModel.isValid()) {
			return Mono.just(HttpResponse.badRequest());
		}

		return repositoryService.updateWebshopSettings(handle, settingsModel);
	}
}


