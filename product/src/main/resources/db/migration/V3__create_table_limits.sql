create table if not exists test_schema.limits
(
    id              bigserial       primary key,
    user_id         bigserial       not null unique,
    limit_amount    numeric(18, 2)  not null
);

do $$
begin
for i in 1..100 loop
                insert into test_schema.limits (id, user_id, limit_amount)
                values (i, i, 10000)
                on conflict (user_id) do nothing;
end loop;
end $$;