package com.github.bgalek.keyforge.test;

import com.github.bgalek.keyforge.ApiKey;
import com.github.bgalek.keyforge.ApiKeyType;
import com.github.bgalek.keyforge.KeyForge;
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

class KeyForgeForgeTest {

    private final Clock testClock = Clock.fixed(
            Instant.parse("2024-10-20T15:03:05.930Z"),
            Clock.systemDefaultZone().getZone()
    );

    @Test
    @DisplayName("Parsing test")
    void shouldParseProvidedKey() {
        KeyForge keyForge = new KeyForge(testClock);
        ApiKey apiKey = keyForge.parse("sk_MDE5MmFhNzQwZmNhN2I2NDgyNmM3Zjg0YmM1ZTcyMjUtb3BhbmFwaQ");

        assertEquals("opanapi", apiKey.getIdentifier());
        assertEquals(Instant.parse("2024-10-20T15:03:05.930Z"), apiKey.getIssuedAt());
    }

    @Test
    @DisplayName("Simplest test")
    void simplestCase() {
        KeyForge keyForge = new KeyForge(testClock);
        ApiKey apiKey = keyForge.newKey()
                .withIdentifier("bgalek")
                .withType(ApiKeyType.SECRET_KEY)
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
        KeyForge keyForge = new KeyForge(testClock);
        ApiKey apiKey = keyForge.newKey().withType(apiKeyType).withIdentifier(identifier).build();

        assertEquals(identifier, apiKey.getIdentifier());
        assertEquals(testClock.instant(), apiKey.getIssuedAt());
    }

    private static Stream<Arguments> serializationTestArguments() {
        return Stream.of(
                Arguments.of(ApiKeyType.SECRET_KEY, "opanapi"),
                Arguments.of(ApiKeyType.PUBLIC_KEY, "github")
        );
    }
}
