package com.mgl.suppliersservice.components;

import com.mgl.suppliersservice.dao.SuppliersDao;
import com.mgl.suppliersservice.dao.entities.SupplierEntity;
import com.mgl.suppliersservice.models.Supplier;
import com.mgl.suppliersservice.models.mappers.SuppliersEntityMapper;
import java.util.Optional;
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

    @Autowired
    public GetSupplierComponent(SuppliersDao suppliersDao,
                                SuppliersEntityMapper suppliersEntityMapper) {
        this.suppliersDao = suppliersDao;
        this.suppliersEntityMapper = suppliersEntityMapper;
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
            return Optional.of(supplier);
        } catch (Exception e) {
            log.error("Error while trying to fetch supplier: {}", supplierId, e);
            return Optional.empty();
        }
    }

}
