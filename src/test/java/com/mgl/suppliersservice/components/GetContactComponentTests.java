package com.mgl.suppliersservice.components;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.mgl.suppliersservice.dao.ContactsDao;
import com.mgl.suppliersservice.dao.entities.ContactEntity;
import com.mgl.suppliersservice.models.Contact;
import com.mgl.suppliersservice.models.mappers.ContactsEntityMapper;
import io.github.benas.randombeans.api.EnhancedRandom;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * .
 */
@ExtendWith(MockitoExtension.class)
public class GetContactComponentTests {

    @Mock
    private ContactsDao contactsDao;
    @Mock
    private ContactsEntityMapper contactsEntityMapper;

    private GetContactComponent component;

    @BeforeEach
    public void setup() {
        component = new GetContactComponent(contactsDao, contactsEntityMapper);
    }

    @Test
    public void getContact_should_returnTheContact() {
        String contactId = "SomeContactId";

        ContactEntity contactEntity = EnhancedRandom.random(ContactEntity.class);
        Contact expectedContact = EnhancedRandom.random(Contact.class);

        when(contactsDao.getContact(contactId)).thenReturn(contactEntity);
        when(contactsEntityMapper.fromEntity(contactEntity)).thenReturn(expectedContact);

        Optional<Contact> contactOpt = component.getContact(contactId);

        assertThat(contactOpt).isNotNull();
        assertThat(contactOpt.isPresent()).isTrue();

        Contact contact = contactOpt.get();
        assertThat(contact).isEqualTo(expectedContact);
    }

    @Test
    public void getContact_should_returnEmptyOptional_when_exceptionOccurs() {
        String contactId = "SomeContactId";

        when(contactsDao.getContact(contactId)).thenThrow(RuntimeException.class);

        Optional<Contact> contact = component.getContact(contactId);

        assertThat(contact.isEmpty()).isTrue();
    }

}
