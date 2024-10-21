package com.github.bgalek.keyforge.api;

import java.util.Objects;
import java.util.UUID;

public class TimestampedApiKey extends ApiKey {
    private final UUID value;

    public TimestampedApiKey(
            String prefix,
            String identifier,
            UUID value
    ) {
        super(prefix, identifier);
        this.value = value;
    }

    public UUID value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimestampedApiKey that = (TimestampedApiKey) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
