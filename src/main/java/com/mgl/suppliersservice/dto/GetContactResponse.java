package com.mgl.suppliersservice.dto;

import com.mgl.suppliersservice.models.Contact;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * .
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class GetContactResponse {

    private Contact contact;

}
