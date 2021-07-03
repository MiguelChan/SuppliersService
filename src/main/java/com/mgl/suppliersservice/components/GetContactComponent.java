package com.mgl.suppliersservice.components;

import com.mgl.suppliersservice.dao.ContactsDao;
import com.mgl.suppliersservice.dao.entities.ContactEntity;
import com.mgl.suppliersservice.models.Contact;
import com.mgl.suppliersservice.models.mappers.ContactsEntityMapper;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * .
 */
@Log4j2
@Component
public class GetContactComponent {

    private final ContactsDao contactsDao;
    private final ContactsEntityMapper contactsEntityMapper;

    @Autowired
    public GetContactComponent(ContactsDao contactsDao,
                               ContactsEntityMapper contactsEntityMapper) {
        this.contactsDao = contactsDao;
        this.contactsEntityMapper = contactsEntityMapper;
    }

    /**
     * .
     *
     * @param contactId .
     * @return .
     */
    public Optional<Contact> getContact(String contactId) {
        log.info("Attempting to fetch Contact with Id: {}", contactId);
        try {
            ContactEntity contactEntity = contactsDao.getContact(contactId);
            Contact contact = contactsEntityMapper.fromEntity(contactEntity);
            return Optional.of(contact);
        } catch (Exception e) {
            log.error("An error occurred when trying to fetch contact", e);
            return Optional.empty();
        }
    }

}
