package com.mgl.suppliersservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Defines the REST Controller for Status and Health monitoring.
 */
@RestController
@RequestMapping("/")
public class HealthController {

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

}
