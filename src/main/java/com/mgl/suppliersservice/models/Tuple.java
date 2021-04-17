package com.mgl.suppliersservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Emm. A Tuple
 *
 * @param <L> The type of the Left Value.
 * @param <R> The type of the Right Value.
 *
 */
@Builder
@Data
@AllArgsConstructor
public class Tuple<L, R> {

    private final L leftValue;
    private final R rightValue;

}
