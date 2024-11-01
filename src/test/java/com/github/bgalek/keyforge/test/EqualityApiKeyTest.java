package com.github.bgalek.keyforge.test;

import com.github.bgalek.keyforge.ApiKeyType;
import com.github.bgalek.keyforge.ApiKey;
import com.github.bgalek.keyforge.TimestampedApiKeyBuilder;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EqualityApiKeyTest {

    @Test
    void anyKeysWithTheSameInternalValueAreEqual() {
        ApiKey a = new TimestampedApiKeyBuilder<>()
                .withValue(UUID.nameUUIDFromBytes("test".getBytes()))
                .withType(ApiKeyType.SECRET_KEY)
                .withIdentifier("opanapi")
                .build();
        ApiKey b = new TimestampedApiKeyBuilder<>()
                .withValue(UUID.nameUUIDFromBytes("test".getBytes()))
                .withType(ApiKeyType.PUBLIC_KEY)
                .withIdentifier("bgalek")
                .build();

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }
}