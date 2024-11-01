package com.github.bgalek.keyforge;

import java.util.Map;

public class MetadataApiKeyBuilder implements KeyBuilder<MetadataApiKey> {
    private Map<String, String> metadata;
    private String prefix;

    public MetadataApiKeyBuilder withMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
        return this;
    }

    public MetadataApiKeyBuilder withType(ApiKeyType type) {
        prefix = type.prefix();
        return this;
    }

    @Override
    public MetadataApiKey build() {
        return new MetadataApiKey(prefix, metadata);
    }
}
