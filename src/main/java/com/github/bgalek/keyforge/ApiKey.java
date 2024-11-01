package com.github.bgalek.keyforge;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

public class ApiKey extends IdentifiableApiKey<UUID> {

    ApiKey(String prefix, String identifier, UUID uuid) {
        super(prefix, identifier, uuid);
    }

    public Instant getIssuedAt() {
        long unixTimestamp = getTimeOrderedEpochTimestamp(this.getValue().getMostSignificantBits());
        final long seconds = unixTimestamp / 10_000_000;
        final long nanos = (unixTimestamp % 10_000_000) * 100;
        return Instant.ofEpochSecond(seconds, nanos);
    }

    private long getTimeOrderedEpochTimestamp(long msb) {
        final long ticksPerMilli = 10_000;
        return ((msb & 0xffffffffffff0000L) >>> 16) * ticksPerMilli;
    }

    @Override
    public String toString() {
        return String.format(
                "%s_%s_%s",
                this.getPrefix(),
                this.getIdentifier(),
                Base64.getUrlEncoder().withoutPadding().encodeToString((this.getValue().toString().replaceAll("-", "")).getBytes(StandardCharsets.UTF_8))
        );
    }
}
