package com.mgl.suppliersservice.dao.entities;

import lombok.Builder;
import lombok.Data;

/**
 * .
 */
@Builder
@Data
public class SupplierEntity {

    private String id;
    private String name;
    private String phoneNumber;
    private String addressLine1;
    private String addressLine2;

}
