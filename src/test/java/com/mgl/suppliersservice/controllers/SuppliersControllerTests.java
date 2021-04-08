package com.mgl.suppliersservice.controllers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import com.mgl.suppliersservice.components.GetSuppliersComponent;
import com.mgl.suppliersservice.models.Supplier;
import io.github.benas.randombeans.api.EnhancedRandom;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * .
 */
@ExtendWith(MockitoExtension.class)
public class SuppliersControllerTests {

    @Mock
    private GetSuppliersComponent getSuppliersComponent;

    private SuppliersController suppliersController;

    @BeforeEach
    public void setup() {
        suppliersController = new SuppliersController(getSuppliersComponent);
    }

    @Test
    public void getSuppliers_should_returnTheSuppliers() {
        List<Supplier> expectedSupplierList = EnhancedRandom.randomListOf(5, Supplier.class);
        when(getSuppliersComponent.getSuppliers(anyInt(), anyInt())).thenReturn(expectedSupplierList);

        List<Supplier> suppliers = suppliersController.getSuppliers(1, 100);

        assertThat(suppliers).isEqualTo(expectedSupplierList);
    }

    @Test
    public void getSuppliers_should_bubbleUpException() {
        when(getSuppliersComponent.getSuppliers(anyInt(), anyInt())).thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> suppliersController.getSuppliers(100, 1)).isInstanceOfAny(RuntimeException.class);
    }

}
