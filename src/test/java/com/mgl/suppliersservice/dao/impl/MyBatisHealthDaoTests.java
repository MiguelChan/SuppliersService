package com.mgl.suppliersservice.dao.impl;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mgl.suppliersservice.dao.mappers.HealthMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * .
 */
@ExtendWith(MockitoExtension.class)
public class MyBatisHealthDaoTests {

    @Mock
    private HealthMapper healthMapper;

    private MyBatisHealthDao healthDao;

    @BeforeEach
    public void setup() {
        healthDao = new MyBatisHealthDao(healthMapper);
    }

    @Test
    public void isHealthy_should_return() {
        when(healthMapper.isHealthy()).thenReturn(true);

        boolean isHealthy = healthDao.isHealthy();

        assertThat(isHealthy).isTrue();
        verify(healthMapper).isHealthy();
    }

    @Test
    public void isHealthy_should_returnFalse_when_anExceptionOccurs() {
        when(healthMapper.isHealthy()).thenThrow(RuntimeException.class);

        boolean isHealthy = healthDao.isHealthy();

        assertThat(isHealthy).isFalse();
    }

}
