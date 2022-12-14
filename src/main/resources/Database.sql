drop table store_img;
drop table product_img;
drop table member_img;
drop table order_detail;
drop table order_master;
drop table product;
drop table administrator;
drop table store;
drop table member;
commit;


create table member
(
    member_id    bigint unsigned primary key AUTO_INCREMENT,
    member_email VARCHAR(60) not null unique key,
    password     VARCHAR(40) not null,
    member_name  VARCHAR(40) not null,
    gender       VARCHAR(40) not null,
    birthday     DATE not null,
    address      VARCHAR(100) not null ,
    phone        VARCHAR(40) not null,
    create_time  timestamp default current_timestamp,
    modify_time  timestamp default current_timestamp on update current_timestamp
);

create table store
(
    store_id    BIGINT unsigned primary key AUTO_INCREMENT,
    store_name  VARCHAR(40) not null,
    store_phone VARCHAR(40) not null,
    intro       TEXT not null ,
    create_time timestamp default current_timestamp,
    modify_time timestamp default current_timestamp on update current_timestamp
);
create table administrator
(
    member_id   BIGINT unsigned,
    store_id    BIGINT unsigned,
    permission  VARCHAR(40) not null,
    create_time timestamp default current_timestamp,
    modify_time timestamp default current_timestamp on update current_timestamp,
    foreign key (member_id) references member (member_id),
    foreign key (store_id) references store (store_id),
    primary key (member_id, store_id)
);
create table product
(
    product_id    BIGINT unsigned primary key AUTO_INCREMENT,
    store_id      BIGINT unsigned,
    category      VARCHAR(40) not null,
    product_name  VARCHAR(40) not null,
    product_price INT         not null,
    info          TEXT not null ,
    stock         INT         not null,
    status        VARCHAR(40) not null,
    create_time   timestamp default current_timestamp,
    modify_time   timestamp default current_timestamp on update current_timestamp,
    foreign key (store_id) references store (store_id)
);
create table order_master
(
    order_id    BIGINT unsigned primary key AUTO_INCREMENT,
    member_id   BIGINT unsigned,
    store_id    BIGINT unsigned,
    status      VARCHAR(40) not null,
    total_price INT         not null,
    create_time  timestamp default current_timestamp,
    modify_time timestamp default current_timestamp on update current_timestamp,
    foreign key (store_id) references store (store_id),
    foreign key (member_id) references member (member_id)
);
create table order_detail
(
    order_id    BIGINT unsigned,
    product_id  BIGINT unsigned,
    quantity    INT not null,
    od_price    INT not null,
    create_time  timestamp default current_timestamp,
    modify_time timestamp default current_timestamp on update current_timestamp,
    foreign key (order_id) references order_master (order_id),
    foreign key (product_id) references product (product_id)
);
create table member_img
(
    image_id    BIGINT unsigned primary key AUTO_INCREMENT,
    member_id   BIGINT unsigned,
    imgur_url   TEXT not null,
    status      varchar(40) not null ,
    create_time timestamp default current_timestamp,
    modify_time timestamp default current_timestamp on update current_timestamp,
    foreign key (member_id) references member (member_id)
);
create table product_img
(
    image_id    BIGINT unsigned primary key AUTO_INCREMENT,
    product_id  BIGINT unsigned,
    imgur_url   TEXT not null,
    status      varchar(40) not null ,
    create_time timestamp default current_timestamp,
    modify_time timestamp default current_timestamp on update current_timestamp,
    foreign key (product_id) references product (product_id)
);
create table store_img
(
    image_id    BIGINT unsigned primary key AUTO_INCREMENT,
    store_id    BIGINT unsigned,
    imgur_url   TEXT not null,
    status      varchar(40) not null ,
    create_time timestamp default current_timestamp,
    modify_time timestamp default current_timestamp on update current_timestamp,
    foreign key (store_id) references store (store_id)
);

commit;