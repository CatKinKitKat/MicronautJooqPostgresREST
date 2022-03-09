-- PLACE YOUR DATABASE SCHEMA HERE FOR CODE GENERATION

create table example
(
    uuid uuid primary key not null,
    id   bigserial unique not null
);