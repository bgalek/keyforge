package com.github.bgalek.keyforge;

import java.util.Map;

public class ExpiringKeyExample {
    public static void main(String[] args) {
        MetadataKeyForge keyForge = new MetadataKeyForge();
        MetadataApiKey apiKey = keyForge.newKey()
                .withMetadata(Map.of("account", "123"))
                .build();
        System.out.println("Generated API Key: %s".formatted(apiKey));
        // Generated API Key: sk_YWNjb3VudD0xMjM
    }
}