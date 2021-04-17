package com.mgl.suppliersservice.components;

import com.mgl.suppliersservice.dao.ContactsDao;
import com.mgl.suppliersservice.dao.SuppliersDao;
import com.mgl.suppliersservice.dao.entities.ContactEntity;
import com.mgl.suppliersservice.dao.entities.SupplierEntity;
import com.mgl.suppliersservice.models.Contact;
import com.mgl.suppliersservice.models.Supplier;
import com.mgl.suppliersservice.models.Tuple;
import com.mgl.suppliersservice.models.mappers.ContactsEntityMapper;
import com.mgl.suppliersservice.models.mappers.SuppliersEntityMapper;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Component that retrieves a List of Suppliers.
 */
@Log4j2
@Component
public class GetSuppliersComponent {

    private final ContactsDao contactsDao;
    private final SuppliersDao suppliersDao;
    private final SuppliersEntityMapper suppliersEntityMapper;
    private final ContactsEntityMapper contactsEntityMapper;

    /**
     * .
     *
     * @param contactsDao .
     * @param suppliersDao .
     * @param suppliersEntityMapper .
     * @param contactsEntityMapper .
     *
     */
    @Autowired
    public GetSuppliersComponent(ContactsDao contactsDao,
                                 SuppliersDao suppliersDao,
                                 SuppliersEntityMapper suppliersEntityMapper,
                                 ContactsEntityMapper contactsEntityMapper) {
        this.contactsDao = contactsDao;
        this.suppliersDao = suppliersDao;
        this.suppliersEntityMapper = suppliersEntityMapper;
        this.contactsEntityMapper = contactsEntityMapper;
    }

    /**
     * Gets a List of Suppliers based off the pageSize and pageNumber.
     *
     * @param pageSize .
     * @param pageNumber .
     *
     * @return .
     */
    public Tuple<Integer, List<Supplier>> getSuppliers(int pageSize, int pageNumber) {
        try {
            List<SupplierEntity> supplierEntities = suppliersDao.getSuppliers(pageSize, pageNumber);
            int suppliersCount = suppliersDao.getSuppliersCount();

            log.info("Found {} Suppliers", suppliersCount);

            Map<SupplierEntity, List<ContactEntity>> supplierEntitiesToContacts = supplierEntities.stream()
                .collect(Collectors.toMap(
                    Function.identity(),
                    currentSupplier -> {
                        return contactsDao.getContactsForSupplier(currentSupplier.getId());
                    }));

            List<Supplier> supplierList = supplierEntitiesToContacts.entrySet().stream().map(currentEntry -> {
                List<Contact> contacts = currentEntry.getValue()
                    .stream()
                    .map(contactsEntityMapper::fromEntity)
                    .collect(Collectors.toList());

                Supplier supplier = suppliersEntityMapper.fromEntity(currentEntry.getKey());

                return supplier.toBuilder()
                    .contacts(contacts)
                    .build();
            }).collect(Collectors.toList());

            return Tuple.<Integer, List<Supplier>>builder()
                .leftValue(suppliersCount)
                .rightValue(supplierList)
                .build();
        } catch (Exception e) {
            log.error(e);
            throw new RuntimeException(e);
        }
    }

}
