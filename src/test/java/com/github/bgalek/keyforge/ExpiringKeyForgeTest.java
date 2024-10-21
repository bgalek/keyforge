package com.github.bgalek.keyforge;

import com.github.bgalek.keyforge.api.ApiKey;
import com.github.bgalek.keyforge.api.ApiKeyType;
import com.github.bgalek.keyforge.api.ExpiringApiKey;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ExpiringKeyForgeTest {

    private final Clock testClock = Clock.fixed(Instant.parse("2024-10-20T15:03:05.930Z"), Clock.systemDefaultZone().getZone());

    @Test
    @DisplayName("Parsing test")
    void shouldParseProvidedKey() {
        ExpiringKeyForge<ExpiringApiKey> keyForge = new ExpiringKeyForge<>(testClock, Duration.ofDays(1));
        keyForge.generateKey(ApiKeyType.SECRET_KEY, "opanapi", Duration.ofDays(1));
        ExpiringApiKey apiKey = keyForge.parse("sk_MDE5MmFhNzQwZmNhNzMxMGI3Nzc2MWY2NjBkMWZlMjItMTcyOTUyMjk4NS1vcGFuYXBp");

        assertEquals("opanapi", keyForge.getIdentifier(apiKey));
        assertEquals(Instant.parse("2024-10-20T15:03:05.930Z"), keyForge.getIssuedAt(apiKey));
    }

    @Test
    @DisplayName("Simplest test")
    void simplestCase() {
        ExpiringKeyForge<ExpiringApiKey> keyForge = new ExpiringKeyForge<>(testClock, Duration.ofMinutes(30));
        ExpiringApiKey apiKey = keyForge.generateKey(ApiKeyType.SECRET_KEY, "bgalek");

        assertEquals("bgalek", keyForge.getIdentifier(apiKey));
        assertEquals(ApiKeyType.SECRET_KEY, keyForge.getType(apiKey));
        assertEquals(testClock.instant(), keyForge.getIssuedAt(apiKey));
        assertTrue(keyForge.serialize(apiKey).startsWith("sk_MDE5MmFhNzQwZmNhN"));
    }

    @ParameterizedTest
    @DisplayName("Serialization test")
    @MethodSource("serializationTestArguments")
    void shouldSerializeKeyToTargetFormat(ApiKeyType apiKeyType, String identifier) {
        ExpiringKeyForge<ExpiringApiKey> keyForge = new ExpiringKeyForge<>(testClock, Duration.ofMinutes(30));
        ExpiringApiKey apiKey = keyForge.generateKey(apiKeyType, identifier, Duration.ofMinutes(15));

        assertEquals(identifier, keyForge.getIdentifier(apiKey));
        assertEquals(testClock.instant(), keyForge.getIssuedAt(apiKey));
        assertEquals(testClock.instant().plus(15, ChronoUnit.MINUTES), keyForge.getExpirationDate(apiKey));
        assertFalse(keyForge.isExpired(apiKey));
    }

    private static Stream<Arguments> serializationTestArguments() {
        return Stream.of(
                Arguments.of(ApiKeyType.SECRET_KEY, "opanapi"),
                Arguments.of(ApiKeyType.PUBLIC_KEY, "github")
        );
    }
}
