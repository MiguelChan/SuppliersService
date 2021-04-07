package com.mgl.suppliersservice.dao.mappers;

import com.mgl.suppliersservice.dao.entities.SupplierEntity;
import java.util.List;

/**
 * .
 */
public interface SuppliersMapper {

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
