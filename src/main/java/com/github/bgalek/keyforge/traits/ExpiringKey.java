package com.github.bgalek.keyforge.traits;

import com.github.bgalek.keyforge.api.ApiKey;
import com.github.bgalek.keyforge.api.ApiKeyType;

import java.time.Duration;
import java.time.Instant;

/**
 * The ExpiringKey interface defines an expiration mechanism for API keys.
 */
public interface ExpiringKey<T extends ApiKey> {
    T generateKey(ApiKeyType apiKeyType, String identifier, Duration duration);

    /**
     * Returns the expiration date of the API key.
     *
     * @return the expiration date as an Instant, or null if the key does not expire.
     */
    Instant getExpirationDate(T apiKey);

    /**
     * Checks whether the API key has expired.
     *
     * @return true if the key has expired, false otherwise.
     */
    boolean isExpired(T apiKey);
}