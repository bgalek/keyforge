# KeyForge

> KeyForge is a Java library designed to generate and parse API keys
> with optional expiration functionality.

[![Build](https://github.com/bgalek/keyforge/actions/workflows/build.yaml/badge.svg?branch=main)](https://github.com/bgalek/keyforge/actions/workflows/build.yaml)
![Codecov](https://img.shields.io/codecov/c/github/bgalek/keyforge.svg?style=flat-square)
![GitHub Release Date](https://img.shields.io/github/release-date/bgalek/keyforge.svg?style=flat-square)
![Maven Central](https://img.shields.io/maven-central/v/com.github.bgalek/keyforge?style=flat-square)

## What Makes a Good API Key:

- **Security**: API keys should be unique, hard to guess, and securely stored.
- **Expiration**: Keys should have an expiration date to limit the risk if they are compromised.
- **Identification**: Keys should include metadata to identify the client or user they are associated with.
- **Revocation**: It should be possible to revoke keys if they are compromised or no longer needed.

## Installation

To include KeyForge in your project,
add the following dependency to your `build.gradle.kts` file:

```kts
dependencies {
    implementation("com.github.bgalek:keyforge:1.0.0")
}
```

## Usage

### Standard KeyForge

```java
public class Example {
    public static void main(String[] args) {
        KeyForge keyForge = new KeyForge();
        ApiKey apiKey = keyForge.newKey().withIdentifier("myapp").build();
        System.out.println("Generated API Key: %s".formatted(apiKey));
        //Generated Expiring API Key: sk_myapp_MDE5MmVkOGFhZGMyNzRiZGJlYTk4M2E4ZDk3NGU4NTc
    }
}
```

### Expiring KeyForge

```java
public class Example {
    public static void main(String[] args) {
        ExpiringKeyForge keyForge = new ExpiringKeyForge();
        ApiKey apiKey = keyForge.newKey()
                .withIdentifier("myapp")
                .withValidFor(Duration.ofMinutes(15))
                .build();
        System.out.println("Generated Expiring API Key: %s".formatted(apiKey));
        // Generated Expiring API Key: sk_myapp_MDE5MmVkODZmZWQzNzM2MDg2YWQ0MmYxNzYwOGM2N2UtMTczMDU2MjgwMA
    }
}
```

### Custom metadata KeyForge

```java
public class Example {
    public static void main(String[] args) {
        MetadataKeyForge keyForge = new MetadataKeyForge();
        MetadataApiKey apiKey = keyForge.newKey()
                .withMetadata(Map.of("account", "123"))
                .build();
        System.out.println("Generated API Key: %s".formatted(apiKey));
        // Generated API Key: sk_YWNjb3VudD0xMjM
    }
}
```

### Parsing API Keys

```java
    KeyForge keyForge = new KeyForge();
    ApiKey apiKey = keyForge.parse("sk_YWNjb3VudD0xMjM");
    System.out.println("Parsed API Key: %s".formatted(apiKey));
```
