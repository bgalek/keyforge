package com.github.bgalek.keyforge;

public interface IKeyForge<T> {
    /**
     * Generates a new API key.
     *
     * @return the key builder.
     */
    KeyBuilder<T> newKey();

    /**
     * Parses the input string to an API key.
     *
     * @param input the input string.
     * @return the API key.
     */
    T parse(String input);
}
