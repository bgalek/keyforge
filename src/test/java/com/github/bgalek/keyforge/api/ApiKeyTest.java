package com.github.bgalek.keyforge.api;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ApiKeyTest {

    @Test
    void testHashCodeAndEquals() {
        ApiKey a = new TimestampedApiKey("sk", "opanapi", UUID.nameUUIDFromBytes("test".getBytes()));
        ApiKey b = new TimestampedApiKey("sk", "opanapi", UUID.nameUUIDFromBytes("test".getBytes()));

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void testHashCodeAndEquals2() {
        ApiKey a = new TimestampedApiKey("pk", "opanapi", UUID.randomUUID());
        ApiKey b = new TimestampedApiKey("pk", "opanapi", UUID.randomUUID());

        assertNotEquals(a, b);
        assertNotEquals(a.hashCode(), b.hashCode());
    }
}