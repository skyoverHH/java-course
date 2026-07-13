create table if not exists test_schema.users
(
    id          bigserial       primary key,
    username    varchar(255)    unique
);

insert into users
(id, username)
values
    (1, 'First'),
    (2, 'Second'),
    (3, 'Third'),
    (4, 'Forth');