CREATE TABLE guest (id bigint not null auto_increment, birth_date date, customer_id varchar(255), first_name varchar(255), gender integer, last_name varchar(255),  primary key (id))

CREATE TABLE reservation (id bigint not null auto_increment, confirmed boolean not null, creation_date timestamp, email varchar(255), from_date date, to_date date, owner_id bigint, room_id bigint, primary key (id))

CREATE TABLE room (id bigint not null auto_increment, number varchar(255), description varchar(255), primary key (id))

CREATE TABLE room_beds (room_id bigint not null, beds integer)

ALTER TABLE reservation ADD constraint FK50156niokhqlv60kjalwhi9wi foreign key (owner_id) references guest

ALTER TABLE reservation ADD constraint FKm8xumi0g23038cw32oiva2ymw foreign key (room_id) references room

ALTER TABLE room_beds ADD constraint FKmgdse5awswl23tm83cvmaerh6 foreign key (room_id) references room
