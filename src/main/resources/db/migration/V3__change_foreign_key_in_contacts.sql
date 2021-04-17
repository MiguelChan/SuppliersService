ALTER TABLE suppliersdb.contacts DROP CONSTRAINT fk_supplier_id;

ALTER TABLE suppliersdb.contacts
ADD CONSTRAINT fk_supplier_id
FOREIGN KEY (supplier_id)
REFERENCES suppliersdb.suppliers(id)
ON DELETE CASCADE
ON UPDATE CASCADE;