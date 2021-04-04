create table suppliersdb.contacts (
	id varchar(10) not null primary key,
	contact_type varchar(10) not null,
	phone_number varchar not null,
	email_address varchar(100),
	contact_first_name varchar(100) not null,
	contact_last_name varchar(100)
);