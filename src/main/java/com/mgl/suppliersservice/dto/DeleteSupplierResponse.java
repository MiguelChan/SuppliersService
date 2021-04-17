package com.mgl.suppliersservice.dto;

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

    private boolean isSuccess;
    private String message;

}
