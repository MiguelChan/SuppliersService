package com.mgl.suppliersservice.controllers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import com.mgl.suppliersservice.components.CreateSupplierComponent;
import com.mgl.suppliersservice.components.DeleteSupplierComponent;
import com.mgl.suppliersservice.components.GetSupplierComponent;
import com.mgl.suppliersservice.components.GetSuppliersComponent;
import com.mgl.suppliersservice.dto.CreateSupplierRequest;
import com.mgl.suppliersservice.dto.CreateSupplierResponse;
import com.mgl.suppliersservice.dto.DeleteSupplierResponse;
import com.mgl.suppliersservice.dto.GetSupplierResponse;
import com.mgl.suppliersservice.dto.GetSuppliersResponse;
import com.mgl.suppliersservice.models.Supplier;
import com.mgl.suppliersservice.models.Tuple;
import io.github.benas.randombeans.api.EnhancedRandom;
import java.util.List;
import java.util.Optional;
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
    @Mock
    private GetSupplierComponent getSupplierComponent;

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
        Supplier expectedSupplier = EnhancedRandom.random(Supplier.class);

        when(deleteSupplierComponent.deleteSupplier(supplierId)).thenReturn(Optional.of(expectedSupplier));

        DeleteSupplierResponse response = suppliersController.deleteSupplier(supplierId);

        assertThat(response.isDeleted()).isTrue();
        assertThat(response.getSupplier()).isNotNull();
        assertThat(response.getSupplier()).isEqualTo(expectedSupplier);
    }

    @Test
    public void deletedSupplier_should_returnEmptyResponse_when_nothingHappens() {
        String supplierId = "SomeRandomSupplier";
        when(deleteSupplierComponent.deleteSupplier(supplierId)).thenReturn(Optional.empty());

        DeleteSupplierResponse response = suppliersController.deleteSupplier(supplierId);

        assertThat(response).isNotNull();
        assertThat(response.isDeleted()).isFalse();
        assertThat(response.getSupplier()).isNull();;
    }

    @Test
    public void getSupplier_should_returnTheSupplier() {
        String supplierId = "SupplierToGet";
        Supplier expectedSupplier = EnhancedRandom.random(Supplier.class);

        when(getSupplierComponent.getSupplier(supplierId)).thenReturn(Optional.of(expectedSupplier));

        GetSupplierResponse response = suppliersController.getSupplier(supplierId);

        assertThat(response.getSupplier()).isNotNull();
        assertThat(response.getSupplier()).isEqualTo(expectedSupplier);
    }

    @Test
    public void getSupplier_should_returnNull_when_nothingCanBeFound() {
        String supplierId = "SupplierToGet";

        when(getSupplierComponent.getSupplier(supplierId)).thenReturn(Optional.empty());

        GetSupplierResponse response = suppliersController.getSupplier(supplierId);

        assertThat(response).isNotNull();
        assertThat(response.getSupplier()).isNull();
    }
}
