package com.github.bgalek.keyforge;

/**
 * Interface for building keys
 *
 * @param <T> key type
 */
public interface KeyBuilder<T> {
    /**
     * Build the key
     *
     * @return generated key
     */
    T build();
}
