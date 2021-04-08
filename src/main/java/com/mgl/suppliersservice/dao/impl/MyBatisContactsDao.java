package com.mgl.suppliersservice.dao.impl;

import com.mgl.suppliersservice.dao.ContactsDao;
import com.mgl.suppliersservice.dao.entities.ContactEntity;
import com.mgl.suppliersservice.dao.mappers.ContactsMapper;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * .
 */
@Component
public class MyBatisContactsDao implements ContactsDao {

    private final ContactsMapper contactsMapper;

    /**
     * .
     *
     * @param contactsMapper .
     *
     */
    public MyBatisContactsDao(ContactsMapper contactsMapper) {
        this.contactsMapper = contactsMapper;
    }

    /**
     * .
     *
     * @param supplierId .
     *
     * @return .
     */
    @Override
    public List<ContactEntity> getContactsForSupplier(String supplierId) {
        return contactsMapper.getContacts(supplierId);
    }
}
