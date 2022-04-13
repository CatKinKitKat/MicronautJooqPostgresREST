CREATE TABLE "webshop"
(
	"webshop_id"    BIGSERIAL    NOT NULL,
	"handle"        VARCHAR(32)  NOT NULL UNIQUE,
	"url"           VARCHAR(128) NOT NULL UNIQUE,
	"interest_rate" SMALLINT     NOT NULL DEFAULT '20',
	"a"             FLOAT        NOT NULL,
	"b"             FLOAT        NOT NULL,
	"c"             FLOAT        NOT NULL,
	"currency"      VARCHAR(3)   NOT NULL DEFAULT 'EUR',
	"run_jobs"      BOOLEAN      NOT NULL DEFAULT 'true',
	"multi_supply"  BOOLEAN      NOT NULL DEFAULT 'false',
	CONSTRAINT "webshop_pk" PRIMARY KEY ("webshop_id")
);

CREATE TABLE "webshopemails"
(
	"address_id" BIGSERIAL    NOT NULL,
	"webshop_id" BIGINT       NOT NULL,
	"address"    VARCHAR(128) NOT NULL,
	CONSTRAINT "webshopemails_pk" PRIMARY KEY ("address_id")
);

ALTER TABLE "webshopemails"
	ADD CONSTRAINT "webshopemails_fk0" FOREIGN KEY ("webshop_id") REFERENCES "webshop" ("webshop_id");
