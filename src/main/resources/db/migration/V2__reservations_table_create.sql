CREATE TABLE reservation (
    id bigint not null auto_increment,
    confirmed boolean not null,
    creation_date timestamp,
    email varchar(255),
    from_date date,
    to_date date,
    owner_id bigint,
    room_id bigint,
    primary key (id)
);

ALTER TABLE reservation ADD constraint FK50156niokhqlv60kjalwhi9wi foreign key (owner_id) references guest(id);

ALTER TABLE reservation ADD constraint FKm8xumi0g23038cw32oiva2ymw foreign key (room_id) references room(id);