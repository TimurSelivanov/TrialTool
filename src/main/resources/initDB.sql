drop table if exists tool;
drop table if exists customer;

create table customer
(
    id    int generated by default as identity primary key,
    name  varchar not null,
    phone int     not null unique
);

create table tool
(
    id int generated by default as identity primary key,
    brand varchar not null,
    type varchar not null unique,
    customer_id int references customer (id)on delete set null,
    taken_on timestamp
);