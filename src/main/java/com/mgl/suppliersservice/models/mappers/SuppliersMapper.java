package com.mgl.suppliersservice.models.mappers;

import com.mgl.suppliersservice.dao.entities.SupplierEntity;
import com.mgl.suppliersservice.models.Supplier;

/**
 * .
 */
public class SuppliersMapper {

    /**
     * .
     *
     * @param supplierEntity .
     *
     * @return .
     */
    public Supplier fromEntity(SupplierEntity supplierEntity) {
        return Supplier.builder()
            .id(supplierEntity.getId())
            .lineAddress1(supplierEntity.getAddressLine1())
            .lineAddress2(supplierEntity.getAddressLine2())
            .name(supplierEntity.getName())
            .phoneNumber(supplierEntity.getPhoneNumber())
            .build();
    }

}
