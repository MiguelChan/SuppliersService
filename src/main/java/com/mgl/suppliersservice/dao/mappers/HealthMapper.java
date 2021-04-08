package com.mgl.suppliersservice.dao.mappers;

import org.apache.ibatis.annotations.Mapper;

/**
 * .
 */
@Mapper
public interface HealthMapper {

    /**
     * Whether the DB is healthy or not.
     *
     * @return true.
     */
    boolean isHealthy();

}
