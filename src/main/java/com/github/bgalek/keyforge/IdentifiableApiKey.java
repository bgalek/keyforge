package com.github.bgalek.keyforge;

public abstract class IdentifiableApiKey<T> extends BaseApiKey<T> {
    private final String prefix;
    private final String identifier;

    IdentifiableApiKey(
            String prefix,
            String identifier,
            T value
    ) {
        super(value);
        this.prefix = prefix;
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getPrefix() {
        return prefix;
    }

    public ApiKeyType getType() {
        return ApiKeyType.fromPrefix(prefix);
    }
}
