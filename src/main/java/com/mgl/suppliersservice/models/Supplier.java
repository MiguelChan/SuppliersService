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

    private String id;
    private String name;
    private String phoneNumber;
    private String lineAddress1;
    private String lineAddress2;
    private List<Contact> contacts;

}
