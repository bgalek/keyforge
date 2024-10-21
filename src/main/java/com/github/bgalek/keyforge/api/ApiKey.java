package com.github.bgalek.keyforge.api;

public abstract class ApiKey {
    private final String prefix;
    private final String identifier;

    public ApiKey(
            String prefix,
            String identifier
    ) {
        this.prefix = prefix;
        this.identifier = identifier;
    }

    public String prefix() {
        return prefix;
    }

    public String identifier() {
        return identifier;
    }
}
