package com.mgl.suppliersservice.dao.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * .
 */
@Builder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierEntity {

    private String id;
    private String name;
    private String phoneNumber;
    private String addressLine1;
    private String addressLine2;

}
