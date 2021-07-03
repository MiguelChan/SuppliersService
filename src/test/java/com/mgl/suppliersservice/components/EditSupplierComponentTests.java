package com.mgl.suppliersservice.components;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.mgl.suppliersservice.dao.ContactsDao;
import com.mgl.suppliersservice.dao.SuppliersDao;
import com.mgl.suppliersservice.dao.entities.ContactEntity;
import com.mgl.suppliersservice.dao.entities.SupplierEntity;
import com.mgl.suppliersservice.dto.EditSupplierResponse;
import com.mgl.suppliersservice.models.Contact;
import com.mgl.suppliersservice.models.Supplier;
import com.mgl.suppliersservice.models.mappers.ContactsEntityMapper;
import com.mgl.suppliersservice.models.mappers.SuppliersEntityMapper;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * .
 */
@ExtendWith(MockitoExtension.class)
public class EditSupplierComponentTests {

    @Mock
    private ContactsDao contactsDao;
    @Mock
    private SuppliersDao suppliersDao;
    @Mock
    private ContactsEntityMapper contactsEntityMapper;
    @Mock
    private SuppliersEntityMapper suppliersEntityMapper;

    private EditSupplierComponent component;

    /**
     * .
     */
    @BeforeEach
    public void setup() {
        component = new EditSupplierComponent(
            suppliersDao,
            contactsDao,
            suppliersEntityMapper,
            contactsEntityMapper
        );
    }

    @Test
    public void editSupplier_should_onlyEditSupplier_when_thereAreNoNewContacts() throws Exception {
        // This creates a Supplier with all its fields and subfields populated.
        Supplier supplier = EnhancedRandom.random(Supplier.class);
        SupplierEntity supplierEntity = EnhancedRandom.random(SupplierEntity.class);

        when(suppliersDao.getSupplier(supplier.getId())).thenReturn(supplierEntity);

        when(suppliersEntityMapper.fromModel(supplier)).thenReturn(supplierEntity);

        EditSupplierResponse response = component.editSupplier(supplier);

        assertThat(response).isNotNull();
        assertThat(response.isSuccess()).isTrue();

        verifyNoInteractions(contactsEntityMapper);
        verify(suppliersEntityMapper).fromModel(supplier);
        verify(contactsDao).insertContacts(Lists.newArrayList());
        verify(suppliersDao).editSupplier(supplierEntity);
    }

    @Test
    public void editSupplier_should_onlyEditSupplierAndContacts_when_newContactsAreAdded() throws Exception {
        // This creates a Supplier with all its fields and subfields populated.
        Supplier supplier = EnhancedRandom.random(Supplier.class);
        SupplierEntity supplierEntity = EnhancedRandom.random(SupplierEntity.class);

        Contact newContact = EnhancedRandom.random(Contact.class, "id");
        ContactEntity newContactEntity = EnhancedRandom.random(ContactEntity.class, "id");

        supplier.getContacts().add(newContact);

        when(suppliersDao.getSupplier(supplier.getId())).thenReturn(supplierEntity);

        when(suppliersEntityMapper.fromModel(supplier)).thenReturn(supplierEntity);
        when(contactsEntityMapper.fromModel(newContact, supplier.getId())).thenReturn(newContactEntity);

        EditSupplierResponse response = component.editSupplier(supplier);

        assertThat(response).isNotNull();
        assertThat(response.isSuccess()).isTrue();

        verify(suppliersEntityMapper).fromModel(supplier);
        verify(contactsEntityMapper).fromModel(newContact, supplier.getId());
        verify(contactsDao).insertContacts(Lists.newArrayList(newContactEntity));
        verify(suppliersDao).editSupplier(supplierEntity);
    }

    @Test
    public void editSupplier_should_onlyEditSupplierAndContacts_when_newContactsAreAddedWithEmptyId() throws Exception {
        // This creates a Supplier with all its fields and subfields populated.
        Supplier supplier = EnhancedRandom.random(Supplier.class);
        final SupplierEntity supplierEntity = EnhancedRandom.random(SupplierEntity.class);

        Contact newContact = EnhancedRandom.random(Contact.class, "id");
        newContact.setId("");

        ContactEntity newContactEntity = EnhancedRandom.random(ContactEntity.class, "id");
        newContactEntity.setId("");

        supplier.getContacts().add(newContact);

        when(suppliersDao.getSupplier(supplier.getId())).thenReturn(supplierEntity);

        when(suppliersEntityMapper.fromModel(supplier)).thenReturn(supplierEntity);
        when(contactsEntityMapper.fromModel(newContact, supplier.getId())).thenReturn(newContactEntity);

        EditSupplierResponse response = component.editSupplier(supplier);

        assertThat(response).isNotNull();
        assertThat(response.isSuccess()).isTrue();

        verify(suppliersEntityMapper).fromModel(supplier);
        verify(contactsEntityMapper).fromModel(newContact, supplier.getId());
        verify(contactsDao).insertContacts(Lists.newArrayList(newContactEntity));
        verify(suppliersDao).editSupplier(supplierEntity);
    }

    @Test
    public void editSupplier_should_returnUnsuccessfulResponse_when_anErrorOccurs() throws Exception {
        Supplier supplier = EnhancedRandom.random(Supplier.class);
        SupplierEntity supplierEntity = EnhancedRandom.random(SupplierEntity.class);

        Contact newContact = EnhancedRandom.random(Contact.class, "id");
        ContactEntity newContactEntity = EnhancedRandom.random(ContactEntity.class, "id");

        supplier.getContacts().add(newContact);

        when(suppliersDao.getSupplier(supplier.getId())).thenReturn(supplierEntity);

        when(suppliersEntityMapper.fromModel(supplier)).thenReturn(supplierEntity);
        when(contactsEntityMapper.fromModel(newContact, supplier.getId())).thenReturn(newContactEntity);

        doThrow(RuntimeException.class).when(contactsDao).insertContacts(anyList());

        EditSupplierResponse response = component.editSupplier(supplier);

        assertThat(response).isNotNull();
        assertThat(response.isSuccess()).isFalse();
    }

    @Test
    public void editSupplier_should_returnUnsuccesfulResponse_when_supplierDoesNotExist() throws Exception {
        Supplier supplier = EnhancedRandom.random(Supplier.class);
        SupplierEntity supplierEntity = EnhancedRandom.random(SupplierEntity.class);

        Contact newContact = EnhancedRandom.random(Contact.class, "id");
        ContactEntity newContactEntity = EnhancedRandom.random(ContactEntity.class, "id");

        supplier.getContacts().add(newContact);

        when(suppliersDao.getSupplier(supplier.getId())).thenReturn(null);

        when(suppliersEntityMapper.fromModel(supplier)).thenReturn(supplierEntity);
        when(contactsEntityMapper.fromModel(newContact, supplier.getId())).thenReturn(newContactEntity);

        EditSupplierResponse response = component.editSupplier(supplier);

        assertThat(response).isNotNull();
        assertThat(response.isSuccess()).isFalse();
    }

}
