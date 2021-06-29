package com.mgl.suppliersservice.components;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
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
public class DeleteContactComponentTests {

    private static final String CONTACT_ID = "SomeContactId";

    @Mock
    private ContactsDao contactsDao;
    @Mock
    private ContactsEntityMapper contactsEntityMapper;

    private DeleteContactComponent component;

    @BeforeEach
    public void setup() {
        component = new DeleteContactComponent(contactsDao, contactsEntityMapper);
    }

    @Test
    public void deleteContact_should_deleteAndReturnTheContact() {
        ContactEntity contactEntity = EnhancedRandom.random(ContactEntity.class);
        Contact expectedContact = EnhancedRandom.random(Contact.class);

        when(contactsDao.getContact(CONTACT_ID)).thenReturn(contactEntity);
        when(contactsEntityMapper.fromEntity(contactEntity)).thenReturn(expectedContact);

        Optional<Contact> contactOptional = component.deleteContact(CONTACT_ID);

        assertThat(contactOptional.isPresent()).isTrue();

        Contact foundContact = contactOptional.get();
        assertThat(foundContact).isEqualTo(expectedContact);

        verify(contactsDao).getContact(CONTACT_ID);
        verify(contactsDao).deleteContact(CONTACT_ID);
    }

    @Test
    public void deleteContact_should_returnEmptyOptional_when_errorOccurs() {
        when(contactsDao.getContact(CONTACT_ID)).thenThrow(RuntimeException.class);

        Optional<Contact> contactOptional = component.deleteContact(CONTACT_ID);

        assertThat(contactOptional.isEmpty()).isTrue();
    }

}
