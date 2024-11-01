package com.github.bgalek.keyforge;

public enum ApiKeyType {
    SECRET_KEY("sk"),
    PUBLIC_KEY("pk");

    private final String prefix;

    ApiKeyType(String value) {
        this.prefix = value;
    }

    public String prefix() {
        return prefix;
    }

    public static ApiKeyType fromPrefix(String prefix) {
        for (ApiKeyType type : values()) {
            if (type.prefix.equals(prefix)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown ApiKeyType: " + prefix);
    }
}
