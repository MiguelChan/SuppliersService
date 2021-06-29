package com.mgl.suppliersservice.dao.mappers;

import com.mgl.suppliersservice.dao.entities.ContactEntity;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * .
 */
@Mapper
public interface ContactsMapper {

    /**
     * Gets the list of contacts that belong to the Supplier.
     *
     * @param supplierId The supplierId.
     *
     * @return A List of Contacts.
     */
    List<ContactEntity> getContacts(String supplierId);

    /**
     * Inserts a List of {@link ContactEntity}.
     *
     * @param contactEntities .
     *
     */
    void insertContacts(List<ContactEntity> contactEntities);

    /**
     * Deletes a Contact.
     *
     * @param contactId .
     *
     * @return .
     */
    ContactEntity getContact(String contactId);

    /**
     * Deletes a Contact.
     *
     * @param contactId .
     */
    void deleteContact(String contactId);

}
