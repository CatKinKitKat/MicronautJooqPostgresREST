package com.optiply.endpoint.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.optiply.endpoint.models.WebshopBodyModel;
import com.optiply.endpoint.models.WebshopEmailsModel;
import com.optiply.endpoint.models.WebshopSettingsModel;
import com.optiply.endpoint.models.WebshopSimpleModel;
import com.optiply.infrastructure.data.models.Tables;
import com.optiply.infrastructure.data.models.tables.pojos.Webshopemails;
import com.optiply.infrastructure.data.repositories.WebshopRepository;
import com.optiply.infrastructure.data.repositories.WebshopemailsRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.validation.Validated;
import jakarta.inject.Inject;
import lombok.extern.java.Log;
import org.jooq.Condition;
import org.jooq.SortField;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.jooq.impl.DSL.condition;

/**
 * The type Endpoint controller.
 */
@Log
@Validated
@Controller("/")
public class EndpointController {

    /**
     * The Webshop repository.
     */
    private final WebshopRepository webshopRepository;
    /**
     * The Webshopemails repository.
     */
    private final WebshopemailsRepository webshopemailsRepository;
    /**
     * The Object mapper.
     */
    private final ObjectMapper objectMapper;

    /**
     * Instantiates a new Endpoint controller.
     *
     * @param webshopRepository       the webshop repository
     * @param webshopemailsRepository the webshopemails repository
     * @param objectMapper            the object mapper
     */
    @Inject
    public EndpointController(WebshopRepository webshopRepository, WebshopemailsRepository webshopemailsRepository, ObjectMapper objectMapper) {

        this.webshopRepository = webshopRepository;
        this.webshopemailsRepository = webshopemailsRepository;
        this.objectMapper = objectMapper;
    }

    /**
     * Gets webshop.
     *
     * @param handle the handle
     * @return the webshop
     */
    @Get(value = "/get/{handle}", produces = "application/json", consumes = "application/json")
    public Mono<HttpResponse<WebshopSimpleModel>> getWebshop(String handle) {

        return webshopRepository.find(handle).flatMap(webshop -> {
            log.info("webshop: " + webshop + " found\n" + "id: " + webshop.getWebshopid());
            return Mono.just(new WebshopSimpleModel(webshop));
        }).flatMap(webshopModel -> Mono.just(HttpResponse.ok(webshopModel)));

    }

    /**
     * Gets webshop emails.
     *
     * @param handle the handle
     * @return the webshop emails
     */
    @Get(value = "/get/{handle}/emails", produces = "application/json", consumes = "application/json")
    public Mono<HttpResponse<WebshopEmailsModel>> getWebshopEmails(String handle) {

        return webshopRepository.find(handle).flatMapMany(webshop -> {
            log.info("webshop: " + webshop + " found\n" + "id: " + webshop.getWebshopid());
            return webshopemailsRepository.find(webshop.getWebshopid());
        }).collectList().flatMap(webshopemails -> {
            log.info("webshopemails: " + webshopemails + " found\n" + "id: " + webshopemails.get(0).getWebshopid());
            return Mono.just(new WebshopEmailsModel(handle, webshopemails.stream().map(Webshopemails::getAddress).toList()));
        }).flatMap(webshopEmailsModel -> Mono.just(HttpResponse.ok(webshopEmailsModel)));

    }

    /**
     * Gets webshop settings.
     *
     * @param handle the handle
     * @return the webshop settings
     */
    @Get(value = "/get/{handle}/settings", produces = "application/json", consumes = "application/json")
    public Mono<HttpResponse<WebshopSettingsModel>> getWebshopSettings(String handle) {

        return webshopRepository.find(handle).flatMap(webshop -> {
            log.info("webshop: " + webshop + " found\n" + "id: " + webshop.getWebshopid());
            return Mono.just(new WebshopSettingsModel(webshop));
        }).flatMap(webshopSettingsModel -> Mono.just(HttpResponse.ok(webshopSettingsModel)));

    }

