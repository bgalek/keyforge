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

    public String toString() {
        return String.format(
                "%s_%s_%s",
                this.getPrefix(),
                this.getIdentifier(),
                Base64.getUrlEncoder()
                        .withoutPadding()
                        .encodeToString(("%s-%d".formatted(
                                this.getValue().toString().replaceAll("-", ""),
                                this.getExpirationDate().getEpochSecond()
                        )).getBytes(StandardCharsets.UTF_8))
        );
    }
}
