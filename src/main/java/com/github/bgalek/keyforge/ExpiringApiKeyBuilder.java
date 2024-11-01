package com.github.bgalek.keyforge;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import static com.github.bgalek.keyforge.UUIDv7.getIssuedAt;

public class ExpiringApiKeyBuilder extends TimestampedApiKeyBuilder<ExpiringApiKey> {
    private Instant expirationDate;

    @Override
    public ExpiringApiKeyBuilder withValue(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public ExpiringApiKeyBuilder withType(ApiKeyType type) {
        this.prefix = type.prefix();
        return this;
    }

    public ExpiringApiKeyBuilder withIdentifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    ExpiringApiKeyBuilder withExpiration(Instant expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    public ExpiringApiKeyBuilder withValidFor(Duration duration) {
        this.expirationDate = getIssuedAt(uuid).plus(duration);
        return this;
    }

    @Override
    public ExpiringApiKey build() {
        return new ExpiringApiKey(prefix, identifier, uuid, expirationDate);
    }
}