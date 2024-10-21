package com.github.bgalek.keyforge;

import com.github.bgalek.keyforge.api.ApiKeyType;
import com.github.bgalek.keyforge.api.KeyForge;
import com.github.bgalek.keyforge.api.TimestampedApiKey;
import com.github.bgalek.keyforge.traits.IdentifiableKey;
import com.github.bgalek.keyforge.traits.TimestampedKey;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.util.Base64;
import java.util.UUID;

public class DefaultKeyForge implements IdentifiableKey<TimestampedApiKey>, TimestampedKey<TimestampedApiKey>, KeyForge<TimestampedApiKey> {

    private final UUIDv7 UUIDGenerator;

    public DefaultKeyForge(Clock clock) {
        this.UUIDGenerator = new UUIDv7(clock);
    }

    @Override
    public TimestampedApiKey generateKey(ApiKeyType apiKeyType, String identifier) {
        return new TimestampedApiKey(
                apiKeyType.prefix(),
                identifier,
                UUIDGenerator.randomUUID()
        );
    }

    @Override
    public TimestampedApiKey parse(String input) {
        String[] prefix = input.split("_", 2);
        String[] rest = new String(Base64.getUrlDecoder().decode(prefix[1]), StandardCharsets.UTF_8).split("-", 2);
        BigInteger bigInteger = new BigInteger(rest[0], 16);
        UUID value = new UUID(bigInteger.shiftRight(64).longValue(), bigInteger.longValue());
        String identifier = rest[1];
        return new TimestampedApiKey(prefix[0], identifier, value);
    }

    @Override
    public String serialize(TimestampedApiKey apiKey) {
        return String.format(
                "%s_%s",
                apiKey.prefix(),
                Base64.getUrlEncoder().withoutPadding().encodeToString((apiKey.value().toString().replaceAll("-", "") + "-" + apiKey.identifier()).getBytes(StandardCharsets.UTF_8))
        );
    }
}
