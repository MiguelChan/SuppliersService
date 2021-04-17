package com.mgl.suppliersservice.components;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import com.mgl.suppliersservice.dao.SuppliersDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * .
 */
@ExtendWith(MockitoExtension.class)
public class DeleteSupplierComponentTests {

    @Mock
    private SuppliersDao suppliersDao;

    private DeleteSupplierComponent deleteSupplierComponent;

    @BeforeEach
    public void setup() {
        deleteSupplierComponent = new DeleteSupplierComponent(suppliersDao);
    }

    @Test
    public void deleteSupplier_should_delete() throws Exception {
        String supplierId = "SomeSupplierId";

        boolean deleted = deleteSupplierComponent.deleteSupplier(supplierId);

        assertThat(deleted).isTrue();
        verify(suppliersDao).deleteSupplier(supplierId);
    }

    @Test
    public void deleteSupplier_should_catchException_when_daoFails() throws Exception {
        String supplierId = "SomeSupplierId";

        doThrow(RuntimeException.class).when(suppliersDao).deleteSupplier(supplierId);

        boolean deleted = deleteSupplierComponent.deleteSupplier(supplierId);

        assertThat(deleted).isFalse();
        verify(suppliersDao).deleteSupplier(supplierId);
    }

}
