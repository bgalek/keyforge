package com.github.bgalek.keyforge;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

public class ExpiringKeyForge implements IKeyForge<ExpiringApiKey> {
    private final UUIDv7 UUIDGenerator;
    private final Duration defaultDuration;

    public ExpiringKeyForge() {
        this(Clock.systemUTC(), Duration.ZERO);
    }

    public ExpiringKeyForge(Duration defaultDuration) {
        this(Clock.systemUTC(), defaultDuration);
    }

    public ExpiringKeyForge(Clock clock, Duration defaultDuration) {
        this.UUIDGenerator = new UUIDv7(clock);
        this.defaultDuration = defaultDuration;
    }

    @Override
    public ExpiringApiKeyBuilder newKey() {
        UUID value = UUIDGenerator.randomUUID();
        return new ExpiringApiKeyBuilder()
                .withValue(value)
                .withType(ApiKeyType.SECRET_KEY)
                .withValidFor(defaultDuration);
    }

    @Override
    public ExpiringApiKey parse(String input) {
        String[] prefix = input.split("_", 2);
        String[] rest = new String(Base64.getUrlDecoder().decode(prefix[1]), StandardCharsets.UTF_8).split("-", 3);
        BigInteger bigInteger = new BigInteger(rest[0], 16);
        UUID value = new UUID(bigInteger.shiftRight(64).longValue(), bigInteger.longValue());
        Instant expirationDate = Instant.ofEpochSecond(Long.parseLong(rest[1]));
        String identifier = rest[2];
        return new ExpiringApiKeyBuilder()
                .withType(ApiKeyType.fromPrefix(prefix[0]))
                .withValue(value)
                .withExpiration(expirationDate)
                .withIdentifier(identifier)
                .build();
    }
}
