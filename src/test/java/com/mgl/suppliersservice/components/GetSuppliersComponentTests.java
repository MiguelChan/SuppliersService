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
import com.mgl.suppliersservice.models.Tuple;
import com.mgl.suppliersservice.models.mappers.ContactsEntityMapper;
import com.mgl.suppliersservice.models.mappers.SuppliersEntityMapper;
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
    private ContactsEntityMapper contactsEntityMapper;
    @Mock
    private SuppliersEntityMapper suppliersEntityMapper;

    private GetSuppliersComponent getSuppliersComponent;

    @BeforeEach
    public void setup() {
        getSuppliersComponent = new GetSuppliersComponent(contactsDao, suppliersDao,
            suppliersEntityMapper, contactsEntityMapper);
    }

    @Test
    public void getSuppliers_should_returnTheSuppliers() throws Exception {
        int expectedPageSize = 100;
        int expectedPageNumber = 10;
        int expectedSuppliersCount = 1000;

        SupplierEntity supplierEntity = EnhancedRandom.random(SupplierEntity.class);
        ContactEntity contactEntity = EnhancedRandom.random(ContactEntity.class);

        when(suppliersDao.getSuppliers(expectedPageSize, expectedPageNumber)).thenReturn((Lists.newArrayList(supplierEntity)));
        when(suppliersDao.getSuppliersCount()).thenReturn(expectedSuppliersCount);
        when(contactsDao.getContactsForSupplier(supplierEntity.getId())).thenReturn(Lists.newArrayList(contactEntity));

        Contact contact = EnhancedRandom.random(Contact.class);
        Supplier supplier = EnhancedRandom.random(Supplier.class);

        Supplier expectedSupplier = supplier.toBuilder()
            .contacts(Lists.newArrayList(contact))
            .build();

        when(contactsEntityMapper.fromEntity(contactEntity)).thenReturn(contact);
        when(suppliersEntityMapper.fromEntity(supplierEntity)).thenReturn(supplier);

        Tuple<Integer, List<Supplier>> getSuppliersResponse =
            getSuppliersComponent.getSuppliers(expectedPageSize, expectedPageNumber);

        verify(suppliersDao).getSuppliers(expectedPageSize, expectedPageNumber);

        List<Supplier> suppliers = getSuppliersResponse.getRightValue();
        assertThat(suppliers.size()).isEqualTo(1);
        assertThat(suppliers.get(0)).isEqualTo(expectedSupplier);

        int totalCount = getSuppliersResponse.getLeftValue();
        assertThat(totalCount).isEqualTo(expectedSuppliersCount);
    }

    @Test
    public void getSuppliers_should_bubbleUpException_when_daoFails() throws Exception {
        int expectedPageSize = 5;
        int expectedPageNumber = 1;
        when(suppliersDao.getSuppliers(expectedPageSize, expectedPageNumber)).thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> getSuppliersComponent.getSuppliers(expectedPageSize, expectedPageNumber)).isInstanceOfAny(RuntimeException.class);
    }

}
