package com.github.bgalek.keyforge;

import com.github.bgalek.keyforge.api.ApiKey;
import com.github.bgalek.keyforge.api.ApiKeyType;
import com.github.bgalek.keyforge.api.TimestampedApiKey;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Clock;
import java.time.Instant;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DefaultKeyForgeTest {

    private final Clock testClock = Clock.fixed(Instant.parse("2024-10-20T15:03:05.930Z"), Clock.systemDefaultZone().getZone());

    @Test
    @DisplayName("Parsing test")
    void shouldParseProvidedKey() {
        DefaultKeyForge keyForge = new DefaultKeyForge(testClock);
        TimestampedApiKey apiKey = keyForge.parse("sk_MDE5MmFhNzQwZmNhN2I2NDgyNmM3Zjg0YmM1ZTcyMjUtb3BhbmFwaQ");

        assertEquals("opanapi", keyForge.getIdentifier(apiKey));
        assertEquals(Instant.parse("2024-10-20T15:03:05.930Z"), keyForge.getIssuedAt(apiKey));
    }

    @Test
    @DisplayName("Simplest test")
    void simplestCase() {
        DefaultKeyForge keyForge = new DefaultKeyForge(testClock);
        TimestampedApiKey apiKey = keyForge.generateKey(ApiKeyType.SECRET_KEY, "bgalek");

        assertEquals("bgalek", keyForge.getIdentifier(apiKey));
        assertEquals(ApiKeyType.SECRET_KEY, keyForge.getType(apiKey));
        assertEquals(testClock.instant(), keyForge.getIssuedAt(apiKey));
        assertTrue(keyForge.serialize(apiKey).startsWith("sk_MDE5MmFhNzQwZmNhN"));
    }

    @ParameterizedTest
    @DisplayName("Serialization test")
    @MethodSource("serializationTestArguments")
    void shouldSerializeKeyToTargetFormat(ApiKeyType apiKeyType, String identifier) {
        DefaultKeyForge keyForge = new DefaultKeyForge(testClock);
        TimestampedApiKey apiKey = keyForge.generateKey(apiKeyType, identifier);

        assertEquals(identifier, keyForge.getIdentifier(apiKey));
        assertEquals(testClock.instant(), keyForge.getIssuedAt(apiKey));
    }

    private static Stream<Arguments> serializationTestArguments() {
        return Stream.of(
                Arguments.of(ApiKeyType.SECRET_KEY, "opanapi"),
                Arguments.of(ApiKeyType.PUBLIC_KEY, "github")
        );
    }
}
