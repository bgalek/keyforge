package com.github.bgalek.keyforge;

import java.util.UUID;

public class TimestampedApiKeyBuilder<T extends ApiKey> extends IdentifiableApiKeyBuilder<TimestampedApiKeyBuilder<T>> implements KeyBuilder<T> {
    protected UUID uuid;

    public TimestampedApiKeyBuilder<T> withValue(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    @Override
    public TimestampedApiKeyBuilder<T> withType(ApiKeyType type) {
        this.prefix = type.prefix();
        return this;
    }

    @Override
    public TimestampedApiKeyBuilder<T> withIdentifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T build() {
        return (T) new ApiKey(prefix, identifier, uuid);
    }
}