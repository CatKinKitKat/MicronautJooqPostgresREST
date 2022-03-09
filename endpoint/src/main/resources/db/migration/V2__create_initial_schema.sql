-- only serves as an example, remove this when using this template
create table example
(
    uuid uuid primary key default uuid_generate_v4() not null,
    id   bigserial unique                            not null
);