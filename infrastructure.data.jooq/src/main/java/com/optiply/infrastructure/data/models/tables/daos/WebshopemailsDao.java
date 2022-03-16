/*
 * This file is generated by jOOQ.
 */
package com.optiply.infrastructure.data.models.tables.daos;


import com.optiply.infrastructure.data.models.tables.Webshopemails;
import com.optiply.infrastructure.data.models.tables.records.WebshopemailsRecord;

import java.util.List;
import java.util.Optional;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class WebshopemailsDao extends DAOImpl<WebshopemailsRecord, com.optiply.infrastructure.data.models.tables.pojos.Webshopemails, Long> {

    /**
     * Create a new WebshopemailsDao without any configuration
     */
    public WebshopemailsDao() {
        super(Webshopemails.WEBSHOPEMAILS, com.optiply.infrastructure.data.models.tables.pojos.Webshopemails.class);
    }

    /**
     * Create a new WebshopemailsDao with an attached configuration
     */
    public WebshopemailsDao(Configuration configuration) {
        super(Webshopemails.WEBSHOPEMAILS, com.optiply.infrastructure.data.models.tables.pojos.Webshopemails.class, configuration);
    }

    @Override
    public Long getId(com.optiply.infrastructure.data.models.tables.pojos.Webshopemails object) {
        return object.getAddressid();
    }

    /**
     * Fetch records that have <code>addressId BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<com.optiply.infrastructure.data.models.tables.pojos.Webshopemails> fetchRangeOfAddressid(Long lowerInclusive, Long upperInclusive) {
        return fetchRange(Webshopemails.WEBSHOPEMAILS.ADDRESSID, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>addressId IN (values)</code>
     */
    public List<com.optiply.infrastructure.data.models.tables.pojos.Webshopemails> fetchByAddressid(Long... values) {
        return fetch(Webshopemails.WEBSHOPEMAILS.ADDRESSID, values);
    }

    /**
     * Fetch a unique record that has <code>addressId = value</code>
     */
    public com.optiply.infrastructure.data.models.tables.pojos.Webshopemails fetchOneByAddressid(Long value) {
        return fetchOne(Webshopemails.WEBSHOPEMAILS.ADDRESSID, value);
    }

    /**
     * Fetch a unique record that has <code>addressId = value</code>
     */
    public Optional<com.optiply.infrastructure.data.models.tables.pojos.Webshopemails> fetchOptionalByAddressid(Long value) {
        return fetchOptional(Webshopemails.WEBSHOPEMAILS.ADDRESSID, value);
    }

    /**
     * Fetch records that have <code>webshopId BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<com.optiply.infrastructure.data.models.tables.pojos.Webshopemails> fetchRangeOfWebshopid(Long lowerInclusive, Long upperInclusive) {
        return fetchRange(Webshopemails.WEBSHOPEMAILS.WEBSHOPID, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>webshopId IN (values)</code>
     */
    public List<com.optiply.infrastructure.data.models.tables.pojos.Webshopemails> fetchByWebshopid(Long... values) {
        return fetch(Webshopemails.WEBSHOPEMAILS.WEBSHOPID, values);
    }

    /**
     * Fetch records that have <code>address BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<com.optiply.infrastructure.data.models.tables.pojos.Webshopemails> fetchRangeOfAddress(String lowerInclusive, String upperInclusive) {
        return fetchRange(Webshopemails.WEBSHOPEMAILS.ADDRESS, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>address IN (values)</code>
     */
    public List<com.optiply.infrastructure.data.models.tables.pojos.Webshopemails> fetchByAddress(String... values) {
        return fetch(Webshopemails.WEBSHOPEMAILS.ADDRESS, values);
    }
}