    /**
     * Create webshop simple mono.
     *
     * @param webshopBodyModel the webshop body model
     * @return the mono
     */
    @Post(value = "/create/simple", produces = "application/json", consumes = "application/json")
    public Mono<HttpResponse<?>> createWebshopSimple(@Body WebshopBodyModel webshopBodyModel) {

        return webshopRepository.create(
                webshopBodyModel.getHandle(), webshopBodyModel.getUrl(),
                webshopBodyModel.getA(), webshopBodyModel.getB(), webshopBodyModel.getC()
        ).flatMap(webshop -> {
            log.info("webshop: " + webshop + " created\n" + "id: " + webshop.toString());
            return Mono.just(HttpResponse.created(webshop));
        });
    }

    /**
     * Create webshop mono.
     *
     * @param webshopBodyModel the webshop body model
     * @return the mono
     */
    @Post(value = "/create", produces = "application/json", consumes = "application/json")
    public Mono<HttpResponse<?>> createWebshop(@Body WebshopBodyModel webshopBodyModel) {

        return webshopRepository.create(
                webshopBodyModel.getHandle(), webshopBodyModel.getUrl(),
                webshopBodyModel.getA(), webshopBodyModel.getB(), webshopBodyModel.getC(),
                webshopBodyModel.getInterestRate(), webshopBodyModel.getCurrency(),
                webshopBodyModel.getRunJobs(), webshopBodyModel.getMultiSupplier()
        ).flatMap(webshop -> {
            log.info("webshop: " + webshop + " created\n" + "id: " + webshop.toString());
            return Mono.just(HttpResponse.created(webshop));
        });
    }

    /**
     * Delete webshop mono.
     *
     * @param handle the handle
     * @return the mono
     */
    @Delete(value = "/delete/{handle}", produces = "application/json", consumes = "application/json")
    public Mono<HttpResponse<?>> deleteWebshop(String handle) {
        return webshopRepository.deleteWebshop(handle).flatMap(webshop -> {
            log.info("webshop: " + webshop + " deleted\n" + "id: " + webshop.toString());
            return Mono.just(HttpResponse.noContent());
        });
    }


    /**
     * Update webshop mono.
     *
     * @param webshopBodyModel the webshop body model
     * @return the mono
     */
    @Put(value = "/update", produces = "application/json", consumes = "application/json")
    public Mono<HttpResponse<?>> updateWebshop(@Body WebshopBodyModel webshopBodyModel) {

        return webshopRepository.updateWebshop(
                webshopBodyModel.getHandle(), webshopBodyModel.getUrl(),
                webshopBodyModel.getA(), webshopBodyModel.getB(), webshopBodyModel.getC(),
                webshopBodyModel.getInterestRate(), webshopBodyModel.getCurrency(),
                webshopBodyModel.getRunJobs(), webshopBodyModel.getMultiSupplier()
        ).flatMap(webshop -> {
            log.info("webshop: " + webshop + " updated\n" + "id: " + webshop.toString());
            return Mono.just(HttpResponse.ok(webshop));
        });
    }


    /**
     * Parse params webshop list.
     *
     * @param params the params
     * @return the list
     */
    private List<Condition> parseParamsWebshop(String... params) {

        if (params == null || params.length == 0) {
            return new ArrayList<>();
        }

        List<Condition> filterList = new ArrayList<>();

        for (String param : params) {
            if (param.contains("handle") || param.contains("url") || param.contains("currency")) {
                if (param.contains(String.valueOf(':'))) {
                    filterList.add(condition(param.substring(0, param.indexOf(':')), param.substring(param.indexOf(':') + 1)));
                } else if (param.contains(String.valueOf('%'))) {
                    filterList.add(condition(param.substring(0, param.indexOf('%')), param.substring(param.indexOf('%') + 1)));
                }
            } else if (param.contains("A") || param.contains("B") || param.contains("C") || param.contains("interestRate")) {
                if (param.contains(String.valueOf('>'))) {
                    filterList.add(condition(param.substring(0, param.indexOf('>')), param.substring(param.indexOf('>') + 1)));
                } else if (param.contains(String.valueOf('<'))) {
                    filterList.add(condition(param.substring(0, param.indexOf('<')), param.substring(param.indexOf('<') + 1)));
                } else if (param.contains(String.valueOf(':'))) {
                    filterList.add(condition(param.substring(0, param.indexOf(':')), param.substring(param.indexOf(':') + 1)));
                }

            } else if (param.contains("runJobs") || param.contains("multiSupply") || param.contains("multiSupplier")) {
                if (param.contains(String.valueOf(':'))) {
                    filterList.add(condition(param.substring(0, param.indexOf(':')), param.substring(param.indexOf(':') + 1)));
                }

            }

            // To add more later
        }

        return filterList;
    }


