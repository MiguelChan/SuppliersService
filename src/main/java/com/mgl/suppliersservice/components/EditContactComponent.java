package com.mgl.suppliersservice.components;

import com.mgl.suppliersservice.dao.ContactsDao;
import com.mgl.suppliersservice.dao.entities.ContactEntity;
import com.mgl.suppliersservice.dto.EditContactResponse;
import com.mgl.suppliersservice.models.Contact;
import com.mgl.suppliersservice.models.mappers.ContactsEntityMapper;
import java.util.Objects;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Component for editing a contact.
 */
@Log4j2
@Component
public class EditContactComponent {

    private final ContactsDao contactsDao;
    private final ContactsEntityMapper contactsEntityMapper;

    @Autowired
    public EditContactComponent(ContactsDao contactsDao,
                                ContactsEntityMapper contactsEntityMapper) {
        this.contactsDao = contactsDao;
        this.contactsEntityMapper = contactsEntityMapper;
    }

    /**
     * Edits the provided contact if it exists.
     *
     * @param contact .
     *
     * @return .
     */
    public EditContactResponse editContact(Contact contact) {
        if (Objects.isNull(contact.getId()) || contact.getId().isEmpty()) {
            return EditContactResponse.builder()
                .success(false)
                .message("Contact ID cant be null")
                .build();
        }

        log.info("Attempting to Edit Contact with Id: {}", contact.getId());

        try {
            ContactEntity originalContact = contactsDao.getContact(contact.getId());
            ContactEntity updatedContact =
                contactsEntityMapper.fromModel(contact, originalContact.getSupplierId());

            contactsDao.editContact(updatedContact);

            return EditContactResponse.builder()
                .success(true)
                .build();
        } catch (Exception e) {
            log.error("There was an error while trying to update the Contact.", e);
            return EditContactResponse.builder()
                .success(false)
                .message(e.getMessage())
                .build();
        }
    }

}
