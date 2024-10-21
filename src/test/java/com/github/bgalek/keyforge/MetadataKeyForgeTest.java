package com.github.bgalek.keyforge;

import com.github.bgalek.keyforge.api.ApiKeyType;
import com.github.bgalek.keyforge.api.MetadataApiKey;
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
        MetadataKeyForge<MetadataApiKey> keyForge = new MetadataKeyForge<>();
        keyForge.generateKey(ApiKeyType.SECRET_KEY, "opanapi", Map.of("Bart", "Simpson"));
        MetadataApiKey apiKey = keyForge.parse("sk_QmFydD1TaW1wc29uLW9wYW5hcGk");

        assertEquals(apiKey.getMetadata(), Map.of("Bart", "Simpson"));
    }

    @Test
    @DisplayName("Simplest test")
    void simplestCase() {
        MetadataKeyForge<MetadataApiKey> keyForge = new MetadataKeyForge<>();
        MetadataApiKey apiKey = keyForge.generateKey(ApiKeyType.SECRET_KEY, "bgalek", Map.of("Bart", "Simpson"));

        assertEquals(ApiKeyType.SECRET_KEY, keyForge.getType(apiKey));
        assertEquals("sk_QmFydD1TaW1wc29uLWJnYWxlaw", keyForge.serialize(apiKey));
    }

    @ParameterizedTest
    @DisplayName("Serialization test")
    @MethodSource("serializationTestArguments")
    void shouldSerializeKeyToTargetFormat(ApiKeyType apiKeyType, String identifier, String expected) {
        MetadataKeyForge<MetadataApiKey> keyForge = new MetadataKeyForge<>();
        MetadataApiKey apiKey = keyForge.generateKey(apiKeyType, identifier, Map.of("Bart", "Simpson"));
        assertEquals(keyForge.serialize(apiKey), expected);
    }

    private static Stream<Arguments> serializationTestArguments() {
        return Stream.of(
                Arguments.of(ApiKeyType.SECRET_KEY, "opanapi", "sk_QmFydD1TaW1wc29uLW9wYW5hcGk"),
                Arguments.of(ApiKeyType.PUBLIC_KEY, "github", "pk_QmFydD1TaW1wc29uLWdpdGh1Yg")
        );
    }
}
