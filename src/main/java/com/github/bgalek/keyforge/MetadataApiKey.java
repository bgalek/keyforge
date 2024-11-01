package com.github.bgalek.keyforge;


import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

public class MetadataApiKey extends BaseApiKey<Map<String, String>> {
    private final String prefix;

    MetadataApiKey(String prefix, Map<String, String> metadata) {
        super(metadata);
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public Map<String, String> getMetadata() {
        return this.getValue();
    }

    public ApiKeyType getType() {
        return ApiKeyType.fromPrefix(prefix);
    }

    @Override
    public String toString() {
        String metadata = this.getMetadata().entrySet().stream().map(entry -> "%s=%s".formatted(entry.getKey(), entry.getValue())).reduce("%s&%s"::formatted).orElse("");
        return String.format(
                "%s_%s",
                this.getPrefix(),
                Base64.getUrlEncoder()
                        .withoutPadding()
                        .encodeToString(metadata.getBytes(StandardCharsets.UTF_8))
        );
    }
}
