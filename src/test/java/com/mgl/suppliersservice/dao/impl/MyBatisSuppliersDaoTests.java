package com.mgl.suppliersservice.dao.impl;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mgl.suppliersservice.dao.entities.SupplierEntity;
import com.mgl.suppliersservice.dao.mappers.SuppliersMapper;
import com.mgl.suppliersservice.dao.utils.RandomIdGenerator;
import io.github.benas.randombeans.api.EnhancedRandom;
import java.util.List;
import net.bytebuddy.build.ToStringPlugin;
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
    @Mock
    private RandomIdGenerator randomIdGenerator;

    private MyBatisSuppliersDao suppliersDao;

    @BeforeEach
    public void setup() {
        suppliersDao = new MyBatisSuppliersDao(suppliersMapper, randomIdGenerator);
    }

    @Test
    public void getSuppliers_should_returnTheSuppliers() throws Exception {
        int pageNumber = 50;
        int pageSize = 100;

        List<SupplierEntity> expectedSupplierEntities = EnhancedRandom.randomListOf(10, SupplierEntity.class);

        when(suppliersMapper.getSuppliers(pageSize, pageNumber * pageSize)).thenReturn(expectedSupplierEntities);

        List<SupplierEntity> suppliers = suppliersDao.getSuppliers(pageSize, pageNumber);

        assertThat(suppliers).isEqualTo(expectedSupplierEntities);
    }

    @Test
    public void getSuppliers_should_bubbleUpTheException() {
        when(suppliersMapper.getSuppliers(anyInt(), anyInt())).thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> suppliersDao.getSuppliers(100, 1000)).isInstanceOfAny(RuntimeException.class);
    }

    @Test
    public void insertSupplier_should_insertTheSupplier() throws Exception {
        String expectedSupplierId = "SupplierId";
        SupplierEntity supplierEntity = EnhancedRandom.random(SupplierEntity.class);

        when(randomIdGenerator.generateRandomId(anyString())).thenReturn(expectedSupplierId);

        String supplierId = suppliersDao.createSupplier(supplierEntity);

        assertThat(supplierId).isEqualTo(expectedSupplierId);
        verify(randomIdGenerator).generateRandomId(any());
        verify(suppliersMapper).insertSupplier(any(SupplierEntity.class));
    }

    @Test
    public void insertSupplier_should_bubbleUpException_when_aFailureOccurs() throws Exception {
        SupplierEntity supplierEntity = EnhancedRandom.random(SupplierEntity.class);

        doThrow(RuntimeException.class).when(suppliersMapper).insertSupplier(any());

        assertThatThrownBy(() -> suppliersDao.createSupplier(supplierEntity)).isInstanceOfAny(RuntimeException.class);
    }

    @Test
    public void getSuppliersCount_should_returnTheCount() throws Exception {
        int expectedCount = 100;

        when(suppliersMapper.getSuppliersCount()).thenReturn(expectedCount);

        int count = suppliersDao.getSuppliersCount();

        assertThat(count).isEqualTo(expectedCount);
    }

    @Test
    public void deleteSupplier_should_deleteTheSupplier() throws Exception {
        String supplierId = "SupplierId";

        suppliersDao.deleteSupplier(supplierId);

        verify(suppliersMapper).deleteSupplier(supplierId);
    }

    @Test
    public void getSupplier_should_returnTheSupplier() throws Exception {
        String supplierId = "SupplierId";

        SupplierEntity expectedSupplier = EnhancedRandom.random(SupplierEntity.class);

        when(suppliersMapper.getSupplier(supplierId)).thenReturn(expectedSupplier);

        SupplierEntity foundSupplier = suppliersDao.getSupplier(supplierId);

        assertThat(foundSupplier).isEqualTo(expectedSupplier);
    }

    @Test
    public void editSupplier_should_editSupplier() throws Exception {
        SupplierEntity supplierEntity = EnhancedRandom.random(SupplierEntity.class);

        suppliersDao.editSupplier(supplierEntity);

        verify(suppliersMapper).updateSupplier(supplierEntity);
    }

    @Test
    public void editSupplier_should_bubbleUpException_when_errorOccurs() throws Exception {
        SupplierEntity supplierEntity = EnhancedRandom.random(SupplierEntity.class);
        doThrow(RuntimeException.class).when(suppliersMapper).updateSupplier(supplierEntity);

        assertThatThrownBy(() -> suppliersDao.editSupplier(supplierEntity)).isInstanceOfAny(RuntimeException.class);
    }
}
