package com.mgl.suppliersservice.controllers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import com.mgl.suppliersservice.components.CreateSupplierComponent;
import com.mgl.suppliersservice.components.DeleteSupplierComponent;
import com.mgl.suppliersservice.components.GetSuppliersComponent;
import com.mgl.suppliersservice.dto.CreateSupplierRequest;
import com.mgl.suppliersservice.dto.CreateSupplierResponse;
import com.mgl.suppliersservice.dto.DeleteSupplierResponse;
import com.mgl.suppliersservice.dto.GetSuppliersResponse;
import com.mgl.suppliersservice.models.Supplier;
import com.mgl.suppliersservice.models.Tuple;
import io.github.benas.randombeans.api.EnhancedRandom;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * .
 */
@ExtendWith(MockitoExtension.class)
public class SuppliersControllerTests {

    @Mock
    private GetSuppliersComponent getSuppliersComponent;
    @Mock
    private CreateSupplierComponent createSupplierComponent;
    @Mock
    private DeleteSupplierComponent deleteSupplierComponent;

    @InjectMocks
    private SuppliersController suppliersController;

    @BeforeEach
    public void setup() {
    }

    @Test
    public void getSuppliers_should_returnTheSuppliers() {
        List<Supplier> expectedSupplierList = EnhancedRandom.randomListOf(5, Supplier.class);
        Tuple<Integer, List<Supplier>> getResponse = Tuple.<Integer, List<Supplier>>builder()
            .rightValue(expectedSupplierList)
            .leftValue(expectedSupplierList.size())
            .build();

        when(getSuppliersComponent.getSuppliers(anyInt(), anyInt())).thenReturn(getResponse);

        GetSuppliersResponse getSuppliersResponse =
            suppliersController.getSuppliers(1, 100);

        List<Supplier> suppliers = getSuppliersResponse.getSuppliers();
        Integer suppliersCount = getSuppliersResponse.getSuppliersCount();

        assertThat(suppliers).isEqualTo(expectedSupplierList);
        assertThat(suppliersCount).isEqualTo(expectedSupplierList.size());
    }

    @Test
    public void createSupplier_should_returnTheCreatedSupplierId() {
        String expectedSupplierId = "NewSupplierId";
        Supplier mockSupplier = EnhancedRandom.random(Supplier.class);
        when(createSupplierComponent.createSupplier(mockSupplier)).thenReturn(expectedSupplierId);

        CreateSupplierRequest request = CreateSupplierRequest.builder()
            .supplier(mockSupplier)
            .build();

        CreateSupplierResponse response = suppliersController.createSupplier(request);

        assertThat(response.getSupplierId()).isEqualTo(expectedSupplierId);
    }

    @Test
    public void deleteSupplier_should_deleteSupplier() {
        String supplierId = "SupplierToDelete";

        when(deleteSupplierComponent.deleteSupplier(supplierId)).thenReturn(true);

        DeleteSupplierResponse response = suppliersController.deleteSupplier(supplierId);

        assertThat(response.isSuccess()).isTrue();
    }

}
