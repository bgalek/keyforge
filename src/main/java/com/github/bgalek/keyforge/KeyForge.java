package com.github.bgalek.keyforge;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.util.Base64;
import java.util.UUID;

public class KeyForge implements IKeyForge<ApiKey> {

    private final UUIDv7 UUIDGenerator;

    public KeyForge() {
        this(Clock.systemDefaultZone());
    }

    public KeyForge(Clock clock) {
        this.UUIDGenerator = new UUIDv7(clock);
    }

    @Override
    public TimestampedApiKeyBuilder<ApiKey> newKey() {
        return new TimestampedApiKeyBuilder<>()
                .withValue(UUIDGenerator.randomUUID())
                .withIdentifier("key")
                .withType(ApiKeyType.SECRET_KEY);
    }

    @Override
    public ApiKey parse(String input) {
        String[] prefix = input.split("_", 2);
        String[] rest = new String(Base64.getUrlDecoder().decode(prefix[1]), StandardCharsets.UTF_8).split("-", 2);
        BigInteger bigInteger = new BigInteger(rest[0], 16);
        UUID value = new UUID(bigInteger.shiftRight(64).longValue(), bigInteger.longValue());
        String identifier = rest[1];
        return new TimestampedApiKeyBuilder<>()
                .withIdentifier(identifier)
                .withType(ApiKeyType.fromPrefix(prefix[0]))
                .withValue(value)
                .build();
    }
}
