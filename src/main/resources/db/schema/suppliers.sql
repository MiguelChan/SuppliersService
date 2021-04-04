create table suppliersdb.suppliers (
	id varchar(10) not null primary key,
	name varchar(100) not null,
	address_line_1 varchar(100),
	address_line_2 varchar(100),
	phone_number varchar(100)
);

create table suppliersdb.suppliers_to_contacts (
	supplier_id varchar(100) not null,
	contact_id varchar(100) not null,
	primary key(supplier_id, contact_id),
	constraint fk_supplier foreign key(supplier_id) references suppliersdb.suppliers(id) on delete cascade,
	constraint fk_contact_id foreign key(contact_id) references suppliersdb.contacts(id) on delete cascade
);