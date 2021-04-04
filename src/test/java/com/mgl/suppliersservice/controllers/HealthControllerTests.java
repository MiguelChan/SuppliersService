package com.mgl.suppliersservice.controllers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * .
 */
public class HealthControllerTests {

    private HealthController controller;

    @BeforeEach
    public void setup() {
        controller = new HealthController();
    }

    @Test
    public void ping_should_returnTrue() {
        assertThat(controller.ping()).isTrue();
    }

}
