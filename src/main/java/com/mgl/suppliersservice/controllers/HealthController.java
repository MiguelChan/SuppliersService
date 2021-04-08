package com.mgl.suppliersservice.controllers;

import com.mgl.suppliersservice.dao.HealthDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Defines the REST Controller for Status and Health monitoring.
 */
@RestController
@RequestMapping("/")
public class HealthController {

    private final HealthDao healthDao;

    @Autowired
    public HealthController(HealthDao healthDao) {
        this.healthDao = healthDao;
    }

    /**
     * A ping operation. Returns true always - since this means the service is up and running.
     *
     * @return true.
     */
    @GetMapping
    @RequestMapping("/ping")
    public boolean ping() {
        return true;
    }

    /**
     * Performs a Deep Ping.
     *
     * @return true.
     */
    @GetMapping
    @RequestMapping("/deepPing")
    public boolean deepPing() {
        return healthDao.isHealthy();
    }

}
