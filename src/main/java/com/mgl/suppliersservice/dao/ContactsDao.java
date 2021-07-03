package com.mgl.suppliersservice.dao;

import com.mgl.suppliersservice.dao.entities.ContactEntity;
import java.util.List;

/**
 * .
 */
public interface ContactsDao {

    /**
     * .
     *
     * @param supplierId .
     *
     * @return .
     */
    List<ContactEntity> getContactsForSupplier(String supplierId);

    /**
     * Inserts a List of {@link ContactEntity}.
     *
     * @param contactEntities .
     *
     */
    void insertContacts(List<ContactEntity> contactEntities);

    /**
     * Gets a contact.
     *
     * @param contactId .
     * @return .
     */
    ContactEntity getContact(String contactId);

    /**
     * Deletes a Contact.
     *
     * @param contactId .
     *
     */
    void deleteContact(String contactId);

    /**
     * Edits a Contact.
     *
     * @param contactEntity .
     */
    void editContact(ContactEntity contactEntity);

}
