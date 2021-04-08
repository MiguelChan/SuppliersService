package com.mgl.suppliersservice.dao.impl;

import com.mgl.suppliersservice.dao.SuppliersDao;
import com.mgl.suppliersservice.dao.entities.SupplierEntity;
import com.mgl.suppliersservice.dao.mappers.SuppliersMapper;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * .
 */
@Log4j2
@Component
public class MyBatisSuppliersDao implements SuppliersDao {

    private final SuppliersMapper suppliersMapper;

    /**
     * .
     */
    @Autowired
    public MyBatisSuppliersDao(SuppliersMapper suppliersMapper) {
        this.suppliersMapper = suppliersMapper;
    }

    @Override
    public List<SupplierEntity> getSuppliers(int pageSize, int pageNumber) {
        return suppliersMapper.getSuppliers(pageSize, pageNumber);
    }
}
