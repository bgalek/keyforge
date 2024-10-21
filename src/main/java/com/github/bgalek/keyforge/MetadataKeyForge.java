package com.github.bgalek.keyforge;

import com.github.bgalek.keyforge.api.ApiKey;
import com.github.bgalek.keyforge.api.ApiKeyType;
import com.github.bgalek.keyforge.api.KeyForge;
import com.github.bgalek.keyforge.api.MetadataApiKey;
import com.github.bgalek.keyforge.traits.IncludingMetadata;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class MetadataKeyForge<T extends ApiKey> implements IncludingMetadata<MetadataApiKey>, KeyForge<MetadataApiKey> {

    public MetadataApiKey generateKey(ApiKeyType apiKeyType, String identifier) {
        return generateKey(apiKeyType, identifier, Map.of());
    }

    @Override
    public MetadataApiKey generateKey(ApiKeyType apiKeyType, String identifier, Map<String, String> metadata) {
        return new MetadataApiKey(
                apiKeyType.prefix(),
                identifier,
                metadata
        );
    }

    @Override
    public MetadataApiKey parse(String input) {
        String[] prefix = input.split("_", 2);
        String[] rest = new String(Base64.getUrlDecoder().decode(prefix[1]), StandardCharsets.UTF_8).split("-", 2);
        Map<String, String> metadata = Arrays.stream(rest[0].split("&")).reduce(new HashMap<>(), (map, entry) -> {
            String[] keyValue = entry.split("=");
            map.put(keyValue[0], keyValue[1]);
            return map;
        }, (map1, map2) -> {
            map1.putAll(map2);
            return map1;
        });
        String identifier = rest[1];
        return new MetadataApiKey(prefix[0], identifier, metadata);
    }

    @Override
    public String serialize(MetadataApiKey apiKey) {
        String metadata = apiKey.getMetadata().entrySet().stream().map(entry -> "%s=%s".formatted(entry.getKey(), entry.getValue())).reduce("%s&%s"::formatted).orElse("");
        return String.format(
                "%s_%s",
                apiKey.prefix(),
                Base64.getUrlEncoder().withoutPadding().encodeToString((metadata + "-" + apiKey.identifier()).getBytes(StandardCharsets.UTF_8))
        );
    }

    @Override
    public Map<String, String> getMetadata() {
        return Map.of();
    }
}
