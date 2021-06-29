package com.mgl.suppliersservice.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.mgl.suppliersservice.components.DeleteContactComponent;
import com.mgl.suppliersservice.dao.ContactsDao;
import com.mgl.suppliersservice.dao.entities.ContactEntity;
import com.mgl.suppliersservice.dto.DeleteContactResponse;
import com.mgl.suppliersservice.models.Contact;
import com.mgl.suppliersservice.models.mappers.ContactsEntityMapper;
import io.github.benas.randombeans.api.EnhancedRandom;
import java.util.List;
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
public class ContactsControllerTests {

    private static final String TEST_SUPPLIER_ID = "SomeRandomSupplierId";

    @Mock
    private ContactsDao contactsDao;
    @Mock
    private ContactsEntityMapper contactsEntityMapper;
    @Mock
    private DeleteContactComponent deleteContactComponent;

    private ContactsController contactsController;

    @BeforeEach
    public void setup() {
        contactsController = new ContactsController(contactsDao, contactsEntityMapper, deleteContactComponent);
    }

    @Test
    public void getContacts_should_returnTheContacts_when_theyAreAvailable() {
        List<ContactEntity> dbContacts = EnhancedRandom.randomListOf(5, ContactEntity.class);

        when(contactsDao.getContactsForSupplier(TEST_SUPPLIER_ID)).thenReturn(dbContacts);
        when(contactsEntityMapper.fromEntity(any())).thenReturn(any());

        List<Contact> contacts = contactsController.getContacts(TEST_SUPPLIER_ID);

        assertThat(contacts.size()).isEqualTo(dbContacts.size());
        verify(contactsDao).getContactsForSupplier(TEST_SUPPLIER_ID);

        dbContacts.forEach(currentContact -> {
            verify(contactsEntityMapper).fromEntity(currentContact);
        });
    }

    @Test
    public void getContacts_should_bubbleUpExceptionFromDao() {
        when(contactsDao.getContactsForSupplier(TEST_SUPPLIER_ID)).thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> contactsController.getContacts(TEST_SUPPLIER_ID)).isInstanceOfAny(RuntimeException.class);

        verify(contactsDao).getContactsForSupplier((TEST_SUPPLIER_ID));
        verifyNoInteractions(contactsEntityMapper);
    }

    @Test
    public void deleteContact_should_deleteContact() {
        String contactId = "SomeContactId";
        Contact deletedContact = EnhancedRandom.random(Contact.class);
        when(deleteContactComponent.deleteContact(contactId)).thenReturn(Optional.of(deletedContact));

        DeleteContactResponse response = contactsController.deleteContact(contactId);

        assertThat(response.isDeleted()).isTrue();
        assertThat(response.getContact()).isEqualTo(deletedContact);
    }

    @Test
    public void deleteContact_should_returnEmptyResponse_when_noDeletionOccurs() {
        String contactId = "SomeContactId";
        when(deleteContactComponent.deleteContact(contactId)).thenReturn(Optional.empty());

        DeleteContactResponse response = contactsController.deleteContact(contactId);

        assertThat(response.isDeleted()).isFalse();
        assertThat(response.getContact()).isNull();
    }

}
