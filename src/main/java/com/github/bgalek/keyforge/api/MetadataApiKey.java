package com.github.bgalek.keyforge.api;

import java.util.Map;

public class MetadataApiKey extends ApiKey {

    private final Map<String, String> metadata;

    public MetadataApiKey(String prefix, String identifier, Map<String, String> metadata) {
        super(prefix, identifier);
        this.metadata = metadata;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }
}
