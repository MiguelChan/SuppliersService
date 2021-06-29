package com.mgl.suppliersservice.controllers;

import com.mgl.suppliersservice.components.DeleteContactComponent;
import com.mgl.suppliersservice.dao.ContactsDao;
import com.mgl.suppliersservice.dto.DeleteContactResponse;
import com.mgl.suppliersservice.models.Contact;
import com.mgl.suppliersservice.models.mappers.ContactsEntityMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * .
 */
@Log4j2
@RestController
@RequestMapping("/")
public class ContactsController {

    private final ContactsDao contactsDao;
    private final ContactsEntityMapper contactsEntityMapper;
    private final DeleteContactComponent deleteContactComponent;

    /**
     * .
     *
     * @param contactsDao .
     * @param contactsEntityMapper .
     *
     */
    @Autowired
    public ContactsController(ContactsDao contactsDao,
                              ContactsEntityMapper contactsEntityMapper,
                              DeleteContactComponent deleteContactComponent) {
        this.contactsDao = contactsDao;
        this.contactsEntityMapper = contactsEntityMapper;
        this.deleteContactComponent = deleteContactComponent;
    }

    /**
     * .
     *
     * @param supplierId .
     *
     * @return .
     */
    @GetMapping
    @RequestMapping("/contacts")
    public List<Contact> getContacts(@RequestParam String supplierId) {
        try {
            return contactsDao.getContactsForSupplier(supplierId)
                .stream()
                .map(contactsEntityMapper::fromEntity)
                .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Exception Found when trying to fetch Suppliers.", e);
            throw e;
        }
    }

    /**
     * .
     *
     * @param contactId .
     *
     * @return .
     */
    @DeleteMapping
    @RequestMapping("/contacts/{contactId}")
    public DeleteContactResponse deleteContact(@PathVariable String contactId) {
        Optional<Contact> deletedContact = deleteContactComponent.deleteContact(contactId);
        return DeleteContactResponse.builder()
            .contact(deletedContact.orElse(null))
            .deleted(deletedContact.isPresent())
            .build();
    }

}
