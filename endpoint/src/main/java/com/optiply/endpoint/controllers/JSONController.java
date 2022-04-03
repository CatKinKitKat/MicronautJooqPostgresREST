package com.optiply.endpoint.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.optiply.endpoint.models.WebshopModel;
import com.optiply.endpoint.parsers.QueryParamParser;
import com.optiply.infrastructure.data.models.tables.pojos.Webshop;
import com.optiply.infrastructure.data.models.tables.pojos.Webshopemails;
import com.optiply.infrastructure.data.repositories.reactive.WebshopRepository;
import com.optiply.infrastructure.data.repositories.reactive.WebshopemailsRepository;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.validation.Validated;
import jakarta.inject.Inject;
import org.jooq.Condition;
import org.jooq.SortField;

import java.util.List;

/**
 * The type Json controller.
 */
@Validated
@Controller("/reactive")
public class JSONController {

	private final WebshopRepository webshopRepository;
	private final WebshopemailsRepository webshopemailsRepository;
	private final QueryParamParser queryParamParser;
	private final ObjectMapper objectMapper;

	/**
	 * Instantiates a new Json controller.
	 *
	 * @param webshopRepository the webshop repository
	 * @param queryParamParser  the query param parser
	 * @param objectMapper      the object mapper
	 */
	@Inject
	public JSONController(WebshopRepository webshopRepository,
	                      WebshopemailsRepository webshopemailsRepository,
	                      QueryParamParser queryParamParser,
	                      ObjectMapper objectMapper) {

		this.webshopRepository = webshopRepository;
		this.webshopemailsRepository = webshopemailsRepository;
		this.queryParamParser = queryParamParser;
		this.objectMapper = objectMapper;
	}

	/**
	 * Gets webshops.
	 *
	 * @return the webshops
	 */
	@Get(value = "/find", produces = "application/json", consumes = "application/json")
	public HttpResponse<WebshopModel[]> getWebshops(@QueryValue String queryParams, @Nullable String sort, @Nullable String order) {
		List<Condition> conditions = queryParamParser.parseParams(queryParams);
		SortField<?> sortBy = queryParamParser.sortParserWebshop(sort, order);
		List<Webshop> webshops;

		webshops = webshopRepository.findVarious(conditions, sortBy).collectList().block();

		if (webshops == null || webshops.isEmpty()) {
			return HttpResponse.notFound();
		}

		WebshopModel[] webshopModels = new WebshopModel[webshops.size()];

		for (Webshop w : webshops) {
			List<String> emails = webshopemailsRepository.find(w.getWebshopid()).map(Webshopemails::getAddress).collectList().block();
			WebshopModel wm = new WebshopModel();
			wm.handle = w.getHandle();
			wm.url = w.getUrl();
			wm.interestRate = w.getInterestrate();
			wm.A = w.getA();
			wm.B = w.getB();
			wm.C = w.getC();
			wm.currency = w.getCurrency();
			wm.multiSupplier = w.getMultisupply();
			wm.runJobs = w.getRunjobs();
			wm.emails = emails;
			webshopModels[webshops.indexOf(w)] = wm;
		}

		return HttpResponse.ok(webshopModels);
	}

	/**
	 * Gets webshops.
	 *
	 * @return the webshops
	 */
	@Get(value = "/find/{handle}", produces = "application/json", consumes = "application/json")
	public HttpResponse<WebshopModel> getWebshop(String handle) {
		Webshop w = webshopRepository.find(handle).block();

		if (w == null) {
			return HttpResponse.notFound();
		}

		WebshopModel wm = new WebshopModel();

		List<String> emails = webshopemailsRepository.find(w.getWebshopid()).map(Webshopemails::getAddress).collectList().block();

		wm.handle = handle;
		wm.url = w.getUrl();
		wm.interestRate = w.getInterestrate();
		wm.A = w.getA();
		wm.B = w.getB();
		wm.C = w.getC();
		wm.currency = w.getCurrency();
		wm.multiSupplier = w.getMultisupply();
		wm.runJobs = w.getRunjobs();
		wm.emails = emails;

		return HttpResponse.ok(wm);
	}




}
