package com.mgl.suppliersservice.dao.utils;

import java.util.UUID;
import org.springframework.stereotype.Component;

/**
 * Generates a RandomId.
 */
@Component
public class RandomIdGenerator {

    private static final int MAX_DIGITS = 10;

    /**
     * Generates a randomId for the Supplier.
     *
     * @return The id of the Supplier.
     *
     */
    public String generateRandomId(String prefix) {
        String randomUuid = UUID.randomUUID().toString().replace("-", "");
        String formattedId = String.format("%s%s", prefix, randomUuid.toUpperCase());
        return formattedId.substring(0, MAX_DIGITS);
    }

}
