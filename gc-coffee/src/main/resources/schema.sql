
create table products
(
    product_id    bigint   auto_increment primary key,
    product_name  varchar(20)  not null,
    category      varchar(50)  not null,
    price         bigint       not null,
    description   varchar(500) not null,
    created_at    datetime(6)  not null,
    updated_at    datetime(6)  not null
);
