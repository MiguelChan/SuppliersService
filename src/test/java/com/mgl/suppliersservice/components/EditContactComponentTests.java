package com.mgl.suppliersservice.components;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.mgl.suppliersservice.dao.ContactsDao;
import com.mgl.suppliersservice.dao.entities.ContactEntity;
import com.mgl.suppliersservice.dto.EditContactResponse;
import com.mgl.suppliersservice.models.Contact;
import com.mgl.suppliersservice.models.mappers.ContactsEntityMapper;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * .
 */
@ExtendWith(MockitoExtension.class)
public class EditContactComponentTests {

    @Mock
    private ContactsDao contactsDao;
    @Mock
    private ContactsEntityMapper contactsEntityMapper;

    private EditContactComponent component;

    /**
     * .
     */
    @BeforeEach
    public void setup() {
        component = new EditContactComponent(contactsDao, contactsEntityMapper);
    }

    @Test
    public void editContact_should_editTheContact() {
        Contact contact = EnhancedRandom.random(Contact.class);
        ContactEntity contactEntity = EnhancedRandom.random(ContactEntity.class);
        ContactEntity originalEntity = EnhancedRandom.random(ContactEntity.class);

        when(contactsDao.getContact(contact.getId())).thenReturn(originalEntity);
        when(contactsEntityMapper.fromModel(contact, originalEntity.getSupplierId())).thenReturn(contactEntity);

        EditContactResponse response = component.editContact(contact);

        assertThat(response.isSuccess()).isTrue();

        verify(contactsDao).editContact(contactEntity);
        verify(contactsDao).getContact(contact.getId());
        verify(contactsEntityMapper).fromModel(contact, originalEntity.getSupplierId());
    }

    @Test
    public void editContact_should_returnUnsuccessfulStatus_when_contactIdIsEmpty() {
        Contact contact = EnhancedRandom.random(Contact.class, "id");
        contact.setId("");

        EditContactResponse response = component.editContact(contact);

        assertThat(response.isSuccess()).isFalse();
        verifyNoInteractions(contactsDao, contactsEntityMapper);
    }

    @Test
    public void editContact_should_returnUnsuccessfulStatus_when_contactIdIsNull() {
        Contact contact = EnhancedRandom.random(Contact.class, "id");

        EditContactResponse response = component.editContact(contact);

        assertThat(response.isSuccess()).isFalse();
        verifyNoInteractions(contactsDao, contactsEntityMapper);
    }

    @Test
    public void editContact_should_returnUnsuccessfulStatus_when_daosFail() {
        Contact contact = EnhancedRandom.random(Contact.class);

        when(contactsDao.getContact(contact.getId())).thenThrow(RuntimeException.class);

        EditContactResponse response = component.editContact(contact);

        assertThat(response.isSuccess()).isFalse();
        verifyNoInteractions(contactsEntityMapper);
    }

}
