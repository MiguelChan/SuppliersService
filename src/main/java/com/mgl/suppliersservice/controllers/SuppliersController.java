package com.mgl.suppliersservice.controllers;

import com.mgl.suppliersservice.components.GetSuppliersComponent;
import com.mgl.suppliersservice.models.Supplier;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * .
 */
@Log4j2
@RestController
@RequestMapping("/")
public class SuppliersController {

    private final GetSuppliersComponent getSuppliersComponent;

    /**
     * .
     *
     * @param getSuppliersComponent .
     */
    @Autowired
    public SuppliersController(GetSuppliersComponent getSuppliersComponent) {
        this.getSuppliersComponent = getSuppliersComponent;
    }

    /**
     * .
     *
     * @param pageSize .
     * @param pageNumber .
     *
     * @return .
     */
    public List<Supplier> getSuppliers(
        @RequestParam(required = false, defaultValue = "100") int pageSize,
        @RequestParam(required = false, defaultValue = "0") int pageNumber) {
        return getSuppliersComponent.getSuppliers(pageSize, pageNumber);
    }
}
