package com.github.bgalek.keyforge;

import java.util.Objects;

abstract class BaseApiKey<T> {
    private final T value;

    public BaseApiKey(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseApiKey)) return false;

        BaseApiKey<?> apiKey = (BaseApiKey<?>) o;
        return Objects.equals(value, apiKey.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public abstract String toString();
}
