package com.optiply.endpoint.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.optiply.endpoint.models.WebshopBodyModel;
import com.optiply.endpoint.models.WebshopEmailsModel;
import com.optiply.endpoint.models.WebshopSettingsModel;
import com.optiply.endpoint.models.WebshopSimpleModel;
import com.optiply.infrastructure.data.models.Tables;
import com.optiply.infrastructure.data.repositories.WebshopRepository;
import com.optiply.infrastructure.data.repositories.WebshopemailsRepository;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.validation.Validated;
import jakarta.inject.Inject;
import lombok.extern.java.Log;
import org.jooq.Condition;
import org.jooq.SortField;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     * Gets webshops.
     *
     * @param q the q
     * @param s the s
     * @param o the o
     * @return the webshops
     */
    @Get(value = "/get", produces = "application/json", consumes = "application/json")
    public Flux<HttpResponse<WebshopSimpleModel>> getWebshops(@QueryValue String q, @Nullable @QueryValue String s, @Nullable @QueryValue String o) {

        if (s == null || s.isEmpty()) {
            s = "handle";
        }

        if (o == null || o.isEmpty()) {
            o = "asc";
        }

        SortField<?> sortField = sortParserWebshop(s, o);
        Condition condition = parseParamsWebshop(q);


        return webshopRepository.findVarious(condition, sortField).flatMap(webshops -> {
            log.info("webshop found");
            return Mono.just(new WebshopSimpleModel(webshops));
        }).concatMap(webshopModel -> Flux.just(HttpResponse.ok(webshopModel)));

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
    public Mono<MutableHttpResponse<String>> createWebshopSimple(@Body WebshopBodyModel webshopModel) {

        return webshopRepository.create(
                        webshopModel.getHandle(), webshopModel.getUrl(),
                        webshopModel.getA(), webshopModel.getB(), webshopModel.getC()
                ).flatMap(webshop -> {
                    if (webshop) {
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

        return webshopRepository.create(
                        webshopModel.getHandle(), webshopModel.getUrl(),
                        webshopModel.getA(), webshopModel.getB(), webshopModel.getC(),
                        webshopModel.getInterestRate(), webshopModel.getCurrency(),
                        webshopModel.getRunJobs(), webshopModel.getMultiSupplier()
                ).flatMap(webshop -> {
                    if (webshop) {
                        log.info("webshop created");
                        return Mono.just(HttpResponse.created("Webshop created."));
                    }
                    log.info("webshop not created");
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
        return webshopRepository.deleteWebshop(handle).flatMap(webshop -> {
                    if (webshop) {
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
                ).flatMap(webshop -> {
                    if (webshop) {
                        log.info("webshop updated");
                        return Mono.just(HttpResponse.ok("Webshop updated."));
                    }
                    log.info("webshop not updated");
                    return Mono.empty();
                })
                .switchIfEmpty(Mono.just(HttpResponse.notFound())).onErrorReturn(HttpResponse.serverError());
    }


    /**
     * Parse params webshop condition.
     *
     * @param params the params
     * @return the condition
     */
    private Condition parseParamsWebshop(String... params) {

        if (params == null || params.length == 0) {
            return null;
        }

        List<Condition> filterList = new ArrayList<>();

        for (String param : params) {

            String operation = "";
            final Matcher m = Pattern.compile("[:%<>]").matcher(param);
            if (m.find())
                switch (m.group().charAt(0)) {
                    case ':' -> operation = ":";
                    case '%' -> operation = "%";
                    case '<' -> operation = "<";
                    case '>' -> operation = ">";
                    default -> operation = "";
                }

            if (operation.isEmpty()) return null;

            String[] split = param.split(operation);
            switch (split[0]) {
                case "handle":
                    if (operation.equals(":")) {
                        filterList.add(Tables.WEBSHOP.HANDLE.equalIgnoreCase(split[1]));
                    } else if (operation.equals("%")) {
                        filterList.add(Tables.WEBSHOP.HANDLE.likeIgnoreCase(split[1]));
                    }
                    break;
                case "url":
                    if (operation.equals(":")) {
                        filterList.add(Tables.WEBSHOP.URL.equalIgnoreCase(split[1]));
                    } else if (operation.equals("%")) {
                        filterList.add(Tables.WEBSHOP.URL.likeIgnoreCase(split[1]));
                    }
                    break;
                case "interestRate":
                    if (operation.equals(">")) {
                        filterList.add(Tables.WEBSHOP.INTEREST_RATE.greaterThan(Short.parseShort(split[1])));
                    } else if (operation.equals("<")) {
                        filterList.add(Tables.WEBSHOP.INTEREST_RATE.lessThan(Short.parseShort(split[1])));
                    }
                    break;
                default:
                    break;
                // To add more later
            }
        }


        Condition condition = filterList.get(0);
        filterList.remove(0);
        for (Condition c : filterList) {
            condition = condition.and(c);
        }

        return condition;
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
                    return Tables.WEBSHOP.INTEREST_RATE.asc();
                }
                return Tables.WEBSHOP.INTEREST_RATE.desc();
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


