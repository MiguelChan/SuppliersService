package com.mgl.suppliersservice.dto;

import com.mgl.suppliersservice.models.Contact;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A request for edit a Contact.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class EditContactRequest {

    private Contact contact;

}
