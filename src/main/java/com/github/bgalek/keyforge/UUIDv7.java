package com.github.bgalek.keyforge;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.time.Clock;
import java.time.Instant;
import java.util.UUID;

class UUIDv7 {
    private final Clock clock;
    private final SecureRandom random = new SecureRandom();

    UUIDv7(Clock clock) {
        this.clock = clock;
    }

    UUID randomUUID() {
        byte[] value = randomBytes();
        ByteBuffer buf = ByteBuffer.wrap(value);
        long high = buf.getLong();
        long low = buf.getLong();
        return new UUID(high, low);
    }

    byte[] randomBytes() {
        // random bytes
        byte[] value = new byte[16];
        this.random.nextBytes(value);

        // current timestamp in ms
        ByteBuffer timestamp = ByteBuffer.allocate(Long.BYTES);
        timestamp.putLong(clock.millis());

        // timestamp
        System.arraycopy(timestamp.array(), 2, value, 0, 6);

        // version and variant
        value[6] = (byte) ((value[6] & 0x0F) | 0x70);
        value[8] = (byte) ((value[8] & 0x3F) | 0x80);

        return value;
    }

    static Instant getIssuedAt(UUID uuid) {
        long unixTimestamp = getTimeOrderedEpochTimestamp(uuid.getMostSignificantBits());
        final long seconds = unixTimestamp / 10_000_000;
        final long nanos = (unixTimestamp % 10_000_000) * 100;
        return Instant.ofEpochSecond(seconds, nanos);
    }

    private static long getTimeOrderedEpochTimestamp(long msb) {
        final long ticksPerMilli = 10_000;
        return ((msb & 0xffffffffffff0000L) >>> 16) * ticksPerMilli;
    }
}