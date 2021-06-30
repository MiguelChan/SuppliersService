package com.mgl.suppliersservice.components;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mgl.suppliersservice.dao.ContactsDao;
import com.mgl.suppliersservice.dao.SuppliersDao;
import com.mgl.suppliersservice.dao.entities.ContactEntity;
import com.mgl.suppliersservice.dao.entities.SupplierEntity;
import com.mgl.suppliersservice.models.Contact;
import com.mgl.suppliersservice.models.Supplier;
import com.mgl.suppliersservice.models.mappers.ContactsEntityMapper;
import com.mgl.suppliersservice.models.mappers.SuppliersEntityMapper;
import io.github.benas.randombeans.api.EnhancedRandom;
import java.util.Optional;
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
public class DeleteSupplierComponentTests {

    @Mock
    private SuppliersDao suppliersDao;
    @Mock
    private ContactsDao contactsDao;
    @Mock
    private SuppliersEntityMapper suppliersEntityMapper;
    @Mock
    private ContactsEntityMapper contactsEntityMapper;

    private DeleteSupplierComponent component;

    /**
     * .
     */
    @BeforeEach
    public void setup() {
        component = new DeleteSupplierComponent(
            suppliersDao,
            suppliersEntityMapper,
            contactsDao,
            contactsEntityMapper
        );
    }

    @Test
    public void deleteSupplier_should_returnDeletedSupplier() throws Exception {
        String supplierId = "SomeSupplierId";

        // Setting up DAO's
        SupplierEntity supplierEntity = EnhancedRandom.random(SupplierEntity.class, "contacts");
        ContactEntity contactEntity = EnhancedRandom.random(ContactEntity.class);

        when(contactsDao.getContactsForSupplier(supplierId)).thenReturn(Lists.newArrayList(contactEntity));
        when(suppliersDao.getSupplier(supplierId)).thenReturn(supplierEntity);

        // Setting up Mappers
        Supplier deletedSupplier = EnhancedRandom.random(Supplier.class, "contacts");
        Contact deletedContact = EnhancedRandom.random(Contact.class);

        when(suppliersEntityMapper.fromEntity(supplierEntity)).thenReturn(deletedSupplier);
        when(contactsEntityMapper.fromEntity(contactEntity)).thenReturn(deletedContact);

        // Let's test this bad boy
        Optional<Supplier> supplierOptional = component.deleteSupplier(supplierId);

        assertThat(supplierOptional).isNotNull();
        assertThat(supplierOptional.isPresent()).isTrue();

        Supplier expectedSupplier = deletedSupplier.toBuilder()
            .contacts(Lists.newArrayList(deletedContact))
            .build();

        Supplier supplier = supplierOptional.get();

        assertThat(supplier).isEqualTo(expectedSupplier);
    }

    @Test
    public void deleteSupplier_should_catchException_when_somethingFails() throws Exception {
        String supplierId = "SomeSupplierId";

        doThrow(RuntimeException.class).when(suppliersDao).getSupplier(supplierId);

        Optional<Supplier> supplierOptional = component.deleteSupplier(supplierId);

        assertThat(supplierOptional).isNotNull();
        assertThat(supplierOptional.isEmpty()).isTrue();
    }

}
