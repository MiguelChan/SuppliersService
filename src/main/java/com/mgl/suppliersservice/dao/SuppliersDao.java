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
    List<SupplierEntity> getSuppliers(int pageSize, int pageNumber);

}
