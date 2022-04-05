package com.optiply.endpoint.parsers;

import com.optiply.infrastructure.data.models.Tables;
import org.jooq.Condition;
import org.jooq.SortField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.jooq.impl.DSL.condition;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The type Query param parser tests.
 */
public class QueryParamParserTests {

    /**
     * The Parser.
     */
    private QueryParamParser parser;

    /**
     * Sets up.
     */
    @BeforeEach
    public void setUp() {
        parser = new QueryParamParser();
    }

    /**
     * Webshop parser.
     */
    @Test
    public void webshopParser() {

        String[] params = {"handle:optiply", "interestRate>20"};
        String sort = "handle";
        String order = "desc";

        List<Condition> conditions = parser.parseParamsWebshop(params);
        List<Condition> toEqual = List.of(
                condition(params[0].substring(0, params[0].indexOf(':')),
                        params[0].substring(params[0].indexOf(':') + 1)),
                condition(params[1].substring(0, params[1].indexOf('>')),
                        params[1].substring(params[1].indexOf('>') + 1))
        );

        SortField<?> sortField = parser.sortParserWebshop(sort, order);
        SortField<?> toEqualSort = Tables.WEBSHOP.HANDLE.desc();

        assertEquals(toEqual, conditions);
        assertEquals(toEqualSort, sortField);

    }

    /**
     * Webshop parser with nulls.
     */
    @Test
    public void webshopParser_withNulls() {

        String[] params = {"handle:optiply"};

        List<Condition> conditions = parser.parseParamsWebshop(params);
        List<Condition> toEqual = List.of(
                condition(params[0].substring(0, params[0].indexOf(':')),
                        params[0].substring(params[0].indexOf(':') + 1))
        );

        SortField<?> sortField = parser.sortParserWebshop(null, null);
        SortField<?> toEqualSort = Tables.WEBSHOP.HANDLE.asc();

        assertEquals(toEqual, conditions);
        assertEquals(toEqualSort, sortField);

    }

    /**
     * Emails parser.
     */
    @Test
    public void emailsParser() {

        String[] params = {"address%optiply"};
        String sort = "address";
        String order = "desc";

        List<Condition> conditions = parser.parseParamsEmails(params);
        List<Condition> toEqual = List.of(
                condition(params[0].substring(0, params[0].indexOf('%')),
                        params[0].substring(params[0].indexOf('%') + 1))
        );

        SortField<?> sortField = parser.sortParserEmails(sort, order);
        SortField<?> toEqualSort = Tables.WEBSHOPEMAILS.ADDRESS.desc();

        assertEquals(toEqual, conditions);
        assertEquals(toEqualSort, sortField);


    }

    /**
     * Emails parser with nulls.
     */
    @Test
    public void emailsParser_withNulls() {

        String[] params = {"address%optiply"};

        List<Condition> conditions = parser.parseParamsEmails(params);
        List<Condition> toEqual = List.of(
                condition(params[0].substring(0, params[0].indexOf('%')),
                        params[0].substring(params[0].indexOf('%') + 1))
        );

        SortField<?> sortField = parser.sortParserEmails(null, null);
        SortField<?> toEqualSort = Tables.WEBSHOPEMAILS.ADDRESS.asc();

        assertEquals(toEqual, conditions);
        assertEquals(toEqualSort, sortField);

    }

}
