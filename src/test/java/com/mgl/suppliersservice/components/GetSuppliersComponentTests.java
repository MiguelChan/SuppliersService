package com.mgl.suppliersservice.components;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mgl.suppliersservice.dao.ContactsDao;
import com.mgl.suppliersservice.dao.SuppliersDao;
import com.mgl.suppliersservice.dao.entities.ContactEntity;
import com.mgl.suppliersservice.dao.entities.SupplierEntity;
import com.mgl.suppliersservice.models.Contact;
import com.mgl.suppliersservice.models.Supplier;
import com.mgl.suppliersservice.models.mappers.ContactsMapper;
import com.mgl.suppliersservice.models.mappers.SuppliersMapper;
import io.github.benas.randombeans.api.EnhancedRandom;
import java.util.List;
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
public class GetSuppliersComponentTests {

    @Mock
    private ContactsDao contactsDao;
    @Mock
    private SuppliersDao suppliersDao;
    @Mock
    private ContactsMapper contactsMapper;
    @Mock
    private SuppliersMapper suppliersMapper;

    private GetSuppliersComponent getSuppliersComponent;

    @BeforeEach
    public void setup() {
        getSuppliersComponent = new GetSuppliersComponent(contactsDao, suppliersDao, suppliersMapper, contactsMapper);
    }

    @Test
    public void getSuppliers_should_returnTheSuppliers() {
        int expectedPageSize = 100;
        int expectedPageNumber = 10;

        SupplierEntity supplierEntity = EnhancedRandom.random(SupplierEntity.class);
        ContactEntity contactEntity = EnhancedRandom.random(ContactEntity.class);

        when(suppliersDao.getSuppliers(expectedPageSize, expectedPageNumber)).thenReturn((Lists.newArrayList(supplierEntity)));
        when(contactsDao.getContactsForSupplier(supplierEntity.getId())).thenReturn(Lists.newArrayList(contactEntity));

        Contact contact = EnhancedRandom.random(Contact.class);
        Supplier supplier = EnhancedRandom.random(Supplier.class);

        Supplier expectedSupplier = supplier.toBuilder()
            .contacts(Lists.newArrayList(contact))
            .build();

        when(contactsMapper.fromEntity(contactEntity)).thenReturn(contact);
        when(suppliersMapper.fromEntity(supplierEntity)).thenReturn(supplier);

        List<Supplier> suppliers = getSuppliersComponent.getSuppliers(expectedPageSize, expectedPageNumber);

        verify(suppliersDao).getSuppliers(expectedPageSize, expectedPageNumber);
        assertThat(suppliers.size()).isEqualTo(1);
        assertThat(suppliers.get(0)).isEqualTo(expectedSupplier);
    }

    @Test
    public void getSuppliers_should_bubbleUpException_when_daoFails() {
        int expectedPageSize = 5;
        int expectedPageNumber = 1;
        when(suppliersDao.getSuppliers(expectedPageSize, expectedPageNumber)).thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> getSuppliersComponent.getSuppliers(expectedPageSize, expectedPageNumber)).isInstanceOfAny(RuntimeException.class);
    }

}
