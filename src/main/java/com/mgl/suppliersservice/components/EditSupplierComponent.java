package com.mgl.suppliersservice.components;

import com.mgl.suppliersservice.dao.ContactsDao;
import com.mgl.suppliersservice.dao.SuppliersDao;
import com.mgl.suppliersservice.dao.entities.ContactEntity;
import com.mgl.suppliersservice.dao.entities.SupplierEntity;
import com.mgl.suppliersservice.dto.EditSupplierResponse;
import com.mgl.suppliersservice.models.Contact;
import com.mgl.suppliersservice.models.Supplier;
import com.mgl.suppliersservice.models.mappers.ContactsEntityMapper;
import com.mgl.suppliersservice.models.mappers.SuppliersEntityMapper;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * .
 */
@Log4j2
@Component
public class EditSupplierComponent {

    private final ContactsDao contactsDao;
    private final ContactsEntityMapper contactsEntityMapper;
    private final SuppliersDao suppliersDao;
    private final SuppliersEntityMapper suppliersEntityMapper;

    /**
     * .
     *
     * @param suppliersDao .
     * @param contactsDao .
     * @param suppliersEntityMapper .
     * @param contactsEntityMapper .
     */
    @Autowired
    public EditSupplierComponent(SuppliersDao suppliersDao,
                                 ContactsDao contactsDao,
                                 SuppliersEntityMapper suppliersEntityMapper,
                                 ContactsEntityMapper contactsEntityMapper) {
        this.suppliersDao = suppliersDao;
        this.contactsDao = contactsDao;
        this.suppliersEntityMapper = suppliersEntityMapper;
        this.contactsEntityMapper = contactsEntityMapper;
    }

    /**
     * .
     *
     * @param supplier .
     *
     * @return .
     */
    public EditSupplierResponse editSupplier(Supplier supplier) {
        log.info("Attempting to Edit Supplier with Id: {}", supplier.getId());

        List<Contact> newContacts = supplier.getContacts().stream()
            .filter(currentContact -> {
                String contactId = currentContact.getId();
                return Objects.isNull(contactId) || contactId.isEmpty();
            })
            .collect(Collectors.toList());
        log.debug("Found {} new contacts to Add", newContacts.size());

        SupplierEntity supplierEntity = suppliersEntityMapper.fromModel(supplier);
        List<ContactEntity> newContactEntities = newContacts.stream()
            .map(contact -> contactsEntityMapper.fromModel(contact, supplier.getId()))
            .collect(Collectors.toList());

        try {
            SupplierEntity existingSupplier = suppliersDao.getSupplier(supplier.getId());
            Objects.requireNonNull(existingSupplier, "Requested supplier does not exist");

            contactsDao.insertContacts(newContactEntities);
            suppliersDao.editSupplier(supplierEntity);
            return EditSupplierResponse.builder()
                .success(true)
                .build();
        } catch (Exception e) {
            log.error("An error occurred while trying to edit Supplier", e);
            return EditSupplierResponse.builder()
                .message(e.getMessage())
                .success(false)
                .build();
        }
    }

}
