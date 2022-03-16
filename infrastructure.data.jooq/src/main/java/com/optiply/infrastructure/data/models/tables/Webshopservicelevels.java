/*
 * This file is generated by jOOQ.
 */
package com.optiply.infrastructure.data.models.tables;


import com.optiply.infrastructure.data.models.DefaultSchema;
import com.optiply.infrastructure.data.models.Keys;
import com.optiply.infrastructure.data.models.tables.records.WebshopservicelevelsRecord;

import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row4;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Webshopservicelevels extends TableImpl<WebshopservicelevelsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>webshopServiceLevels</code>
     */
    public static final Webshopservicelevels WEBSHOPSERVICELEVELS = new Webshopservicelevels();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<WebshopservicelevelsRecord> getRecordType() {
        return WebshopservicelevelsRecord.class;
    }

    /**
     * The column <code>webshopServiceLevels.webshopId</code>.
     */
    public final TableField<WebshopservicelevelsRecord, Long> WEBSHOPID = createField(DSL.name("webshopId"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>webshopServiceLevels.slcA</code>.
     */
    public final TableField<WebshopservicelevelsRecord, Double> SLCA = createField(DSL.name("slcA"), SQLDataType.DOUBLE.nullable(false), this, "");

    /**
     * The column <code>webshopServiceLevels.slcB</code>.
     */
    public final TableField<WebshopservicelevelsRecord, Double> SLCB = createField(DSL.name("slcB"), SQLDataType.DOUBLE.nullable(false), this, "");

    /**
     * The column <code>webshopServiceLevels.slcC</code>.
     */
    public final TableField<WebshopservicelevelsRecord, Double> SLCC = createField(DSL.name("slcC"), SQLDataType.DOUBLE.nullable(false), this, "");

    private Webshopservicelevels(Name alias, Table<WebshopservicelevelsRecord> aliased) {
        this(alias, aliased, null);
    }

    private Webshopservicelevels(Name alias, Table<WebshopservicelevelsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>webshopServiceLevels</code> table reference
     */
    public Webshopservicelevels(String alias) {
        this(DSL.name(alias), WEBSHOPSERVICELEVELS);
    }

    /**
     * Create an aliased <code>webshopServiceLevels</code> table reference
     */
    public Webshopservicelevels(Name alias) {
        this(alias, WEBSHOPSERVICELEVELS);
    }

    /**
     * Create a <code>webshopServiceLevels</code> table reference
     */
    public Webshopservicelevels() {
        this(DSL.name("webshopServiceLevels"), null);
    }

    public <O extends Record> Webshopservicelevels(Table<O> child, ForeignKey<O, WebshopservicelevelsRecord> key) {
        super(child, key, WEBSHOPSERVICELEVELS);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public UniqueKey<WebshopservicelevelsRecord> getPrimaryKey() {
        return Keys.WEBSHOPSERVICELEVELS_PK;
    }

    @Override
    public List<UniqueKey<WebshopservicelevelsRecord>> getUniqueKeys() {
        return Arrays.asList(Keys.CONSTRAINT_5);
    }

    @Override
    public List<ForeignKey<WebshopservicelevelsRecord, ?>> getReferences() {
        return Arrays.asList(Keys.WEBSHOPSERVICELEVELS_FK0);
    }

    private transient Webshop _webshop;

    /**
     * Get the implicit join path to the <code>PUBLIC.webshop</code> table.
     */
    public Webshop webshop() {
        if (_webshop == null)
            _webshop = new Webshop(this, Keys.WEBSHOPSERVICELEVELS_FK0);

        return _webshop;
    }

    @Override
    public Webshopservicelevels as(String alias) {
        return new Webshopservicelevels(DSL.name(alias), this);
    }

    @Override
    public Webshopservicelevels as(Name alias) {
        return new Webshopservicelevels(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Webshopservicelevels rename(String name) {
        return new Webshopservicelevels(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Webshopservicelevels rename(Name name) {
        return new Webshopservicelevels(name, null);
    }

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row4<Long, Double, Double, Double> fieldsRow() {
        return (Row4) super.fieldsRow();
    }
}