package com.mgl.suppliersservice.dao.impl;

import com.mgl.suppliersservice.dao.ContactsDao;
import com.mgl.suppliersservice.dao.entities.ContactEntity;
import com.mgl.suppliersservice.dao.mappers.ContactsMapper;
import com.mgl.suppliersservice.dao.utils.RandomIdGenerator;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * .
 */
@Component
public class MyBatisContactsDao implements ContactsDao {

    private static final String CONTACT_ID_PREFIX = "ctc";

    private final ContactsMapper contactsMapper;
    private final RandomIdGenerator randomIdGenerator;

    /**
     * .
     *
     * @param contactsMapper .
     *
     */
    public MyBatisContactsDao(ContactsMapper contactsMapper,
                              RandomIdGenerator randomIdGenerator) {
        this.contactsMapper = contactsMapper;
        this.randomIdGenerator = randomIdGenerator;
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

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void insertContacts(List<ContactEntity> contactEntities) {
        if (contactEntities.isEmpty()) {
            return;
        }

        contactEntities.forEach(currentContact -> {
            String contactId = randomIdGenerator.generateRandomId(CONTACT_ID_PREFIX);
            currentContact.setId(contactId);
        });

        contactsMapper.insertContacts(contactEntities);
    }
}
