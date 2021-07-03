package com.mgl.suppliersservice.controllers;

import com.mgl.suppliersservice.components.DeleteContactComponent;
import com.mgl.suppliersservice.components.EditContactComponent;
import com.mgl.suppliersservice.components.GetContactComponent;
import com.mgl.suppliersservice.components.GetContactsForSupplierComponent;
import com.mgl.suppliersservice.dto.DeleteContactResponse;
import com.mgl.suppliersservice.dto.EditContactRequest;
import com.mgl.suppliersservice.dto.EditContactResponse;
import com.mgl.suppliersservice.dto.GetContactResponse;
import com.mgl.suppliersservice.dto.GetContactsForSupplierResponse;
import com.mgl.suppliersservice.models.Contact;
import java.util.List;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * .
 */
@Log4j2
@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class ContactsController {

    private final DeleteContactComponent deleteContactComponent;
    private final EditContactComponent editContactComponent;
    private final GetContactsForSupplierComponent getContactsForSupplierComponent;
    private final GetContactComponent getContactComponent;

    /**
     * .
     *
     * @param getContactsForSupplierComponent .
     * @param deleteContactComponent .
     * @param editContactComponent .
     */
    @Autowired
    public ContactsController(GetContactsForSupplierComponent getContactsForSupplierComponent,
                              DeleteContactComponent deleteContactComponent,
                              EditContactComponent editContactComponent,
                              GetContactComponent getContactComponent) {
        this.getContactsForSupplierComponent = getContactsForSupplierComponent;
        this.deleteContactComponent = deleteContactComponent;
        this.editContactComponent = editContactComponent;
        this.getContactComponent = getContactComponent;
    }

    /**
     * .
     *
     * @param supplierId .
     *
     * @return .
     */
    @GetMapping("/contacts")
    public GetContactsForSupplierResponse getContacts(@RequestParam String supplierId) {
        List<Contact> contacts = getContactsForSupplierComponent.getContactsForSupplier(supplierId);
        return GetContactsForSupplierResponse.builder()
            .contacts(contacts)
            .build();
    }

    /**
     * .
     *
     * @param contactId .
     *
     * @return .
     */
    @DeleteMapping("/contacts/{contactId}")
    public DeleteContactResponse deleteContact(@PathVariable String contactId) {
        Optional<Contact> deletedContact = deleteContactComponent.deleteContact(contactId);
        return DeleteContactResponse.builder()
            .contact(deletedContact.orElse(null))
            .deleted(deletedContact.isPresent())
            .build();
    }

    /**
     * .
     *
     * @param contactId .
     * @param request .
     * @return .
     */
    @PutMapping("/contacts/{contactId}")
    public EditContactResponse editContact(@PathVariable String contactId,
                                           @RequestBody EditContactRequest request) {
        Contact contact = request.getContact();
        return editContactComponent.editContact(contact);
    }

    /**
     * .
     *
     * @param contactId .
     * @return .
     */
    @GetMapping("/contacts/{contactId}")
    public GetContactResponse getContact(@PathVariable String contactId) {
        Optional<Contact> contactOpt = getContactComponent.getContact(contactId);
        return GetContactResponse.builder()
            .contact(contactOpt.orElseGet(() -> null))
            .build();
    }

}
