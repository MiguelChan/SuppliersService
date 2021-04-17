DROP TABLE suppliersdb.suppliers_to_contacts;

ALTER TABLE suppliersdb.contacts ADD COLUMN supplier_id varchar(10);
ALTER TABLE suppliersdb.contacts
ADD CONSTRAINT fk_supplier_id FOREIGN KEY (supplier_id) REFERENCES suppliersdb.suppliers (id) ON DELETE CASCADE;