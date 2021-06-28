package com.mgl.suppliersservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Defines a Contact.
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class Contact {

    private String id;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    private ContactType contactType;

}
