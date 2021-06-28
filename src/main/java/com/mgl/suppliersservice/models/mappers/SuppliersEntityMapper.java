package com.mgl.suppliersservice.models.mappers;

import com.mgl.suppliersservice.dao.entities.SupplierEntity;
import com.mgl.suppliersservice.models.Supplier;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * .
 */
@Component
@NoArgsConstructor
public class SuppliersEntityMapper {

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

    /**
     * .
     *
     * @param supplier .
     *
     * @return .
     *
     */
    public SupplierEntity fromModel(Supplier supplier) {
        return SupplierEntity.builder()
            .id(supplier.getId())
            .name(supplier.getName())
            .phoneNumber(supplier.getPhoneNumber())
            .addressLine1(supplier.getLineAddress1())
            .addressLine2(supplier.getLineAddress2())
            .build();
    }

}
