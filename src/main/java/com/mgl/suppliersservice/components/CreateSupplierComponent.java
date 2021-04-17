package com.mgl.suppliersservice.components;

import com.mgl.suppliersservice.dao.ContactsDao;
import com.mgl.suppliersservice.dao.SuppliersDao;
import com.mgl.suppliersservice.dao.entities.ContactEntity;
import com.mgl.suppliersservice.dao.entities.SupplierEntity;
import com.mgl.suppliersservice.models.Supplier;
import com.mgl.suppliersservice.models.mappers.ContactsEntityMapper;
import com.mgl.suppliersservice.models.mappers.SuppliersEntityMapper;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Component for Creating a {@link Supplier}.
 */
@Component
@Log4j2
public class CreateSupplierComponent {

    private final ContactsDao contactsDao;
    private final SuppliersDao suppliersDao;

    private final SuppliersEntityMapper suppliersEntityMapper;
    private final ContactsEntityMapper contactsEntityMapper;

    /**
     * .
     *
     * @param suppliersDao .
     * @param contactsDao .
     * @param suppliersEntityMapper .
     * @param contactsEntityMapper .
     *
     */
    @Autowired
    public CreateSupplierComponent(SuppliersDao suppliersDao,
                                   ContactsDao contactsDao,
                                   SuppliersEntityMapper suppliersEntityMapper,
                                   ContactsEntityMapper contactsEntityMapper) {
        this.suppliersDao = suppliersDao;
        this.contactsDao = contactsDao;
        this.suppliersEntityMapper = suppliersEntityMapper;
        this.contactsEntityMapper = contactsEntityMapper;
    }

    /**
     * Creates the provided {@link Supplier} in the Persistence Layer.
     *
     * @param supplier .
     *
     * @return The id of the created {@link Supplier}.
     */
    public String createSupplier(Supplier supplier) {
        log.info("Creating Supplier: {}", supplier);

        try {
            SupplierEntity supplierEntity = suppliersEntityMapper.fromModel(supplier);
            String supplierId = suppliersDao.createSupplier(supplierEntity);

            log.info("Successfully created Supplier: {}", supplierId);

            List<ContactEntity> contactEntities = supplier.getContacts()
                .stream()
                .map(contact -> contactsEntityMapper.fromModel(contact, supplierId))
                .collect(Collectors.toList());
            contactsDao.insertContacts(contactEntities);

            return supplierId;
        } catch (Exception e) {
            log.error("Error while trying to create A Supplier", e);
            throw new RuntimeException(e);
        }
    }

}
