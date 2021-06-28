package com.mgl.suppliersservice.components;

import static org.assertj.core.api.Assertions.assertThat;
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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * .
 */
@ExtendWith(MockitoExtension.class)
public class GetSupplierComponentTests {

    private static final String SUPPLIER_ID = "SomeSupplierId";

    @Mock
    private SuppliersDao suppliersDao;
    @Mock
    private SuppliersEntityMapper suppliersEntityMapper;
    @Mock
    private ContactsDao contactsDao;
    @Mock
    private ContactsEntityMapper contactsEntityMapper;

    private GetSupplierComponent getSupplierComponent;

    /**
     * .
     */
    @BeforeEach
    public void setup() {
        getSupplierComponent = new GetSupplierComponent(
            suppliersDao,
            suppliersEntityMapper,
            contactsDao,
            contactsEntityMapper
        );
    }

    @AfterEach
    public void tearDown() {
        getSupplierComponent = null;
    }

    @Test
    public void getSupplier_should_returnTheSupplier() throws Exception {
        SupplierEntity supplierEntity = EnhancedRandom.random(SupplierEntity.class);
        ContactEntity contactEntity = EnhancedRandom.random(ContactEntity.class);

        Supplier supplier = EnhancedRandom.random(Supplier.class);
        Contact contact = EnhancedRandom.random(Contact.class);

        when(suppliersDao.getSupplier(SUPPLIER_ID)).thenReturn(supplierEntity);
        when(contactsDao.getContactsForSupplier(SUPPLIER_ID)).thenReturn(Lists.newArrayList(contactEntity));

        when(suppliersEntityMapper.fromEntity(supplierEntity)).thenReturn(supplier);
        when(contactsEntityMapper.fromEntity(contactEntity)).thenReturn(contact);

        Optional<Supplier> foundSupplierOptional = getSupplierComponent.getSupplier(SUPPLIER_ID);

        assertThat(foundSupplierOptional).isNotNull();
        assertThat(foundSupplierOptional.isPresent()).isTrue();

        Supplier expectedSupplier = supplier.toBuilder()
            .contacts(Lists.newArrayList(contact))
            .build();

        Supplier foundSupplier = foundSupplierOptional.get();
        assertThat(foundSupplier).isEqualTo(expectedSupplier);

        verify(suppliersDao).getSupplier(SUPPLIER_ID);
        verify(suppliersEntityMapper).fromEntity(supplierEntity);

        verify(contactsDao).getContactsForSupplier(SUPPLIER_ID);
        verify(contactsEntityMapper).fromEntity(contactEntity);
    }

    @Test
    public void getSupplier_should_returnEmpty_when_anErrorOccurs() throws Exception {
        when(suppliersDao.getSupplier(SUPPLIER_ID)).thenThrow(Exception.class);

        Optional<Supplier> response = getSupplierComponent.getSupplier(SUPPLIER_ID);

        assertThat(response.isEmpty()).isTrue();
    }

}
