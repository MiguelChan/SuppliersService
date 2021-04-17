package com.mgl.suppliersservice.dao.utils;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * .
 */
public class RandomIdGeneratorTests {

    private RandomIdGenerator randomIdGenerator;

    @BeforeEach
    public void setup() {
        randomIdGenerator = new RandomIdGenerator();
    }

    @Test
    public void generateRandomId_should_generateRandomId() {
        String prefix = "spl";
        String randomId = randomIdGenerator.generateRandomId(prefix);
        assertThat(randomId).matches("^spl(.*){7}");
    }

}
