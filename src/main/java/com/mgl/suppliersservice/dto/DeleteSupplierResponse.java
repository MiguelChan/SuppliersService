package com.mgl.suppliersservice.dto;

import com.mgl.suppliersservice.models.Supplier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * .
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DeleteSupplierResponse {

    private boolean deleted;
    private Supplier supplier;

}
