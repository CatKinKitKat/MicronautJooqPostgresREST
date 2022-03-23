/*
 * This file is generated by jOOQ.
 */
package com.optiply.infrastructure.data.models.tables.interfaces;


import java.io.Serializable;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public interface IWebshopemails extends Serializable {

    /**
     * Setter for <code>webshopEmails.addressId</code>.
     */
    public IWebshopemails setAddressid(Long value);

    /**
     * Getter for <code>webshopEmails.addressId</code>.
     */
    public Long getAddressid();

    /**
     * Setter for <code>webshopEmails.webshopId</code>.
     */
    public IWebshopemails setWebshopid(Long value);

    /**
     * Getter for <code>webshopEmails.webshopId</code>.
     */
    public Long getWebshopid();

    /**
     * Setter for <code>webshopEmails.address</code>.
     */
    public IWebshopemails setAddress(String value);

    /**
     * Getter for <code>webshopEmails.address</code>.
     */
    public String getAddress();

    // -------------------------------------------------------------------------
    // FROM and INTO
    // -------------------------------------------------------------------------

    /**
     * Load data from another generated Record/POJO implementing the common
     * interface IWebshopemails
     */
    public void from(IWebshopemails from);

    /**
     * Copy data into another generated Record/POJO implementing the common
     * interface IWebshopemails
     */
    public <E extends IWebshopemails> E into(E into);
}