package com.optiply.endpoint.parsers;

import com.optiply.infrastructure.data.models.Tables;
import org.jooq.Condition;
import org.jooq.SortField;

import java.util.ArrayList;
import java.util.List;

import static org.jooq.impl.DSL.condition;

/**
 * The type Query param parser.
 */
public class QueryParamParser {

    /**
     * The Querysign.
     */
    private final char QUERYSIGN = '?';
    /**
     * The And.
     */
    private final char AND = '&';
    /**
     * The Eq.
     */
    private final char EQ = ':';
    /**
     * The Ilike.
     */
    private final char ILIKE = '%';
    /**
     * The Greater.
     */
    private final char GREATER = '>';
    /**
     * The Less.
     */
    private final char LESS = '<';

    /**
     * Parse params webshop list.
     *
     * @param params the params
     * @return the list
     */
    public List<Condition> parseParamsWebshop(String... params) {

        if (params == null || params.length == 0) {
            return new ArrayList<>();
        }

        List<Condition> filterList = new ArrayList<>();

        for (String param : params) {
            if (param.contains("handle") ||
                    param.contains("url") ||
                    param.contains("currency")) {

                if (param.contains(String.valueOf(EQ))) {
                    filterList.add(condition(
                            param.substring(0, param.indexOf(EQ)),
                            param.substring(param.indexOf(EQ) + 1))
                    );
                } else if (param.contains(String.valueOf(ILIKE))) {
                    filterList.add(condition(
                            param.substring(0, param.indexOf(ILIKE)),
                            param.substring(param.indexOf(ILIKE) + 1))
                    );
                }

            } else if (param.contains("A") ||
                    param.contains("B") ||
                    param.contains("C") ||
                    param.contains("interestRate")) {

                if (param.contains(String.valueOf(GREATER))) {
                    filterList.add(condition(
                            param.substring(0, param.indexOf(GREATER)),
                            param.substring(param.indexOf(GREATER) + 1))
                    );
                } else if (param.contains(String.valueOf(LESS))) {
                    filterList.add(condition(
                            param.substring(0, param.indexOf(LESS)),
                            param.substring(param.indexOf(LESS) + 1))
                    );
                } else if (param.contains(String.valueOf(EQ))) {
                    filterList.add(condition(
                            param.substring(0, param.indexOf(EQ)),
                            param.substring(param.indexOf(EQ) + 1))
                    );
                }

            } else if (param.contains("runJobs") ||
                    param.contains("multiSupply") ||
                    param.contains("multiSupplier")) {

                if (param.contains(String.valueOf(EQ))) {
                    filterList.add(
                            condition(param.substring(0, param.indexOf(EQ)),
                                    param.substring(param.indexOf(EQ) + 1))
                    );
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
    public List<Condition> parseParamsEmails(String... params) {

        if (params == null || params.length == 0) {
            return new ArrayList<>();
        }

        List<Condition> filterList = new ArrayList<>();

        for (String param : params) {
            if (param.contains("address")) {

                if (param.contains(String.valueOf(ILIKE))) {
                    filterList.add(condition(
                            param.substring(0, param.indexOf(ILIKE)),
                            param.substring(param.indexOf(ILIKE) + 1))
                    );
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
    public SortField<?> sortParserWebshop(String sort, String order) {

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
    public SortField<?> sortParserEmails(String sort, String order) {


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

