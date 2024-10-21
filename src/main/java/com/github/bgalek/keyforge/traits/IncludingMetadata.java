package com.github.bgalek.keyforge.traits;

import com.github.bgalek.keyforge.api.ApiKey;
import com.github.bgalek.keyforge.api.ApiKeyType;
import com.github.bgalek.keyforge.api.ExpiringApiKey;

import java.time.Duration;
import java.util.Map;

/**
 * The ExpiringKey interface defines an expiration mechanism for API keys.
 */
public interface IncludingMetadata<T extends ApiKey> {
    T generateKey(ApiKeyType apiKeyType, String identifier, Map<String, String> metadata);
    Map<String, String> getMetadata();
}