package com.mgl.suppliersservice.controllers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

import com.mgl.suppliersservice.dao.HealthDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * .
 */
@ExtendWith(MockitoExtension.class)
public class HealthControllerTests {

    @Mock
    private HealthDao healthDao;

    private HealthController controller;

    @BeforeEach
    public void setup() {
        controller = new HealthController(healthDao);
    }

    @Test
    public void ping_should_alwaysReturnTrue() {
        assertThat(controller.ping()).isTrue();
    }

    @Test
    public void isHealthy_should_returnTrue_when_daoIsHealthy() {
        when(healthDao.isHealthy()).thenReturn(true);

        boolean isHealthy = controller.deepPing();

        assertThat(isHealthy).isTrue();
    }

    @Test
    public void isHealthy_should_returnFalse_when_daoIsNotHealthy() {
        when(healthDao.isHealthy()).thenReturn(false);

        boolean isHealthy = controller.deepPing();

        assertThat(isHealthy).isFalse();
    }


}
