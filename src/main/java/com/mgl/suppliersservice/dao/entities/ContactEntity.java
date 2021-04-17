package com.mgl.suppliersservice.dao.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Defines the {@link com.mgl.suppliersservice.models.Contact} Model that can be stored
 * in the Database.
 */
@Builder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactEntity {

    private String id;
    private String contactType;
    private String phoneNumber;
    private String emailAddress;
    private String contactFirstName;
    private String contactLastName;
    private String supplierId;

}
