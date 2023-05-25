create table user_status(
	id smallserial primary key,
	status varchar(50) unique
);

create table Users(
	id bigserial primary key,
	id_istu bigint unique NULL,
	fullname text,
	studentGroup varchar(15) NULL,
	institute varchar(20) NULL,
	status smallint references user_status(id)
);

create table event_organisators(
	id bigserial primary key,
	organisator bigint references Users(id),
	isMain bool DEFAULT '0'
);

create table events(
	id bigserial primary key,
	organisator bigint[],
	image text,
	title text,
	description text,
	tags text[],
	date timestamp,
	location text,
	isOpen bool default '1',
	regstrationDate daterange 
);

create table my_event(
	id serial primary key,
	event bigint references events(id),
	id_user bigint references Users(id),
	presence bool default '0',
	qr text,
	uid text
);