package com.github.bgalek.keyforge.test;

import com.github.bgalek.keyforge.ApiKeyType;
import com.github.bgalek.keyforge.ExpiringApiKey;
import com.github.bgalek.keyforge.ExpiringKeyForge;
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

    private final Clock testClock = Clock.fixed(
            Instant.parse("2024-10-20T15:03:05.930Z"),
            Clock.systemDefaultZone().getZone()
    );

    @Test
    @DisplayName("Parsing test")
    void shouldParseProvidedKey() {
        ExpiringKeyForge keyForge = new ExpiringKeyForge(testClock, Duration.ofDays(1));
        ExpiringApiKey apiKey = keyForge.parse("sk_MDE5MmFhNzQwZmNhNzMxMGI3Nzc2MWY2NjBkMWZlMjItMTcyOTUyMjk4NS1vcGFuYXBp");

        assertEquals("opanapi", apiKey.getIdentifier());
        assertEquals(Instant.parse("2024-10-20T15:03:05.930Z"), apiKey.getIssuedAt());
    }

    @Test
    @DisplayName("Simplest test")
    void simplestCase() {
        ExpiringKeyForge keyForge = new ExpiringKeyForge(testClock, Duration.ofMinutes(30));
        ExpiringApiKey apiKey = keyForge.newKey()
                .withType(ApiKeyType.SECRET_KEY)
                .withIdentifier("bgalek")
                .build();

        assertEquals("bgalek", apiKey.getIdentifier());
        assertEquals(ApiKeyType.SECRET_KEY, apiKey.getType());
        assertEquals(testClock.instant(), apiKey.getIssuedAt());
        assertTrue(apiKey.toString().startsWith("sk_bgalek_MDE5MmFhNzQwZmNhN"));
    }

    @ParameterizedTest
    @DisplayName("Serialization test")
    @MethodSource("serializationTestArguments")
    void shouldSerializeKeyToTargetFormat(ApiKeyType apiKeyType, String identifier) {
        ExpiringKeyForge keyForge = new ExpiringKeyForge(testClock, Duration.ofMinutes(30));
        ExpiringApiKey apiKey = keyForge.newKey()
                .withType(apiKeyType)
                .withIdentifier(identifier)
                .withValidFor(Duration.ofMinutes(15))
                .build();

        assertEquals(identifier, apiKey.getIdentifier());
        assertEquals(testClock.instant(), apiKey.getIssuedAt());
        assertEquals(testClock.instant().plus(15, ChronoUnit.MINUTES), apiKey.getExpirationDate());
        assertFalse(apiKey.isExpired(testClock));
    }

    private static Stream<Arguments> serializationTestArguments() {
        return Stream.of(
                Arguments.of(ApiKeyType.SECRET_KEY, "opanapi"),
                Arguments.of(ApiKeyType.PUBLIC_KEY, "github")
        );
    }
}
