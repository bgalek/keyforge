package com.github.bgalek.keyforge.api;

public interface KeyForge<T extends ApiKey> {
    /**
     * Generates a new API key.
     *
     * @param apiKeyType the type of the API key.
     * @param identifier the identifier associated with the API key.
     * @return the generated API key.
     */
    T generateKey(ApiKeyType apiKeyType, String identifier);

    /**
     * Parses the input string to an API key.
     *
     * @param input the input string.
     * @return the API key.
     */
    T parse(String input);

    /**
     * Serializes the API key to a string.
     *
     * @param apiKey the API key.
     * @return the serialized API key.
     */
    String serialize(T apiKey);

    /**
     * Returns the type of the API key.
     *
     * @param apiKey the API key.
     * @return the type of the API key.
     */
    default ApiKeyType getType(T apiKey) {
        return switch (apiKey.prefix()) {
            case "sk" -> ApiKeyType.SECRET_KEY;
            case "pk" -> ApiKeyType.PUBLIC_KEY;
            default -> throw new IllegalArgumentException("Unknown key type");
        };
    }
}
