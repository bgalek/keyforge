package com.github.bgalek.keyforge;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

/**
 * Represents an API key.
 */
public class ApiKey extends IdentifiableApiKey<UUID> {

    ApiKey(String prefix, String identifier, UUID uuid) {
        super(prefix, identifier, uuid);
    }

    public Instant getIssuedAt() {
        return UUIDv7.getIssuedAt(this.getValue());
    }

    @Override
    public String toString() {
        return String.format(
                "%s_%s_%s",
                this.getPrefix(),
                this.getIdentifier(),
                Base64.getUrlEncoder().withoutPadding().encodeToString(this.getValue().toString().replace("-", "").getBytes(StandardCharsets.UTF_8))
        );
    }
}
