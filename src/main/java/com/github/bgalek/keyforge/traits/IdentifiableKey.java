package com.github.bgalek.keyforge.traits;

import com.github.bgalek.keyforge.api.ApiKey;

import java.time.Instant;
import java.util.UUID;

/**
 * The IdentifiableKey interface ensures that API keys can provide a unique identifier
 * to track or recognize the key.
 */
public interface IdentifiableKey<T extends ApiKey> {
    /**
     * Returns the identifier associated with the API key.
     *
     * @param apiKey the API key.
     * @return the unique identifier.
     */
    default String getIdentifier(ApiKey apiKey) {
        return apiKey.identifier();
    }
}