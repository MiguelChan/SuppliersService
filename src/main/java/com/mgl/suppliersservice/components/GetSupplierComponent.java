package com.mgl.suppliersservice.components;

import com.mgl.suppliersservice.dao.ContactsDao;
import com.mgl.suppliersservice.dao.SuppliersDao;
import com.mgl.suppliersservice.dao.entities.ContactEntity;
import com.mgl.suppliersservice.dao.entities.SupplierEntity;
import com.mgl.suppliersservice.models.Contact;
import com.mgl.suppliersservice.models.Supplier;
import com.mgl.suppliersservice.models.mappers.ContactsEntityMapper;
import com.mgl.suppliersservice.models.mappers.SuppliersEntityMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The component for retrieving suppliers.
 */
@Log4j2
@Component
public class GetSupplierComponent {

    private final SuppliersDao suppliersDao;
    private final SuppliersEntityMapper suppliersEntityMapper;
    private final ContactsDao contactsDao;
    private final ContactsEntityMapper contactsEntityMapper;

    /**
     * .
     *
     * @param suppliersDao .
     * @param suppliersEntityMapper .
     * @param contactsDao .
     * @param contactsEntityMapper .
     *
     */
    @Autowired
    public GetSupplierComponent(SuppliersDao suppliersDao,
                                SuppliersEntityMapper suppliersEntityMapper,
                                ContactsDao contactsDao,
                                ContactsEntityMapper contactsEntityMapper) {
        this.suppliersDao = suppliersDao;
        this.suppliersEntityMapper = suppliersEntityMapper;
        this.contactsDao = contactsDao;
        this.contactsEntityMapper = contactsEntityMapper;
    }

    /**
     * Gets a Supplier given its Id.
     *
     * @param supplierId .
     *
     * @return .
     */
    public Optional<Supplier> getSupplier(String supplierId) {
        log.info("Getting Supplier with Id: {}", supplierId);
        try {
            SupplierEntity supplierEntity = suppliersDao.getSupplier(supplierId);
            Supplier supplier = suppliersEntityMapper.fromEntity(supplierEntity);

            List<ContactEntity> contactEntities = contactsDao.getContactsForSupplier(supplierId);
            List<Contact> contactList = contactEntities.stream()
                .map(contactsEntityMapper::fromEntity)
                .collect(Collectors.toList());

            supplier.setContacts(contactList);

            return Optional.of(supplier);
        } catch (Exception e) {
            log.error("Error while trying to fetch supplier: {}", supplierId, e);
            return Optional.empty();
        }
    }

}
