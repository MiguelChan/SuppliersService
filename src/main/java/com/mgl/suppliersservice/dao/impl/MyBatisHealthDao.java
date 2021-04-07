package com.mgl.suppliersservice.dao.impl;

import com.mgl.suppliersservice.dao.HealthDao;
import com.mgl.suppliersservice.dao.mappers.HealthMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * .
 */
@Log4j2
@Component
public class MyBatisHealthDao implements HealthDao {

    private final HealthMapper healthMapper;

    /**
     * .
     *
     * @param healthMapper .
     *
     */
    @Autowired
    public MyBatisHealthDao(HealthMapper healthMapper) {
        this.healthMapper = healthMapper;
    }

    /**
     * .
     *
     * @return .
     */
    @Override
    public boolean isHealthy() {
        try {
            return healthMapper.isHealthy();
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }
}
