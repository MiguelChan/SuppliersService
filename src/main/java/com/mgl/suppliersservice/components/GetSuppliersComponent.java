package com.mgl.suppliersservice.components;

import com.mgl.suppliersservice.dao.ContactsDao;
import com.mgl.suppliersservice.dao.SuppliersDao;
import com.mgl.suppliersservice.dao.entities.ContactEntity;
import com.mgl.suppliersservice.dao.entities.SupplierEntity;
import com.mgl.suppliersservice.models.Contact;
import com.mgl.suppliersservice.models.Supplier;
import com.mgl.suppliersservice.models.mappers.ContactsMapper;
import com.mgl.suppliersservice.models.mappers.SuppliersMapper;
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
    private final SuppliersMapper suppliersMapper;
    private final ContactsMapper contactsMapper;

    /**
     * .
     *
     * @param contactsDao .
     * @param suppliersDao .
     * @param suppliersMapper .
     * @param contactsMapper .
     *
     */
    @Autowired
    public GetSuppliersComponent(ContactsDao contactsDao,
                                 SuppliersDao suppliersDao,
                                 SuppliersMapper suppliersMapper,
                                 ContactsMapper contactsMapper) {
        this.contactsDao = contactsDao;
        this.suppliersDao = suppliersDao;
        this.suppliersMapper = suppliersMapper;
        this.contactsMapper = contactsMapper;
    }

    /**
     * Gets a List of Suppliers based off the pageSize and pageNumber.
     *
     * @param pageSize .
     * @param pageNumber .
     *
     * @return .
     */
    public List<Supplier> getSuppliers(int pageSize, int pageNumber) {
        try {
            List<SupplierEntity> supplierEntities = suppliersDao.getSuppliers(pageSize, pageNumber);
            Map<SupplierEntity, List<ContactEntity>> supplierEntitiesToContacts = supplierEntities.stream()
                .collect(Collectors.toMap(
                    Function.identity(),
                    currentSupplier -> {
                        return contactsDao.getContactsForSupplier(currentSupplier.getId());
                    }));

            return supplierEntitiesToContacts.entrySet().stream().map(currentEntry -> {
                List<Contact> contacts = currentEntry.getValue()
                    .stream()
                    .map(contactsMapper::fromEntity)
                    .collect(Collectors.toList());

                Supplier supplier = suppliersMapper.fromEntity(currentEntry.getKey());

                return supplier.toBuilder()
                    .contacts(contacts)
                    .build();
            }).collect(Collectors.toList());
        } catch (Exception e) {
            log.error(e);
            throw e;
        }
    }

}
