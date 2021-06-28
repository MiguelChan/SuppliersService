package com.mgl.suppliersservice.models.mappers;

import static org.assertj.core.api.Assertions.assertThat;

import com.mgl.suppliersservice.dao.entities.SupplierEntity;
import com.mgl.suppliersservice.models.Supplier;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * .
 */
public class SuppliersEntityMapperTests {

    private SuppliersEntityMapper suppliersEntityMapper;

    @BeforeEach
    public void setup() {
        suppliersEntityMapper = new SuppliersEntityMapper();
    }

    @AfterEach
    public void teardown() {
        suppliersEntityMapper = null;
    }

    @Test
    public void fromEntity_should_convertEntityIntoAppModel() {
        SupplierEntity supplierEntity = EnhancedRandom.random(SupplierEntity.class);

        Supplier supplier = suppliersEntityMapper.fromEntity(supplierEntity);

        assertThat(supplier.getId()).isEqualTo(supplierEntity.getId());
        assertThat(supplier.getName()).isEqualTo(supplierEntity.getName());
        assertThat(supplier.getLineAddress1()).isEqualTo(supplierEntity.getAddressLine1());
        assertThat(supplier.getLineAddress2()).isEqualTo(supplierEntity.getAddressLine2());
        assertThat(supplier.getPhoneNumber()).isEqualTo(supplierEntity.getPhoneNumber());
    }

    @Test
    public void fromModel_should_convertAppModelIntoEntity() {
        Supplier supplier = EnhancedRandom.random(Supplier.class, "contacts");

        SupplierEntity supplierEntity = suppliersEntityMapper.fromModel(supplier);

        assertThat(supplierEntity.getId()).isEqualTo(supplier.getId());
        assertThat(supplierEntity.getName()).isEqualTo(supplier.getName());
        assertThat(supplierEntity.getAddressLine1()).isEqualTo(supplier.getLineAddress1());
        assertThat(supplierEntity.getAddressLine2()).isEqualTo(supplier.getLineAddress2());
        assertThat(supplierEntity.getPhoneNumber()).isEqualTo(supplier.getPhoneNumber());

    }

}