    /**
     * Parse params emails list.
     *
     * @param params the params
     * @return the list
     */
    private List<Condition> parseParamsEmails(String... params) {

        if (params == null || params.length == 0) {
            return new ArrayList<>();
        }

        List<Condition> filterList = new ArrayList<>();

        for (String param : params) {
            if (param.contains("address")) {

                if (param.contains(String.valueOf('%'))) {
                    filterList.add(condition(param.substring(0, param.indexOf('%')), param.substring(param.indexOf('%') + 1)));
                }
            }
            // To add more later
        }
        return filterList;
    }


    /**
     * Sort parser webshop sort field.
     *
     * @param sort  the sort
     * @param order the order
     * @return the sort field
     */
    private SortField<?> sortParserWebshop(String sort, String order) {

        if (sort == null || sort.isEmpty()) {
            sort = "handle";
        }

        if (order == null || order.isEmpty()) {
            order = "asc";
        }

        sort = sort.toLowerCase();
        order = order.toLowerCase();

        switch (sort) {
            case "handle" -> {
                if (order.equals("asc")) {
                    return Tables.WEBSHOP.HANDLE.asc();
                }
                return Tables.WEBSHOP.HANDLE.desc();
            }
            case "url" -> {
                if (order.equals("asc")) {
                    return Tables.WEBSHOP.URL.asc();
                }
                return Tables.WEBSHOP.URL.desc();
            }
            case "currency" -> {
                if (order.equals("asc")) {
                    return Tables.WEBSHOP.CURRENCY.asc();
                }
                return Tables.WEBSHOP.CURRENCY.desc();
            }
            case "a" -> {
                if (order.equals("asc")) {
                    return Tables.WEBSHOP.A.asc();
                }
                return Tables.WEBSHOP.A.desc();
            }
            case "b" -> {
                if (order.equals("asc")) {
                    return Tables.WEBSHOP.B.asc();
                }
                return Tables.WEBSHOP.B.desc();
            }
            case "c" -> {
                if (order.equals("asc")) {
                    return Tables.WEBSHOP.C.asc();
                }
                return Tables.WEBSHOP.C.desc();
            }
            case "interestrate" -> {
                if (order.equals("asc")) {
                    return Tables.WEBSHOP.INTERESTRATE.asc();
                }
                return Tables.WEBSHOP.INTERESTRATE.desc();
            }
        }


        return Tables.WEBSHOP.HANDLE.asc();
    }

    /**
     * Sort parser emails sort field.
     *
     * @param sort  the sort
     * @param order the order
     * @return the sort field
     */
    private SortField<?> sortParserEmails(String sort, String order) {


        if (sort == null || sort.isEmpty()) {
            sort = "address";
        }

        if (order == null || order.isEmpty()) {
            order = "asc";
        }

        sort = sort.toLowerCase();
        order = order.toLowerCase();

        if ("address".equals(sort)) {
            if (order.equals("asc")) {
                return Tables.WEBSHOPEMAILS.ADDRESS.asc();
            }
            return Tables.WEBSHOPEMAILS.ADDRESS.desc();
        }

        return Tables.WEBSHOPEMAILS.ADDRESS.asc();
    }

}


