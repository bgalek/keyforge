package com.github.bgalek.keyforge;

import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

public class ExpiringApiKey extends ApiKey {
    private final Instant expirationDate;

    public ExpiringApiKey(String prefix, String identifier, UUID value, Instant expirationDate) {
        super(prefix, identifier, value);
        this.expirationDate = expirationDate;
    }

    public Instant getExpirationDate() {
        return expirationDate;
    }

    public boolean isExpired(Clock clock) {
        return expirationDate.isBefore(clock.instant());
    }

    @Override
    public String toString() {
        String payload = "%s-%d".formatted(
                this.getValue().toString().replace("-", ""),
                this.getExpirationDate().getEpochSecond()
        );
        return "%s_%s_%s".formatted(
                this.getPrefix(),
                this.getIdentifier(),
                Base64.getUrlEncoder().withoutPadding().encodeToString(payload.getBytes(StandardCharsets.UTF_8))
        );
    }
}
