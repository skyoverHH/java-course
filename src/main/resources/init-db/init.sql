\c database

create schema if not exists test_schema;
create table test_schema.users
(
    id          bigserial       primary key,
    username    varchar(255)    unique
);