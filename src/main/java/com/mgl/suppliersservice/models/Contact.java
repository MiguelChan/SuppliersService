package com.mgl.suppliersservice.models;

import lombok.Builder;
import lombok.Data;

/**
 * Defines a Contact.
 */
@Data
@Builder(toBuilder = true)
public class Contact {

    private final String id;
    private final String firstName;
    private final String lastName;
    private final String emailAddress;
    private final String phoneNumber;
    private final ContactType contactType;

}
