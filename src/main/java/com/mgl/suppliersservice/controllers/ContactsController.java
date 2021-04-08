package com.mgl.suppliersservice.controllers;

import com.mgl.suppliersservice.dao.ContactsDao;
import com.mgl.suppliersservice.models.Contact;
import com.mgl.suppliersservice.models.mappers.ContactsMapper;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
    private final ContactsMapper contactsMapper;

    /**
     * .
     *
     * @param contactsDao .
     * @param contactsMapper .
     *
     */
    @Autowired
    public ContactsController(ContactsDao contactsDao, ContactsMapper contactsMapper) {
        this.contactsDao = contactsDao;
        this.contactsMapper = contactsMapper;
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
                .map(contactsMapper::fromEntity)
                .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Exception Found when trying to fetch Suppliers.", e);
            throw e;
        }
    }

}
