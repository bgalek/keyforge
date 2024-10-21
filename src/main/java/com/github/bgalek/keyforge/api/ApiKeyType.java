package com.github.bgalek.keyforge.api;

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
}
