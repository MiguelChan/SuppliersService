package com.mgl.suppliersservice.controllers;

import com.mgl.suppliersservice.components.CreateSupplierComponent;
import com.mgl.suppliersservice.components.GetSuppliersComponent;
import com.mgl.suppliersservice.dto.CreateSupplierRequest;
import com.mgl.suppliersservice.dto.CreateSupplierResponse;
import com.mgl.suppliersservice.dto.GetSuppliersResponse;
import com.mgl.suppliersservice.models.Supplier;
import com.mgl.suppliersservice.models.Tuple;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
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

    /**
     * .
     *
     * @param getSuppliersComponent .
     * @param createSupplierComponent .
     */
    @Autowired
    public SuppliersController(GetSuppliersComponent getSuppliersComponent,
                               CreateSupplierComponent createSupplierComponent) {
        this.getSuppliersComponent = getSuppliersComponent;
        this.createSupplierComponent = createSupplierComponent;
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

}
