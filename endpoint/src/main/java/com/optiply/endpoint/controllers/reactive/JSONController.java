package com.optiply.endpoint.controllers.reactive;


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



}
