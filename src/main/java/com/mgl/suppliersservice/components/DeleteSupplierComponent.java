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
 * The component for deleting suppliers.
 */
@Log4j2
@Component
public class DeleteSupplierComponent {

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
     */
    @Autowired
    public DeleteSupplierComponent(SuppliersDao suppliersDao,
                                   SuppliersEntityMapper suppliersEntityMapper,
                                   ContactsDao contactsDao,
                                   ContactsEntityMapper contactsEntityMapper) {
        this.suppliersDao = suppliersDao;
        this.suppliersEntityMapper = suppliersEntityMapper;
        this.contactsDao = contactsDao;
        this.contactsEntityMapper = contactsEntityMapper;
    }

    /**
     * Deletes a supplier.
     *
     * @param supplierId .
     *
     * @return true if deleted correctly, false otherwise.
     */
    public Optional<Supplier> deleteSupplier(String supplierId) {
        log.info("Attempting to delete Supplier: {}", supplierId);
        try {
            SupplierEntity supplierToDelete = suppliersDao.getSupplier(supplierId);
            List<ContactEntity> contactsToDelete = contactsDao.getContactsForSupplier(supplierId);

            // This should cascade the Contacts as well.
            suppliersDao.deleteSupplier(supplierId);

            Supplier deletedSupplier = suppliersEntityMapper.fromEntity(supplierToDelete);
            List<Contact> deletedContacts = contactsToDelete.stream()
                .map(contactsEntityMapper::fromEntity)
                .collect(Collectors.toList());

            deletedSupplier.setContacts(deletedContacts);

            return Optional.of(deletedSupplier);
        } catch (Exception e) {
            log.error("An error occurred when trying to deleted Supplier.", e);
            return Optional.empty();
        }
    }

}
