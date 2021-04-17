package com.mgl.suppliersservice.dao.impl;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.mgl.suppliersservice.dao.entities.ContactEntity;
import com.mgl.suppliersservice.dao.mappers.ContactsMapper;
import com.mgl.suppliersservice.dao.utils.RandomIdGenerator;
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
public class MyBatisContactsDaoTests {

    private static final String TEST_SUPPLIER_ID = "SomeRandomSupplier";

    @Mock
    private ContactsMapper contactsMapper;
    @Mock
    private RandomIdGenerator randomIdGenerator;

    private MyBatisContactsDao contactsDao;

    @BeforeEach
    public void setup() {
        contactsDao = new MyBatisContactsDao(contactsMapper, randomIdGenerator);
    }

    @Test
    public void getContactsForSupplier_should_returnTheSuppliers_when_theyAreFound() {
        List<ContactEntity> expectedContacts = EnhancedRandom.randomListOf(100, ContactEntity.class);
        when(contactsMapper.getContacts(TEST_SUPPLIER_ID)).thenReturn(expectedContacts);

        List<ContactEntity> foundContacts = contactsDao.getContactsForSupplier(TEST_SUPPLIER_ID);

        assertThat(foundContacts).isEqualTo(foundContacts);
        verify(contactsMapper).getContacts(TEST_SUPPLIER_ID);
    }

    @Test
    public void getContactsForSupplier_should_bubbleUpException_when_mapperFails() {
        when(contactsMapper.getContacts(TEST_SUPPLIER_ID)).thenThrow(RuntimeException.class);
        assertThatThrownBy(() -> contactsDao.getContactsForSupplier(TEST_SUPPLIER_ID)).isInstanceOfAny(Exception.class);
    }

    @Test
    public void insertContacts_should_doNothing_when_providedListIsEmpty() {
        contactsDao.insertContacts(Lists.newArrayList());

        verifyNoInteractions(contactsMapper);
        verifyNoInteractions(randomIdGenerator);
    }

    @Test
    public void insertContacts_should_insertContacts() {
        String contactId = "SomeRandomId";
        ContactEntity contactEntity = EnhancedRandom.random(ContactEntity.class);

        when(randomIdGenerator.generateRandomId(anyString())).thenReturn(contactId);

        contactsDao.insertContacts(Lists.newArrayList(contactEntity));

        verify(randomIdGenerator).generateRandomId(anyString());
        verify(contactsMapper).insertContacts(anyList());
    }

}
