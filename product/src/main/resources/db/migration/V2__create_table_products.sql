create table if not exists test_schema.products
(
    id              bigserial       primary key,
    account_number  varchar(32)     unique,
    balance         numeric(18, 2),
    type            varchar(255),
    user_id         bigint          references users(id) on delete cascade
);

insert into products
(id, account_number, balance, type, user_id)
values
    (1, '1111', 20, 'account', 1),
    (2, '2222', 30, 'card', 2),
    (3, '3333', 10, 'account', 2),
    (4, '4444', 45, 'card', 3),
    (5, '5555', 55, 'account', 3),
    (6, '6666', 35, 'card', 4);