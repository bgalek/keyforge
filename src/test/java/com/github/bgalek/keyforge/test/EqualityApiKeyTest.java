package com.github.bgalek.keyforge.test;

import com.github.bgalek.keyforge.ApiKeyType;
import com.github.bgalek.keyforge.ApiKey;
import com.github.bgalek.keyforge.TimestampedApiKeyBuilder;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EqualityApiKeyTest {

    @Test
    void anyKeysWithTheSameInternalValueAreEqual() {
        ApiKey a = new TimestampedApiKeyBuilder<>()
                .withValue(UUID.nameUUIDFromBytes("test".getBytes(StandardCharsets.UTF_8)))
                .withType(ApiKeyType.SECRET_KEY)
                .withIdentifier("opanapi")
                .build();
        ApiKey b = new TimestampedApiKeyBuilder<>()
                .withValue(UUID.nameUUIDFromBytes("test".getBytes(StandardCharsets.UTF_8)))
                .withType(ApiKeyType.PUBLIC_KEY)
                .withIdentifier("bgalek")
                .build();

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }
}
