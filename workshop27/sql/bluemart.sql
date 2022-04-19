drop database if exists bluemart;

create schema bluemart;

use bluemart;

create table po (
    order_id int auto_increment not null,
    name varchar(64) not null,
    email varchar(64),
    primary key(order_id)
);

create table lineitem (
    item_id int auto_increment not null,
    quantity int,
    order_id int,
	description varchar(64),
    primary key(item_id),
    constraint fk_order_id
        foreign key(order_id)
        references po(order_id)
);