package com.mgl.suppliersservice.dao.mappers;

import com.mgl.suppliersservice.dao.entities.SupplierEntity;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * .
 */
@Mapper
public interface SuppliersMapper {

    /**
     * .
     *
     * @param pageSize The number of rows per page.
     * @param offset Where the count should start.
     *
     * @return .
     */
    List<SupplierEntity> getSuppliers(int pageSize, int offset);

    /**
     * Gets the total number of suppliers stored.
     *
     * @return The total count of suppliers.
     */
    int getSuppliersCount();

    /**
     * .
     *
     * @param supplierEntity .
     *
     */
    void insertSupplier(SupplierEntity supplierEntity);

    /**
     * .
     *
     * @param supplierId .
     *
     */
    void deleteSupplier(String supplierId);

}
