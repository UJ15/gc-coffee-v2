
create table products
(
    product_id    bigint   not null auto_increment primary key,
    product_name  varchar(20)  not null,
    category      varchar(50)  not null,
    price         bigint       not null,
    description   varchar(500) not null,
    created_at    datetime(6)  not null,
    updated_at    datetime(6)  not null
);

create table orders
(
    order_id     bigint         not null auto_increment primary key,
    email        VARCHAR(50)    not null,
    address      VARCHAR(200)   not null,
    postcode     VARCHAR(200)   not null,
    order_status VARCHAR(50)    not null,
    created_at    datetime(6)    not null,
    updated_at   datetime(6)    not null
);

create table order_items
(
    seq         bigint      not null primary key auto_increment,
    order_id    bigint      not null,
    product_id  bigint      not null,
    category    VARCHAR(50) not null,
    price       bigint      not null,
    quantity    int         not null,
    created_at  datetime(6) not null,
    updated_at  datetime(6) default null,
    INDEX (order_id),
    CONSTRAINT fk_order_items_to_order FOREIGN KEY (order_id) REFERENCES orders (order_id) ON DELETE CASCADE,
    CONSTRAINT fk_order_items_to_product FOREIGN KEY (product_id) REFERENCES products (product_id)
);


