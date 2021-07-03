package com.mgl.suppliersservice.dto;

import com.mgl.suppliersservice.models.Supplier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * .
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
public class EditSupplierRequest {

    private Supplier supplier;

}
