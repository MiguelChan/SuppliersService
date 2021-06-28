package com.mgl.suppliersservice.controllers;

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
import java.util.List;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * .
 */
@Log4j2
@RestController
@RequestMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class SuppliersController {

    private final GetSuppliersComponent getSuppliersComponent;
    private final CreateSupplierComponent createSupplierComponent;
    private final DeleteSupplierComponent deleteSupplierComponent;
    private final GetSupplierComponent getSupplierComponent;

    /**
     * .
     *
     * @param getSuppliersComponent .
     * @param createSupplierComponent .
     * @param deleteSupplierComponent .
     * @param getSupplierComponent .
     */
    @Autowired
    public SuppliersController(GetSuppliersComponent getSuppliersComponent,
                               CreateSupplierComponent createSupplierComponent,
                               DeleteSupplierComponent deleteSupplierComponent,
                               GetSupplierComponent getSupplierComponent) {
        this.getSuppliersComponent = getSuppliersComponent;
        this.createSupplierComponent = createSupplierComponent;
        this.deleteSupplierComponent = deleteSupplierComponent;
        this.getSupplierComponent = getSupplierComponent;
    }

    /**
     * .
     *
     * @param pageSize .
     * @param pageNumber .
     *
     * @return .
     */
    @GetMapping("/suppliers")
    public GetSuppliersResponse getSuppliers(
        @RequestParam(required = false, defaultValue = "100") int pageSize,
        @RequestParam(required = false, defaultValue = "0") int pageNumber) {

        Tuple<Integer, List<Supplier>> getSuppliersResponse =
            getSuppliersComponent.getSuppliers(pageSize, pageNumber);

        return GetSuppliersResponse.builder()
            .suppliers(getSuppliersResponse.getRightValue())
            .suppliersCount(getSuppliersResponse.getLeftValue())
            .build();
    }

    /**
     * .
     *
     * @param request .
     *
     * @return .
     */
    @PostMapping("/suppliers")
    public CreateSupplierResponse createSupplier(@RequestBody CreateSupplierRequest request) {
        Supplier supplierToCreate = request.getSupplier();

        String supplierId = createSupplierComponent.createSupplier(supplierToCreate);

        return CreateSupplierResponse.builder()
            .supplierId(supplierId)
            .build();
    }

    /**
     * .
     *
     * @param supplierId The id of the supplier to delete.
     * @return The response.
     */
    @DeleteMapping("/suppliers/{supplierId}")
    public DeleteSupplierResponse deleteSupplier(@PathVariable String supplierId) {
        boolean isSuccess = deleteSupplierComponent.deleteSupplier(supplierId);

        return DeleteSupplierResponse.builder()
            .isSuccess(isSuccess)
            .build();
    }

    /**
     * Gets a Supplier by its Id.
     *
     * @param supplierId .
     *
     * @return .
     */
    @GetMapping("/suppliers/{supplierId}")
    public GetSupplierResponse getSupplier(@PathVariable String supplierId) {
        Optional<Supplier> foundSupplier = getSupplierComponent.getSupplier(supplierId);

        return GetSupplierResponse.builder()
            .supplier(foundSupplier.orElseGet(() -> null))
            .build();
    }

}
