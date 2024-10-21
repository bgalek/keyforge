package com.github.bgalek.keyforge.traits;

import com.github.bgalek.keyforge.api.TimestampedApiKey;

import java.time.Instant;
import java.util.UUID;

/**
 * The IdentifiableKey interface ensures that API keys can provide a unique identifier
 * to track or recognize the key.
 */
public interface TimestampedKey<T extends TimestampedApiKey> {
    /**
     * Returns the date and time the API key was issued.
     *
     * @param apiKey the API key.
     * @return the date and time the key was issued as an Instant.
     */
    default Instant getIssuedAt(T apiKey) {
        return getIssuedAt(apiKey.value());
    }

    default Instant getIssuedAt(UUID uuid) {
        long unixTimestamp = getTimeOrderedEpochTimestamp(uuid.getMostSignificantBits());
        final long seconds = unixTimestamp / 10_000_000;
        final long nanos = (unixTimestamp % 10_000_000) * 100;
        return Instant.ofEpochSecond(seconds, nanos);
    }

    private long getTimeOrderedEpochTimestamp(long msb) {
        final long ticksPerMilli = 10_000;
        return ((msb & 0xffffffffffff0000L) >>> 16) * ticksPerMilli;
    }
}