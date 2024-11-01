package com.github.bgalek.keyforge.test;

import com.github.bgalek.keyforge.ApiKeyType;
import com.github.bgalek.keyforge.MetadataApiKey;
import com.github.bgalek.keyforge.MetadataKeyForge;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MetadataKeyForgeTest {

    @Test
    @DisplayName("Parsing test")
    void shouldParseProvidedKey() {
        MetadataKeyForge keyForge = new MetadataKeyForge();
        MetadataApiKey apiKey = keyForge.parse("sk_QmFydD1TaW1wc29u");

        assertEquals(apiKey.getMetadata(), Map.of("Bart", "Simpson"));
    }

    @Test
    @DisplayName("Simplest test")
    void simplestCase() {
        MetadataKeyForge keyForge = new MetadataKeyForge();
        MetadataApiKey apiKey = keyForge.newKey()
                .withMetadata(Map.of("Bart", "Simpson"))
                .withType(ApiKeyType.SECRET_KEY)
                .build();
        assertEquals(ApiKeyType.SECRET_KEY, apiKey.getType());
        assertEquals("sk_QmFydD1TaW1wc29u", apiKey.toString());
    }

    @ParameterizedTest
    @DisplayName("Serialization test")
    @MethodSource("serializationTestArguments")
    void shouldSerializeKeyToTargetFormat(ApiKeyType apiKeyType, String expected) {
        MetadataKeyForge keyForge = new MetadataKeyForge();
        MetadataApiKey apiKey = keyForge.newKey()
                .withMetadata(Map.of("Bart", "Simpson"))
                .withType(apiKeyType)
                .build();
        assertEquals(expected, apiKey.toString());
    }

    private static Stream<Arguments> serializationTestArguments() {
        return Stream.of(
                Arguments.of(ApiKeyType.SECRET_KEY, "sk_QmFydD1TaW1wc29u"),
                Arguments.of(ApiKeyType.PUBLIC_KEY, "pk_QmFydD1TaW1wc29u")
        );
    }
}
