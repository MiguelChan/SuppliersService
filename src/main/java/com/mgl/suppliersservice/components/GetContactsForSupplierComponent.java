package com.mgl.suppliersservice.components;

import com.mgl.suppliersservice.dao.ContactsDao;
import com.mgl.suppliersservice.dao.entities.ContactEntity;
import com.mgl.suppliersservice.models.Contact;
import com.mgl.suppliersservice.models.mappers.ContactsEntityMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Gets a List of Contacts based off the Supplier Id.
 */
@Log4j2
@Component
public class GetContactsForSupplierComponent {

    private final ContactsDao contactsDao;
    private final ContactsEntityMapper contactsEntityMapper;

    @Autowired
    public GetContactsForSupplierComponent(ContactsDao contactsDao,
                                           ContactsEntityMapper contactsEntityMapper) {
        this.contactsDao = contactsDao;
        this.contactsEntityMapper = contactsEntityMapper;
    }

    /**
     * .
     *
     * @param supplierId .
     * @return .
     */
    public List<Contact> getContactsForSupplier(String supplierId) {
        log.info("Attempting to retrieve contacts for SupplierId: {}", supplierId);
        try {
            List<ContactEntity> contactEntities = contactsDao.getContactsForSupplier(supplierId);
            return contactEntities.stream()
                .map(contactsEntityMapper::fromEntity)
                .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error while trying to fetch contacts.", e);
            return new ArrayList<>();
        }
    }

}
