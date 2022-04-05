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
import io.micronaut.http.annotation.*;
import io.micronaut.validation.Validated;
import jakarta.inject.Inject;
import org.jooq.Condition;
import org.jooq.SortField;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Json controller.
 */
@Validated
@Controller("/reactive")
public class JSONController {

	/**
	 * The Webshop repository.
	 */
	private final WebshopRepository webshopRepository;
	/**
	 * The Webshopemails repository.
	 */
	private final WebshopemailsRepository webshopemailsRepository;
	/**
	 * The Query param parser.
	 */
	private final QueryParamParser queryParamParser;
	/**
	 * The Object mapper.
	 */
	private final ObjectMapper objectMapper;

	/**
	 * Instantiates a new Json controller.
	 *
	 * @param webshopRepository       the webshop repository
	 * @param webshopemailsRepository the webshopemails repository
	 * @param queryParamParser        the query param parser
	 * @param objectMapper            the object mapper
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
	 * @param queryParams the query params
	 * @param sort        the sort
	 * @param order       the order
	 * @return the webshops
	 */
	@Get(value = "/get", produces = "application/json", consumes = "application/json")
    public Flux<HttpResponse<WebshopModel[]>> getWebshops(@QueryValue String queryParams, @Nullable String sort, @Nullable String order) {

        List<Condition> condition = queryParamParser.parseParamsWebshop(queryParams);
        SortField<?> sortField = queryParamParser.sortParserWebshop(sort, order);

        List<WebshopModel> webshopModels = new ArrayList<>();

        List<Webshop> webshopFlux = webshopRepository.findVarious(condition, sortField).collectList().block();
        if (webshopFlux != null) {
            for (Webshop webshop : webshopFlux) {
                List<String> webshopemails = webshopemailsRepository.find(webshop.getWebshopid()).map(Webshopemails::getAddress).collectList().block();
                WebshopModel webshopModel = new WebshopModel(webshop, webshopemails);
            }
            return Flux.just(HttpResponse.ok(webshopModels.toArray(WebshopModel[]::new)));
        }

        return Flux.just(HttpResponse.notFound());
    }

	/**
	 * Gets webshop.
	 *
	 * @param handle the handle
	 * @return the webshop
	 */
	@Get(value = "/get/{handle}", produces = "application/json", consumes = "application/json")
    public Flux<HttpResponse<WebshopModel>> getWebshop(String handle) {

        Webshop webshop = webshopRepository.find(handle).block();
        if (webshop != null) {
            List<String> emails = webshopemailsRepository.find(webshop.getWebshopid()).map(Webshopemails::getAddress).collectList().block();
            WebshopModel webshopModel = new WebshopModel(webshop, emails);
            return Flux.just(HttpResponse.ok(webshopModel));
        }

        return Flux.just(HttpResponse.notFound());
    }

	/**
	 * Gets webshop emails.
	 *
	 * @param handle the handle
	 * @return the webshop emails
	 */
	@Get(value = "/get/{handle}/emails", produces = "application/json", consumes = "application/json")
    public Flux<HttpResponse<String[]>> getWebshopEmails(String handle) {

        Webshop webshop = webshopRepository.find(handle).block();
        if (webshop != null) {
            List<String> emails = webshopemailsRepository.find(webshop.getWebshopid()).map(Webshopemails::getAddress).collectList().block();
            if (emails != null) {
                return Flux.just(HttpResponse.ok(emails.toArray(String[]::new)));
            }

            return Flux.just(HttpResponse.notFound());
        }

        return Flux.just(HttpResponse.serverError());
    }

	/**
	 * Create webshop mono.
	 *
	 * @param webshopModel the webshop model
	 * @return the mono
	 */
	@Post(value = "/create", produces = "application/json", consumes = "application/json")
    public Mono<HttpResponse<WebshopModel>> createWebshop(@Body WebshopModel webshopModel) {


        if (Boolean.FALSE.equals(webshopRepository.create(webshopModel.getHandle(), webshopModel.getUrl(),
                webshopModel.getA(), webshopModel.getB(), webshopModel.getC(),
                webshopModel.getInterestRate(), webshopModel.getCurrency(),
                webshopModel.isRunJobs(), webshopModel.isMultiSupplier()).block())) {

            Long webshopId = webshopRepository.find(webshopModel.getHandle()).map(Webshop::getWebshopid).block();
            List<String> emails = webshopemailsRepository.find(webshopId).map(Webshopemails::getAddress).collectList().block();
            for (String email : emails) {
                if (Boolean.FALSE.equals(webshopemailsRepository.create(webshopId, email).block())) {
                    return Mono.just(HttpResponse.serverError());
                }
            }
        }

        return Mono.just(HttpResponse.ok(webshopModel));

    }

	/**
	 * Update webshop mono.
	 *
	 * @param webshopModel the webshop model
	 * @return the mono
	 */
	@Put(value = "/update", produces = "application/json", consumes = "application/json")
    public Mono<HttpResponse<WebshopModel>> updateWebshop(@Body WebshopModel webshopModel) {

        if (Boolean.FALSE.equals(webshopRepository.updateWebshop(webshopModel.getHandle(), webshopModel.getUrl(),
                webshopModel.getA(), webshopModel.getB(), webshopModel.getC(),
                webshopModel.getInterestRate(), webshopModel.getCurrency(),
                webshopModel.isRunJobs(), webshopModel.isMultiSupplier()).block())) {
            return Mono.just(HttpResponse.notFound());
        }

        return Mono.just(HttpResponse.ok(webshopModel));
    }

	/**
	 * Delete webshop mono.
	 *
	 * @param handle the handle
	 * @return the mono
	 */
	@Delete(value = "/delete/{handle}", produces = "application/json", consumes = "application/json")
    public Mono<HttpResponse<Boolean>> deleteWebshop(String handle) {

        if (Boolean.FALSE.equals(webshopRepository.deleteWebshop(handle).block())) {
            return Mono.just(HttpResponse.notFound());
        }

        return Mono.just(HttpResponse.ok(Boolean.TRUE));
    }

}


