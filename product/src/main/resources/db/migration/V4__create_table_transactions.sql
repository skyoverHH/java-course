create table if not exists test_schema.transactions
(
    id              bigserial       primary key,
    user_id         bigserial       not null,
    payment_id      uuid            not null unique,
    amount          numeric(18, 2)  not null
);