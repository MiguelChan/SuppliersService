package com.mgl.suppliersservice.models.mappers;

import com.mgl.suppliersservice.dao.entities.ContactEntity;
import com.mgl.suppliersservice.models.Contact;
import com.mgl.suppliersservice.models.ContactType;
import org.springframework.stereotype.Component;

/**
 * .
 */
@Component
public class ContactsEntityMapper {

    /**
     * .
     *
     * @param contactEntity .
     *
     * @return .
     */
    public Contact fromEntity(ContactEntity contactEntity) {
        return Contact.builder()
            .id(contactEntity.getId())
            .firstName(contactEntity.getContactFirstName())
            .lastName(contactEntity.getContactLastName())
            .emailAddress(contactEntity.getEmailAddress())
            .phoneNumber(contactEntity.getPhoneNumber())
            .contactType(ContactType.valueOf(contactEntity.getContactType()))
            .build();
    }

    /**
     * .
     *
     * @param contact .
     * @param supplierId .
     *
     * @return .
     */
    public ContactEntity fromModel(Contact contact, String supplierId) {
        return ContactEntity.builder()
            .id(contact.getId())
            .contactType(contact.getContactType().getType())
            .phoneNumber(contact.getPhoneNumber())
            .emailAddress(contact.getEmailAddress())
            .contactFirstName(contact.getFirstName())
            .contactLastName(contact.getLastName())
            .supplierId(supplierId)
            .build();
    }

}
