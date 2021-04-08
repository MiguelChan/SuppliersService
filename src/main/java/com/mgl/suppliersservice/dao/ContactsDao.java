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

}
