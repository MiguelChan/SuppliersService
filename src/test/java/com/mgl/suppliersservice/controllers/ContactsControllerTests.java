package com.mgl.suppliersservice.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.mgl.suppliersservice.components.DeleteContactComponent;
import com.mgl.suppliersservice.components.EditContactComponent;
import com.mgl.suppliersservice.components.GetContactComponent;
import com.mgl.suppliersservice.components.GetContactsForSupplierComponent;
import com.mgl.suppliersservice.dto.DeleteContactResponse;
import com.mgl.suppliersservice.dto.EditContactRequest;
import com.mgl.suppliersservice.dto.EditContactResponse;
import com.mgl.suppliersservice.dto.GetContactResponse;
import com.mgl.suppliersservice.dto.GetContactsForSupplierResponse;
import com.mgl.suppliersservice.models.Contact;
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

    @Mock
    private GetContactsForSupplierComponent getContactsForSupplierComponent;
    @Mock
    private DeleteContactComponent deleteContactComponent;
    @Mock
    private EditContactComponent editContactComponent;
    @Mock
    private GetContactComponent getContactComponent;

    private ContactsController contactsController;

    /**
     * .
     */
    @BeforeEach
    public void setup() {
        contactsController = new ContactsController(
            getContactsForSupplierComponent,
            deleteContactComponent,
            editContactComponent,
            getContactComponent
        );
    }

    @Test
    public void getContacts_should_returnTheContacts_when_theyAreAvailable() {
        String supplierId = "SomeSupplierId";
        List<Contact> expectedContacts = EnhancedRandom.randomListOf(5, Contact.class);

        when(getContactsForSupplierComponent.getContactsForSupplier(supplierId)).thenReturn(expectedContacts);

        GetContactsForSupplierResponse response = contactsController.getContacts(supplierId);

        assertThat(response).isNotNull();
        assertThat(response.getContacts()).isEqualTo(expectedContacts);
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

    @Test
    public void editContact_should_editContact() {
        String contactId = "SomeId";
        Contact contact = EnhancedRandom.random(Contact.class);

        EditContactResponse expectedResponse = EnhancedRandom.random(EditContactResponse.class);


        when(editContactComponent.editContact(contact)).thenReturn(expectedResponse);

        EditContactRequest request = EditContactRequest.builder()
            .contact(contact)
            .build();

        EditContactResponse response = contactsController.editContact(contactId, request);

        assertThat(response).isEqualTo(expectedResponse);
    }

    @Test
    public void getContact_should_returnTheContact() {
        String contactId = "SomeRandomId";
        Contact expectedContact = EnhancedRandom.random(Contact.class);

        when(getContactComponent.getContact(contactId)).thenReturn(Optional.of(expectedContact));

        GetContactResponse response = contactsController.getContact(contactId);

        assertThat(response).isNotNull();
        assertThat(response.getContact()).isEqualTo(expectedContact);
    }

    @Test
    public void getContact_should_returnEmptyResponse_when_nothingCanBeFound() {
        String contactId = "SomeRandomId";

        when(getContactComponent.getContact(contactId)).thenReturn(Optional.empty());

        GetContactResponse response = contactsController.getContact(contactId);

        assertThat(response).isNotNull();
        assertThat(response.getContact()).isNull();
    }
}
