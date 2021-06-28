package com.mgl.suppliersservice.models.mappers;

import static org.assertj.core.api.Assertions.assertThat;

import com.mgl.suppliersservice.dao.entities.ContactEntity;
import com.mgl.suppliersservice.models.Contact;
import com.mgl.suppliersservice.models.ContactType;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * .
 */
public class ContactsEntityMapperTests {

    private ContactsEntityMapper contactsEntityMapper;

    @BeforeEach
    public void setup() {
        contactsEntityMapper = new ContactsEntityMapper();
    }

    @AfterEach
    public void cleanup() {
        contactsEntityMapper = null;
    }

    @Test
    public void fromEntity_should_convertEntityIntoAppModel() {
        ContactEntity randomEntity = EnhancedRandom.random(ContactEntity.class, "contactType");
        randomEntity.setContactType(ContactType.RETURNS.name());

        Contact contact = contactsEntityMapper.fromEntity(randomEntity);

        assertThat(contact.getId()).isEqualTo(randomEntity.getId());
        assertThat(contact.getFirstName()).isEqualTo(randomEntity.getContactFirstName());
        assertThat(contact.getLastName()).isEqualTo(randomEntity.getContactLastName());
        assertThat(contact.getEmailAddress()).isEqualTo(randomEntity.getEmailAddress());
        assertThat(contact.getPhoneNumber()).isEqualTo(randomEntity.getPhoneNumber());
        assertThat(contact.getContactType().getType()).isEqualTo(randomEntity.getContactType());
    }

    @Test
    public void fromModel_should_convertAppModelIntoEntity() {
        String testSupplierId = EnhancedRandom.random(String.class);
        Contact contact = EnhancedRandom.random(Contact.class, "contactType");
        contact.setContactType(ContactType.RETURNS);

        ContactEntity contactEntity = contactsEntityMapper.fromModel(contact, testSupplierId);

        assertThat(contactEntity.getId()).isEqualTo(contact.getId());
        assertThat(contactEntity.getContactType()).isEqualTo(contact.getContactType().getType());
        assertThat(contactEntity.getPhoneNumber()).isEqualTo(contact.getPhoneNumber());
        assertThat(contactEntity.getEmailAddress()).isEqualTo(contact.getEmailAddress());
        assertThat(contactEntity.getContactFirstName()).isEqualTo(contact.getFirstName());
        assertThat(contactEntity.getContactLastName()).isEqualTo(contact.getLastName());
        assertThat(contactEntity.getSupplierId()).isEqualTo(testSupplierId);
    }

}
