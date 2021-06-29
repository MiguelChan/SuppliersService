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
 * Component for handling the delete of a Contact.
 */
@Log4j2
@Component
public class DeleteContactComponent {

    private final ContactsDao contactsDao;
    private final ContactsEntityMapper contactsEntityMapper;

    @Autowired
    public DeleteContactComponent(ContactsDao contactsDao,
                                  ContactsEntityMapper contactsEntityMapper) {
        this.contactsDao = contactsDao;
        this.contactsEntityMapper = contactsEntityMapper;
    }

    /**
     * Deletes a Contact based off the provided contactId.
     * Should the contact be deleted without any issues then it is returned in the Optional.
     * An Empty Optional means that either an error occurred or the contact was not found.
     *
     * @param contactId .
     *
     * @return .
     */
    public Optional<Contact> deleteContact(String contactId) {
        log.info("Attempting to delete contact: {}", contactId);
        try {
            ContactEntity contactEntity = contactsDao.getContact(contactId);
            log.info("Found contact: {}", contactEntity);

            contactsDao.deleteContact(contactId);

            Contact foundContact = contactsEntityMapper.fromEntity(contactEntity);
            return Optional.of(foundContact);
        } catch (Exception e) {
            log.error("Error when trying to delete a Contact", e);
            return Optional.empty();
        }
    }

}
