package com.mgl.suppliersservice.models;

import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * .
 */
@Data
@Builder(toBuilder = true)
public class Supplier {

    private final String id;
    private final String name;
    private final String phoneNumber;
    private final String lineAddress1;
    private final String lineAddress2;
    private final List<Contact> contacts;

}
