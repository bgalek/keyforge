# KeyForge

> KeyForge is a Java library for generating and parsing structured API keys
> with optional expiration and custom metadata.

[![Build](https://github.com/bgalek/keyforge/actions/workflows/build.yaml/badge.svg?branch=main)](https://github.com/bgalek/keyforge/actions/workflows/build.yaml)
![Codecov](https://img.shields.io/codecov/c/github/bgalek/keyforge.svg?style=flat-square)
![GitHub Release Date](https://img.shields.io/github/release-date/bgalek/keyforge.svg?style=flat-square)
![Maven Central](https://img.shields.io/maven-central/v/com.github.bgalek/keyforge?style=flat-square)

## What Makes a Good API Key

- **Security**: API keys should be unique, hard to guess, and securely stored.
- **Expiration**: Keys should have an expiration date to limit the risk if they are compromised.
- **Identification**: Keys should include metadata to identify the client or user they are associated with.
- **Revocation**: It should be possible to revoke keys if they are compromised or no longer needed.

## Requirements

- Java 17 or higher

## Installation

```kts
dependencies {
    implementation("com.github.bgalek:keyforge:1.0.2")
}
```

## Usage

### Standard KeyForge

Generates a timestamped key backed by a UUIDv7. The issued-at timestamp can be recovered from the key itself.

```java
public class Example {
    public static void main(String[] args) {
        KeyForge keyForge = new KeyForge();
        ApiKey apiKey = keyForge.newKey()
                .withIdentifier("myapp")
                .build();
        System.out.println(apiKey);
        // sk_myapp_MDE5MmVkOGFhZGMyNzRiZGJlYTk4M2E4ZDk3NGU4NTc

        System.out.println(apiKey.getIssuedAt());
        // 2024-10-20T15:03:05.930Z

        ApiKey parsed = keyForge.parse(apiKey.toString());
        System.out.println(parsed.getIdentifier()); // myapp
    }
}
```

### Expiring KeyForge

Embeds an expiration timestamp in the key. Call `isExpired(clock)` to validate at request time.

```java
public class Example {
    public static void main(String[] args) {
        ExpiringKeyForge keyForge = new ExpiringKeyForge();
        ExpiringApiKey apiKey = keyForge.newKey()
                .withIdentifier("myapp")
                .withValidFor(Duration.ofMinutes(15))
                .build();
        System.out.println(apiKey);
        // sk_myapp_MDE5MmVkODZmZWQzNzM2MDg2YWQ0MmYxNzYwOGM2N2UtMTczMDU2MjgwMA

        ExpiringApiKey parsed = keyForge.parse(apiKey.toString());
        System.out.println(parsed.getIdentifier());       // myapp
        System.out.println(parsed.getExpirationDate());   // 2024-10-20T15:18:05Z
        System.out.println(parsed.isExpired(Clock.systemUTC())); // false
    }
}
```

### Metadata KeyForge

Embeds arbitrary key-value metadata in the key instead of a UUID.

```java
public class Example {
    public static void main(String[] args) {
        MetadataKeyForge keyForge = new MetadataKeyForge();
        MetadataApiKey apiKey = keyForge.newKey()
                .withMetadata(Map.of("account", "123"))
                .build();
        System.out.println(apiKey);
        // sk_YWNjb3VudD0xMjM

        MetadataApiKey parsed = keyForge.parse(apiKey.toString());
        System.out.println(parsed.getMetadata()); // {account=123}
    }
}
```

### Key types

Both `sk` (secret key) and `pk` (public key) prefixes are supported via `ApiKeyType`:

```java
ApiKey apiKey = keyForge.newKey()
        .withType(ApiKeyType.PUBLIC_KEY)
        .withIdentifier("myapp")
        .build();
// pk_myapp_...
```

### Custom clock

All forge implementations accept an optional `Clock` for testing or time-zone control:

```java
KeyForge keyForge = new KeyForge(Clock.fixed(Instant.parse("2024-01-01T00:00:00Z"), ZoneOffset.UTC));
```
