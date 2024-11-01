# KeyForge Library

KeyForge is a Java library designed to generate, parse,
and manage API keys with optional expiration functionality. It
supports both simple and expiring API keys, providing
a flexible and secure way to handle API key management.

## Features

- **Generate API Keys**: Create new API keys with or without expiration.
- **Parse API Keys**: Decode and extract information from existing API keys.
- **Expiration Handling**: Automatically manage and check the expiration of API keys.
- **Serialization**: Convert API keys to and from string representations.

## Installation

To include KeyForge in your project,
add the following dependency to your `build.gradle` file:

```kts
dependencies {
    implementation("com.github.bgalek:keyforge:1.0.0")
}
```

## Usage

### Generating a Simple API Key

```java
import com.github.bgalek.keyforge.*;

public class Example {
    public static void main(String[] args) {
        KeyForge keyForge = new KeyForge();
        ApiKey apiKey = keyForge.generateKey()
                .withIdentifier("bgalek")
                .withType(ApiKeyType.SECRET_KEY)
                .generateKey();
        System.out.println("Generated API Key: " + keyForge.serialize(apiKey));
    }
}

