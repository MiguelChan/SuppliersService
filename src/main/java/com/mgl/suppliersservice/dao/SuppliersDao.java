package com.mgl.suppliersservice.dao;

import com.mgl.suppliersservice.dao.entities.SupplierEntity;
import java.util.List;

/**
 * DAO For Suppliers.
 */
public interface SuppliersDao {

    /**
     * .
     *
     * @param pageSize .
     * @param pageNumber .
     *
     * @return .
     */
    List<SupplierEntity> getSuppliers(int pageSize, int pageNumber) throws Exception;

    /**
     * Gets the count of all the Suppliers in the DB.
     *
     * @return .
     *
     * @throws Exception .
     */
    int getSuppliersCount() throws Exception;

    /**
     * Creates the supplier in the Database.
     *
     * @param supplierEntity The supplier to create.
     *
     * @return The ID of the new Supplier.
     */
    String createSupplier(SupplierEntity supplierEntity) throws Exception;

    /**
     * Deletes a Supplier from the Persistance Storage.
     *
     * @param supplierId The supplierId to delete.
     *
     * @throws Exception .
     */
    void deleteSupplier(String supplierId) throws Exception;

}
