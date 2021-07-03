package com.mgl.suppliersservice.components;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.mgl.suppliersservice.dao.ContactsDao;
import com.mgl.suppliersservice.dao.entities.ContactEntity;
import com.mgl.suppliersservice.models.Contact;
import com.mgl.suppliersservice.models.mappers.ContactsEntityMapper;
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
public class GetContactsForSupplierComponentTests {

    private static final String SUPPLIER_ID = "SomeSupplierId";

    @Mock
    private ContactsDao contactsDao;
    @Mock
    private ContactsEntityMapper contactsEntityMapper;

    private GetContactsForSupplierComponent component;

    @BeforeEach
    public void setup() {
        component = new GetContactsForSupplierComponent(contactsDao, contactsEntityMapper);
    }

    @Test
    public void getContactsForSupplier_should_returnContacts() {
        ContactEntity entity1 = EnhancedRandom.random(ContactEntity.class);
        ContactEntity entity2 = EnhancedRandom.random(ContactEntity.class);

        Contact expectedContact1 = EnhancedRandom.random(Contact.class);
        Contact expectedContact2 = EnhancedRandom.random(Contact.class);

        when(contactsEntityMapper.fromEntity(entity1)).thenReturn(expectedContact1);
        when(contactsEntityMapper.fromEntity(entity2)).thenReturn(expectedContact2);

        when(contactsDao.getContactsForSupplier(SUPPLIER_ID)).thenReturn(Lists.newArrayList(entity1, entity2));

        List<Contact> foundContacts = component.getContactsForSupplier(SUPPLIER_ID);

        assertThat(foundContacts).isEqualTo(Lists.newArrayList(expectedContact1, expectedContact2));
    }

    @Test
    public void getContactsForSupplier_should_returnEmptyList_when_errorOccurs() {
        when(contactsDao.getContactsForSupplier(SUPPLIER_ID)).thenThrow(RuntimeException.class);

        List<Contact> contacts = component.getContactsForSupplier(SUPPLIER_ID);

        assertThat(contacts).isNotNull();
        assertThat(contacts.isEmpty()).isTrue();

        verifyNoInteractions(contactsEntityMapper);
    }

}
