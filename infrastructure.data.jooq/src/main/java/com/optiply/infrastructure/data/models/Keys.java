/*
 * This file is generated by jOOQ.
 */
package com.optiply.infrastructure.data.models;


import com.optiply.infrastructure.data.models.tables.Webshop;
import com.optiply.infrastructure.data.models.tables.Webshopemails;
import com.optiply.infrastructure.data.models.tables.Webshopservicelevels;
import com.optiply.infrastructure.data.models.tables.Webshopsettings;
import com.optiply.infrastructure.data.models.tables.records.WebshopRecord;
import com.optiply.infrastructure.data.models.tables.records.WebshopemailsRecord;
import com.optiply.infrastructure.data.models.tables.records.WebshopservicelevelsRecord;
import com.optiply.infrastructure.data.models.tables.records.WebshopsettingsRecord;

import org.jooq.ForeignKey;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables in the
 * default schema.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<WebshopRecord> CONSTRAINT_4 = Internal.createUniqueKey(Webshop.WEBSHOP, DSL.name("CONSTRAINT_4"), new TableField[] { Webshop.WEBSHOP.HANDLE }, true);
    public static final UniqueKey<WebshopRecord> CONSTRAINT_48 = Internal.createUniqueKey(Webshop.WEBSHOP, DSL.name("CONSTRAINT_48"), new TableField[] { Webshop.WEBSHOP.URL }, true);
    public static final UniqueKey<WebshopRecord> WEBSHOP_PK = Internal.createUniqueKey(Webshop.WEBSHOP, DSL.name("webshop_pk"), new TableField[] { Webshop.WEBSHOP.WEBSHOPID }, true);
    public static final UniqueKey<WebshopemailsRecord> WEBSHOPEMAILS_PK = Internal.createUniqueKey(Webshopemails.WEBSHOPEMAILS, DSL.name("webshopEmails_pk"), new TableField[] { Webshopemails.WEBSHOPEMAILS.ADDRESSID }, true);
    public static final UniqueKey<WebshopservicelevelsRecord> CONSTRAINT_5 = Internal.createUniqueKey(Webshopservicelevels.WEBSHOPSERVICELEVELS, DSL.name("CONSTRAINT_5"), new TableField[] { Webshopservicelevels.WEBSHOPSERVICELEVELS.WEBSHOPID }, true);
    public static final UniqueKey<WebshopservicelevelsRecord> WEBSHOPSERVICELEVELS_PK = Internal.createUniqueKey(Webshopservicelevels.WEBSHOPSERVICELEVELS, DSL.name("webshopServiceLevels_pk"), new TableField[] { Webshopservicelevels.WEBSHOPSERVICELEVELS.WEBSHOPID }, true);
    public static final UniqueKey<WebshopsettingsRecord> WEBSHOPSETTINGS_PK = Internal.createUniqueKey(Webshopsettings.WEBSHOPSETTINGS, DSL.name("webshopSettings_pk"), new TableField[] { Webshopsettings.WEBSHOPSETTINGS.WEBSHOPID }, true);

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<WebshopemailsRecord, WebshopRecord> WEBSHOPEMAILS_FK0 = Internal.createForeignKey(Webshopemails.WEBSHOPEMAILS, DSL.name("webshopEmails_fk0"), new TableField[] { Webshopemails.WEBSHOPEMAILS.WEBSHOPID }, Keys.WEBSHOP_PK, new TableField[] { Webshop.WEBSHOP.WEBSHOPID }, true);
    public static final ForeignKey<WebshopservicelevelsRecord, WebshopRecord> WEBSHOPSERVICELEVELS_FK0 = Internal.createForeignKey(Webshopservicelevels.WEBSHOPSERVICELEVELS, DSL.name("webshopServiceLevels_fk0"), new TableField[] { Webshopservicelevels.WEBSHOPSERVICELEVELS.WEBSHOPID }, Keys.WEBSHOP_PK, new TableField[] { Webshop.WEBSHOP.WEBSHOPID }, true);
    public static final ForeignKey<WebshopsettingsRecord, WebshopRecord> WEBSHOPSETTINGS_FK0 = Internal.createForeignKey(Webshopsettings.WEBSHOPSETTINGS, DSL.name("webshopSettings_fk0"), new TableField[] { Webshopsettings.WEBSHOPSETTINGS.WEBSHOPID }, Keys.WEBSHOP_PK, new TableField[] { Webshop.WEBSHOP.WEBSHOPID }, true);
}
