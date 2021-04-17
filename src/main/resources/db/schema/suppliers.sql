create table suppliersdb.suppliers (
	id varchar(10) not null primary key,
	name varchar(100) not null,
	address_line_1 varchar(100),
	address_line_2 varchar(100),
	phone_number varchar(100)
);