CREATE TABLE "webshop"
(
    "webshopId"    BIGSERIAL    NOT NULL,
    "handle"       VARCHAR(32)  NOT NULL UNIQUE,
    "url"          VARCHAR(128) NOT NULL UNIQUE,
    "interestRate" SMALLINT     NOT NULL DEFAULT '20',
    CONSTRAINT "webshop_pk" PRIMARY KEY ("webshopId")
);

CREATE TABLE "webshopEmails"
(
    "addressId" BIGSERIAL    NOT NULL,
    "webshopId" BIGINT       NOT NULL,
    "address"   VARCHAR(128) NOT NULL,
    CONSTRAINT "webshopEmails_pk" PRIMARY KEY ("addressId")
);

CREATE TABLE "webshopServiceLevels"
(
    "webshopId" BIGINT NOT NULL UNIQUE,
    "slcA"      FLOAT  NOT NULL,
    "slcB"      FLOAT  NOT NULL,
    "slcC"      FLOAT  NOT NULL,
    CONSTRAINT "webshopServiceLevels_pk" PRIMARY KEY ("webshopId")
);

CREATE TABLE "webshopSettings"
(
    "webshopId"   BIGINT     NOT NULL,
    "currency"    VARCHAR(3) NOT NULL DEFAULT 'EUR',
    "runJobs"     BOOLEAN    NOT NULL DEFAULT 'true',
    "multiSupply" BOOLEAN    NOT NULL DEFAULT 'false',
    CONSTRAINT "webshopSettings_pk" PRIMARY KEY ("webshopId")
);

ALTER TABLE "webshopEmails"
    ADD CONSTRAINT "webshopEmails_fk0" FOREIGN KEY ("webshopId") REFERENCES "webshop" ("webshopId");

ALTER TABLE "webshopServiceLevels"
    ADD CONSTRAINT "webshopServiceLevels_fk0" FOREIGN KEY ("webshopId") REFERENCES "webshop" ("webshopId");

ALTER TABLE "webshopSettings"
    ADD CONSTRAINT "webshopSettings_fk0" FOREIGN KEY ("webshopId") REFERENCES "webshop" ("webshopId");