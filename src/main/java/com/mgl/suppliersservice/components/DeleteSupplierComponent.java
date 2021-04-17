package com.mgl.suppliersservice.components;

import com.mgl.suppliersservice.dao.SuppliersDao;
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

    /**
     * .
     *
     * @param suppliersDao .
     *
     */
    @Autowired
    public DeleteSupplierComponent(SuppliersDao suppliersDao) {
        this.suppliersDao = suppliersDao;
    }

    /**
     * Deletes a supplier.
     *
     * @param supplierId .
     *
     * @return true if deleted correctly, false otherwise.
     */
    public boolean deleteSupplier(String supplierId) {
        log.info("Attempting to delete Supplier: {}", supplierId);
        try {
            suppliersDao.deleteSupplier(supplierId);
            return true;
        } catch (Exception e) {
            log.info("An exception occurred while trying to delete Supplier.", e);
            return false;
        }
    }

}
