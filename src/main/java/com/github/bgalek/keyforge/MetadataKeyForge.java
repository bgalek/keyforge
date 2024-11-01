package com.github.bgalek.keyforge;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class MetadataKeyForge implements IKeyForge<MetadataApiKey> {

    @Override
    public MetadataApiKeyBuilder generateKey() {
        return new MetadataApiKeyBuilder();
    }

    @Override
    public MetadataApiKey parse(String input) {
        String[] prefix = input.split("_", 2);
        String[] rest = new String(Base64.getUrlDecoder().decode(prefix[1]), StandardCharsets.UTF_8).split("-", 1);
        Map<String, String> metadata = Arrays.stream(rest[0].split("&")).reduce(new HashMap<>(), (map, entry) -> {
            String[] keyValue = entry.split("=");
            map.put(keyValue[0], keyValue[1]);
            return map;
        }, (map1, map2) -> {
            map1.putAll(map2);
            return map1;
        });
        return new MetadataApiKeyBuilder()
                .withMetadata(metadata)
                .withType(ApiKeyType.fromPrefix(prefix[0]))
                .generateKey();
    }
}
