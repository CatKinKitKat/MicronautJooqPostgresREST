package com.optiply.endpoint.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.optiply.endpoint.parsers.QueryParamParser;
import com.optiply.infrastructure.data.repositories.reactive.WebshopRepository;
import com.optiply.infrastructure.data.repositories.reactive.WebshopemailsRepository;
import io.micronaut.http.annotation.Controller;
import io.micronaut.validation.Validated;
import jakarta.inject.Inject;

@Validated
@Controller("/non-reactive")
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


}
