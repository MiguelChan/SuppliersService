package com.mgl.suppliersservice.dto;

import com.mgl.suppliersservice.models.Supplier;
import lombok.Builder;
import lombok.Data;

/**
 * The DTO for fetching a single supplier.
 */
@Builder(toBuilder = true)
@Data
public class GetSupplierResponse {

    private final Supplier supplier;

}
