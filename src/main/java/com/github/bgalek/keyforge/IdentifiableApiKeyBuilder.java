package com.github.bgalek.keyforge;

public abstract class IdentifiableApiKeyBuilder<T> {
    protected String prefix;
    protected String identifier;

    abstract T withIdentifier(String identifier);

    abstract T withType(ApiKeyType type);
}
