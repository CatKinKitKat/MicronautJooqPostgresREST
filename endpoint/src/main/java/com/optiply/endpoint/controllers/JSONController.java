package com.optiply.endpoint.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.optiply.endpoint.models.WebshopModel;
import com.optiply.endpoint.parsers.QueryParamParser;
import com.optiply.endpoint.parsers.WebshopModelParser;
import com.optiply.infrastructure.data.models.tables.pojos.Webshop;
import com.optiply.infrastructure.data.repositories.WebshopRepository;
import com.optiply.infrastructure.data.repositories.WebshopemailsRepository;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
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
     * The Webshop model parser.
     */
    private final WebshopModelParser webshopModelParser;
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
     * @param webshopModelParser      the webshop model parser
     * @param objectMapper            the object mapper
     */
    @Inject
    public JSONController(WebshopRepository webshopRepository,
                          WebshopemailsRepository webshopemailsRepository,
                          QueryParamParser queryParamParser,
                          WebshopModelParser webshopModelParser,
                          ObjectMapper objectMapper) {

        this.webshopRepository = webshopRepository;
        this.webshopemailsRepository = webshopemailsRepository;
        this.queryParamParser = queryParamParser;
        this.webshopModelParser = webshopModelParser;
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
    @Get(value = "/find", produces = "application/json", consumes = "application/json")
    public HttpResponse<WebshopModel[]> getWebshops(@QueryValue String queryParams, @Nullable String sort, @Nullable String order) {
        List<Condition> conditions = queryParamParser.parseParamsWebshop(queryParams);
        SortField<?> sortBy = queryParamParser.sortParserWebshop(sort, order);
        List<Webshop> webshops;

        webshops = webshopRepository.getWebshops(conditions, sortBy);

        if (webshops == null || webshops.isEmpty()) {
            return HttpResponse.notFound();
        }

        WebshopModel[] webshopModels = new WebshopModel[webshops.size()];

        for (Webshop w : webshops) {
            List<String> emails = webshopemailsRepository.getEmails(w.getHandle(), queryParamParser.sortParserEmails("address", "asc"));
            WebshopModel wm = new WebshopModel(w, emails);
        }

        return HttpResponse.ok(webshopModels);
    }

    /**
     * Gets webshop.
     *
     * @param handle the handle
     * @param sort   the sort
     * @param order  the order
     * @return the webshop
     */
    @Get(value = "/get/{handle}", produces = "application/json", consumes = "application/json")
    public HttpResponse<WebshopModel> getWebshop(String handle, @Nullable @QueryValue String sort, @Nullable @QueryValue String order) {

        if (sort == null || sort.isEmpty()) sort = "address";
        if (order == null || order.isEmpty()) order = "asc";

        SortField<?> sortBy = queryParamParser.sortParserWebshop(sort, order);


        Webshop w = webshopRepository.getWebshop(handle);

        if (w == null) {
            return HttpResponse.notFound();
        }

        List<String> emails = webshopemailsRepository.getEmails(handle, queryParamParser.sortParserEmails("address", "asc"));

        WebshopModel wm = new WebshopModel(w, emails);

        return HttpResponse.ok(wm);
    }

    /**
     * Create webshop http response.
     *
     * @param body the body
     * @return the http response
     */
    @Post(value = "/create", produces = "application/json", consumes = "application/json")
    public HttpResponse<WebshopModel> createWebshop(@Body String body) {

        if (!webshopModelParser.parseJSON(body)) {
            return HttpResponse.badRequest();
        }

        WebshopModel wm = objectMapper.convertValue(body, WebshopModel.class);

        if (!(webshopRepository.getWebshop(wm.handle) == null)) {
            Boolean webshop = webshopRepository.createWebshop(wm.handle, wm.url, wm.interestRate, wm.A, wm.B, wm.C, wm.currency, wm.multiSupplier, wm.runJobs);
            Boolean emails = webshopemailsRepository.create(wm.handle, wm.emails);

            if (webshop && emails) {
                return HttpResponse.ok(wm);
            }

            return HttpResponse.serverError();

        }

        return HttpResponse.serverError();
    }

    /**
     * Update webshop http response.
     *
     * @param body the body
     * @return the http response
     */
    @Put(value = "/update", produces = "application/json", consumes = "application/json")
    public HttpResponse<WebshopModel> updateWebshop(@Body String body) {

        if (!webshopModelParser.parseJSON(body)) {
            return HttpResponse.badRequest();
        }

        WebshopModel wm = objectMapper.convertValue(body, WebshopModel.class);

        if (webshopRepository.getWebshop(wm.handle) == null) {
            return HttpResponse.notFound();
        }

        Boolean webshop = webshopRepository.updateWebshop(wm.handle, wm.url, wm.interestRate, wm.A, wm.B, wm.C, wm.currency, wm.multiSupplier, wm.runJobs);
        Boolean emails1 = webshopemailsRepository.deleteAll(wm.handle);
        Boolean emails2 = webshopemailsRepository.create(wm.handle, wm.emails);

        if (webshop && emails1 && emails2) {
            return HttpResponse.ok(wm);
        }

        return HttpResponse.serverError();
    }


    /**
     * Delete webshop http response.
     *
     * @param handle the handle
     * @return the http response
     */
    @Get(value = "/delete/{handle}", produces = "application/json", consumes = "application/json")
    public HttpResponse<WebshopModel> deleteWebshop(String handle) {

        if (webshopRepository.getWebshop(handle) == null) {
            return HttpResponse.notFound();
        }

        Boolean webshop = webshopRepository.deleteWebshop(handle);
        Boolean emails = webshopemailsRepository.deleteAll(handle);

        if (webshop && emails) {
            return HttpResponse.ok();
        }

        return HttpResponse.serverError();
    }


    /**
     * Gets emails.
     *
     * @param handle the handle
     * @return the emails
     */
    @Get(value = "/emails/{handle}", produces = "application/json", consumes = "application/json")
    public HttpResponse<List<String>> getEmails(String handle) {

        List<String> emails = webshopemailsRepository.getEmails(handle, queryParamParser.sortParserEmails("address", "asc"));

        if (emails.isEmpty()) {
            return HttpResponse.notFound();
        }

        return HttpResponse.ok(emails);
    }

    /**
     * Gets emails.
     *
     * @param query the query
     * @param sort  the sort
     * @param order the order
     * @return the emails
     */
    @Get(value = "/emails", produces = "application/json", consumes = "application/json")
    public HttpResponse<List<String>> getEmails(@QueryValue String query, @Nullable @QueryValue String sort, @Nullable @QueryValue String order) {

        if (sort == null || sort.isEmpty()) sort = "address";
        if (order == null || order.isEmpty()) order = "asc";

        SortField<?> sortBy = queryParamParser.sortParserEmails(sort, order);

        List<String> emails = webshopemailsRepository.getEmails(query, sortBy);

        if (emails.isEmpty()) {
            return HttpResponse.notFound();
        }

        return HttpResponse.ok(emails);
    }

}
