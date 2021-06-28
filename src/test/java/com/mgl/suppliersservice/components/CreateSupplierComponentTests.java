package com.mgl.suppliersservice.components;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
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
public class CreateSupplierComponentTests {

    @Mock
    private ContactsDao contactsDao;
    @Mock
    private SuppliersDao suppliersDao;
    @Mock
    private SuppliersEntityMapper suppliersEntityMapper;
    @Mock
    private ContactsEntityMapper contactsEntityMapper;

    private CreateSupplierComponent createSupplierComponent;

    /**
     * .
     */
    @BeforeEach
    public void setup() {
        createSupplierComponent = new CreateSupplierComponent(
            suppliersDao,
            contactsDao,
            suppliersEntityMapper,
            contactsEntityMapper
        );
    }

    @Test
    public void createSupplier_should_createTheSupplier() throws Exception {
        List<Contact> contacts = EnhancedRandom.randomListOf(2, Contact.class);
        Supplier expectedSupplier = EnhancedRandom.random(Supplier.class)
            .toBuilder()
            .contacts(contacts)
            .build();

        String expectedSupplierId = "SupplierId";

        SupplierEntity mockSupplier = EnhancedRandom.random(SupplierEntity.class);
        when(suppliersEntityMapper.fromModel(expectedSupplier)).thenReturn(mockSupplier);
        when(suppliersDao.createSupplier(mockSupplier)).thenReturn(expectedSupplierId);

        ContactEntity mockContact = EnhancedRandom.random(ContactEntity.class);
        when(contactsEntityMapper.fromModel(any(), eq(expectedSupplierId))).thenReturn(mockContact);

        String supplierId = createSupplierComponent.createSupplier(expectedSupplier);

        assertThat(supplierId).isEqualTo(expectedSupplierId);
        verify(contactsEntityMapper, times(contacts.size())).fromModel(any(), eq(expectedSupplierId));
        verify(contactsDao).insertContacts(anyList());
        verify(suppliersDao).createSupplier(mockSupplier);
    }

    @Test
    public void createSupplier_should_callContactsDao_when_contactsAreEmpty() throws Exception {
        List<Contact> contacts = Lists.newArrayList();
        Supplier expectedSupplier = EnhancedRandom.random(Supplier.class)
            .toBuilder()
            .contacts(contacts)
            .build();

        String expectedSupplierId = "SupplierId";

        SupplierEntity mockSupplier = EnhancedRandom.random(SupplierEntity.class);
        when(suppliersEntityMapper.fromModel(expectedSupplier)).thenReturn(mockSupplier);
        when(suppliersDao.createSupplier(mockSupplier)).thenReturn(expectedSupplierId);

        String supplierId = createSupplierComponent.createSupplier(expectedSupplier);

        assertThat(supplierId).isEqualTo(expectedSupplierId);
        verifyNoInteractions(contactsEntityMapper);
        verify(contactsDao).insertContacts(Lists.newArrayList());
    }

    @Test
    public void createSupplier_should_bubbleUpException_when_somethingHappens() {
        Supplier randomSupplier = EnhancedRandom.random(Supplier.class);
        when(suppliersEntityMapper.fromModel(any())).thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> {
            createSupplierComponent.createSupplier(randomSupplier);
        }).isInstanceOfAny(RuntimeException.class);
    }
}
