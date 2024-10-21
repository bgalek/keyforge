package com.github.bgalek.keyforge.api;

import java.time.Instant;
import java.util.UUID;

public class ExpiringApiKey extends TimestampedApiKey {
    private final Instant expirationDate;

    public ExpiringApiKey(String prefix, String identifier, UUID value, Instant expirationDate) {
        super(prefix, identifier, value);
        this.expirationDate = expirationDate;
    }

    public Instant getExpirationDate() {
        return expirationDate;
    }
}
