package com.mgl.suppliersservice.dao.impl;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import com.mgl.suppliersservice.dao.entities.SupplierEntity;
import com.mgl.suppliersservice.dao.mappers.SuppliersMapper;
import io.github.benas.randombeans.api.EnhancedRandom;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * .
 */
@ExtendWith(MockitoExtension.class)
public class MyBatisSuppliersDaoTests {

    @Mock
    private SuppliersMapper suppliersMapper;

    private MyBatisSuppliersDao suppliersDao;

    @BeforeEach
    public void setup() {
        suppliersDao = new MyBatisSuppliersDao(suppliersMapper);
    }

    @Test
    public void getSuppliers_should_returnTheSuppliers() {
        int pageNumber = 50;
        int pageSize = 100;

        List<SupplierEntity> expectedSupplierEntities = EnhancedRandom.randomListOf(10, SupplierEntity.class);

        when(suppliersMapper.getSuppliers(pageSize, pageNumber)).thenReturn(expectedSupplierEntities);

        List<SupplierEntity> suppliers = suppliersDao.getSuppliers(pageSize, pageNumber);

        assertThat(suppliers).isEqualTo(expectedSupplierEntities);
    }

    @Test
    public void getSuppliers_should_bubbleUpTheException() {
        when(suppliersMapper.getSuppliers(anyInt(), anyInt())).thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> suppliersDao.getSuppliers(100, 1000)).isInstanceOfAny(RuntimeException.class);
    }

}
