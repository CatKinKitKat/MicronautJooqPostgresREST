CREATE TABLE "webshop"
(
	"webshopId"    BIGSERIAL    NOT NULL,
	"handle"       VARCHAR(32)  NOT NULL UNIQUE,
	"url"          VARCHAR(128) NOT NULL UNIQUE,
	"interestRate" SMALLINT     NOT NULL DEFAULT '20',
	"A"            FLOAT        NOT NULL,
	"B"            FLOAT        NOT NULL,
	"C"            FLOAT        NOT NULL,
	"currency"     VARCHAR(3)   NOT NULL DEFAULT 'EUR',
	"runJobs"      BOOLEAN      NOT NULL DEFAULT 'true',
	"multiSupply"  BOOLEAN      NOT NULL DEFAULT 'false',
	CONSTRAINT "webshop_pk" PRIMARY KEY ("webshopId")
);

CREATE TABLE "webshopEmails"
(
	"addressId" BIGSERIAL    NOT NULL,
	"webshopId" BIGINT       NOT NULL,
	"address"   VARCHAR(128) NOT NULL,
	CONSTRAINT "webshopEmails_pk" PRIMARY KEY ("addressId")
);

ALTER TABLE "webshopEmails"
	ADD CONSTRAINT "webshopEmails_fk0" FOREIGN KEY ("webshopId") REFERENCES "webshop" ("webshopId");
