package com.github.bgalek.keyforge;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Forge for {@link MetadataApiKey}.
 */
public class MetadataKeyForge implements IKeyForge<MetadataApiKey> {

    @Override
    public MetadataApiKeyBuilder newKey() {
        return new MetadataApiKeyBuilder()
                .withType(ApiKeyType.SECRET_KEY);
    }

    @Override
    public MetadataApiKey parse(String input) {
        String[] prefix = input.split("_", 2);
        String[] rest = new String(Base64.getUrlDecoder().decode(prefix[1]), StandardCharsets.UTF_8).split("-", 1);
        Map<String, String> metadata = Arrays.stream(rest[0].split("&"))
                .map(entry -> entry.split("="))
                .collect(Collectors.toMap(keyValue -> keyValue[0], keyValue -> keyValue[1]));
        return new MetadataApiKeyBuilder()
                .withMetadata(metadata)
                .withType(ApiKeyType.SECRET_KEY)
                .withType(ApiKeyType.fromPrefix(prefix[0]))
                .build();
    }
}
