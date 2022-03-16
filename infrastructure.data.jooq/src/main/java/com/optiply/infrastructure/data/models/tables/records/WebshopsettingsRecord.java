/*
 * This file is generated by jOOQ.
 */
package com.optiply.infrastructure.data.models.tables.records;


import com.optiply.infrastructure.data.models.tables.Webshopsettings;
import com.optiply.infrastructure.data.models.tables.interfaces.IWebshopsettings;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class WebshopsettingsRecord extends UpdatableRecordImpl<WebshopsettingsRecord> implements Record4<Long, String, Boolean, Boolean>, IWebshopsettings {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>webshopSettings.webshopId</code>.
     */
    @Override
    public WebshopsettingsRecord setWebshopid(Long value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>webshopSettings.webshopId</code>.
     */
    @Override
    public Long getWebshopid() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>webshopSettings.currency</code>.
     */
    @Override
    public WebshopsettingsRecord setCurrency(String value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>webshopSettings.currency</code>.
     */
    @Override
    public String getCurrency() {
        return (String) get(1);
    }

    /**
     * Setter for <code>webshopSettings.runJobs</code>.
     */
    @Override
    public WebshopsettingsRecord setRunjobs(Boolean value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>webshopSettings.runJobs</code>.
     */
    @Override
    public Boolean getRunjobs() {
        return (Boolean) get(2);
    }

    /**
     * Setter for <code>webshopSettings.multiSupply</code>.
     */
    @Override
    public WebshopsettingsRecord setMultisupply(Boolean value) {
        set(3, value);
        return this;
    }

    /**
     * Getter for <code>webshopSettings.multiSupply</code>.
     */
    @Override
    public Boolean getMultisupply() {
        return (Boolean) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row4<Long, String, Boolean, Boolean> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    @Override
    public Row4<Long, String, Boolean, Boolean> valuesRow() {
        return (Row4) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return Webshopsettings.WEBSHOPSETTINGS.WEBSHOPID;
    }

    @Override
    public Field<String> field2() {
        return Webshopsettings.WEBSHOPSETTINGS.CURRENCY;
    }

    @Override
    public Field<Boolean> field3() {
        return Webshopsettings.WEBSHOPSETTINGS.RUNJOBS;
    }

    @Override
    public Field<Boolean> field4() {
        return Webshopsettings.WEBSHOPSETTINGS.MULTISUPPLY;
    }

    @Override
    public Long component1() {
        return getWebshopid();
    }

    @Override
    public String component2() {
        return getCurrency();
    }

    @Override
    public Boolean component3() {
        return getRunjobs();
    }

    @Override
    public Boolean component4() {
        return getMultisupply();
    }

    @Override
    public Long value1() {
        return getWebshopid();
    }

    @Override
    public String value2() {
        return getCurrency();
    }

    @Override
    public Boolean value3() {
        return getRunjobs();
    }

    @Override
    public Boolean value4() {
        return getMultisupply();
    }

    @Override
    public WebshopsettingsRecord value1(Long value) {
        setWebshopid(value);
        return this;
    }

    @Override
    public WebshopsettingsRecord value2(String value) {
        setCurrency(value);
        return this;
    }

    @Override
    public WebshopsettingsRecord value3(Boolean value) {
        setRunjobs(value);
        return this;
    }

    @Override
    public WebshopsettingsRecord value4(Boolean value) {
        setMultisupply(value);
        return this;
    }

    @Override
    public WebshopsettingsRecord values(Long value1, String value2, Boolean value3, Boolean value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // FROM and INTO
    // -------------------------------------------------------------------------

    @Override
    public void from(IWebshopsettings from) {
        setWebshopid(from.getWebshopid());
        setCurrency(from.getCurrency());
        setRunjobs(from.getRunjobs());
        setMultisupply(from.getMultisupply());
    }

    @Override
    public <E extends IWebshopsettings> E into(E into) {
        into.from(this);
        return into;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached WebshopsettingsRecord
     */
    public WebshopsettingsRecord() {
        super(Webshopsettings.WEBSHOPSETTINGS);
    }

    /**
     * Create a detached, initialised WebshopsettingsRecord
     */
    public WebshopsettingsRecord(Long webshopid, String currency, Boolean runjobs, Boolean multisupply) {
        super(Webshopsettings.WEBSHOPSETTINGS);

        setWebshopid(webshopid);
        setCurrency(currency);
        setRunjobs(runjobs);
        setMultisupply(multisupply);
    }

    /**
     * Create a detached, initialised WebshopsettingsRecord
     */
    public WebshopsettingsRecord(com.optiply.infrastructure.data.models.tables.pojos.Webshopsettings value) {
        super(Webshopsettings.WEBSHOPSETTINGS);

        if (value != null) {
            setWebshopid(value.getWebshopid());
            setCurrency(value.getCurrency());
            setRunjobs(value.getRunjobs());
            setMultisupply(value.getMultisupply());
        }
    }
}
