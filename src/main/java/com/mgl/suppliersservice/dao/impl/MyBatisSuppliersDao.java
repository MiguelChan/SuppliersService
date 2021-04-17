package com.mgl.suppliersservice.dao.impl;

import com.mgl.suppliersservice.dao.SuppliersDao;
import com.mgl.suppliersservice.dao.entities.SupplierEntity;
import com.mgl.suppliersservice.dao.mappers.SuppliersMapper;
import com.mgl.suppliersservice.dao.utils.RandomIdGenerator;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * .
 */
@Log4j2
@Component
public class MyBatisSuppliersDao implements SuppliersDao {

    private static final String SUPPLIER_ID_PREFIX = "spl";

    private final SuppliersMapper suppliersMapper;
    private final RandomIdGenerator randomIdGenerator;

    /**
     * .
     */
    @Autowired
    public MyBatisSuppliersDao(SuppliersMapper suppliersMapper,
                               RandomIdGenerator randomIdGenerator) {
        this.suppliersMapper = suppliersMapper;
        this.randomIdGenerator = randomIdGenerator;
    }

    @Override
    public List<SupplierEntity> getSuppliers(int pageSize, int pageNumber) throws Exception {
        int offset = pageSize * pageNumber;
        return suppliersMapper.getSuppliers(pageSize, offset);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public String createSupplier(SupplierEntity supplierEntity) throws Exception {
        String supplierId = randomIdGenerator.generateRandomId(SUPPLIER_ID_PREFIX);
        SupplierEntity newEntity = supplierEntity.toBuilder()
            .id(supplierId)
            .build();

        try {
            suppliersMapper.insertSupplier(newEntity);
            return supplierId;
        } catch (Exception e) {
            log.error("Error thrown from Database", e);
            throw e;
        }
    }

    @Override
    public int getSuppliersCount() throws Exception {
        return suppliersMapper.getSuppliersCount();
    }

    @Override
    public void deleteSupplier(String supplierId) throws Exception {
        suppliersMapper.deleteSupplier(supplierId);
    }
}
