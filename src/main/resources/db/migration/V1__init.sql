drop table if exists test_schema.users;

create table test_schema.users
(
    id          bigserial       primary key,
    username    varchar(255)    unique
);