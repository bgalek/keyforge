package com.github.bgalek.keyforge;

import com.github.bgalek.keyforge.api.ApiKey;
import com.github.bgalek.keyforge.api.ApiKeyType;
import com.github.bgalek.keyforge.api.KeyForge;
import com.github.bgalek.keyforge.api.ExpiringApiKey;
import com.github.bgalek.keyforge.traits.ExpiringKey;
import com.github.bgalek.keyforge.traits.IdentifiableKey;
import com.github.bgalek.keyforge.traits.TimestampedKey;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

public class ExpiringKeyForge<T extends ApiKey> implements
        IdentifiableKey<ExpiringApiKey>,
        TimestampedKey<ExpiringApiKey>,
        KeyForge<ExpiringApiKey>,
        ExpiringKey<ExpiringApiKey> {

    private final Clock clock;
    private final UUIDv7 UUIDGenerator;
    private final Duration defaultDuration;

    public ExpiringKeyForge(Clock clock, Duration defaultDuration) {
        this.clock = clock;
        this.UUIDGenerator = new UUIDv7(clock);
        this.defaultDuration = defaultDuration;
    }

    public ExpiringApiKey generateKey(ApiKeyType apiKeyType, String identifier) {
        return generateKey(apiKeyType, identifier, defaultDuration);
    }

    @Override
    public ExpiringApiKey generateKey(ApiKeyType apiKeyType, String identifier, Duration duration) {
        UUID value = UUIDGenerator.randomUUID();
        return new ExpiringApiKey(
                apiKeyType.prefix(),
                identifier,
                value,
                getIssuedAt(value).plusSeconds(duration.getSeconds())
        );
    }

    @Override
    public Instant getExpirationDate(ExpiringApiKey apiKey) {
        return apiKey.getExpirationDate();
    }

    @Override
    public ExpiringApiKey parse(String input) {
        String[] prefix = input.split("_", 2);
        String[] rest = new String(Base64.getUrlDecoder().decode(prefix[1]), StandardCharsets.UTF_8).split("-", 3);
        BigInteger bigInteger = new BigInteger(rest[0], 16);
        UUID value = new UUID(bigInteger.shiftRight(64).longValue(), bigInteger.longValue());
        Instant expirationDate = Instant.ofEpochSecond(Long.parseLong(rest[1]));
        String identifier = rest[2];
        return new ExpiringApiKey(prefix[0], identifier, value, expirationDate);
    }

    @Override
    public boolean isExpired(ExpiringApiKey apiKey) {
        return apiKey.getExpirationDate().isBefore(clock.instant());
    }

    @Override
    public String serialize(ExpiringApiKey apiKey) {
        return String.format(
                "%s_%s",
                apiKey.prefix(),
                Base64.getUrlEncoder().withoutPadding().encodeToString((apiKey.value().toString().replaceAll("-", "") + "-" + apiKey.getExpirationDate().getEpochSecond() + "-" + apiKey.identifier()).getBytes(StandardCharsets.UTF_8))
        );
    }
}
