CREATE TABLE room (
    id bigint not null auto_increment,
    number varchar(255),
    primary key (id)
);

CREATE TABLE room_beds (room_id bigint not null, beds integer);

ALTER TABLE room_beds ADD constraint FKmgdse5awswl23tm83cvmaerh6 foreign key (room_id) references room;

CREATE TABLE guest (
    id bigint not null auto_increment,
    birth_date date,
    first_name varchar(255),
    gender integer,
    last_name varchar(255),
    primary key (id)
);