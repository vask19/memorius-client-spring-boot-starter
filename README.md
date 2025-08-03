# Memorius Spring Boot Starter

A lightweight Spring Boot starter for interacting with a Memorius key-value store over raw RESP (REdis Serialization Protocol) using sockets.

## Features

- Spring Boot autoconfiguration for `MemoriusClient`
- Simple, dependency-free TCP client (no Redis/Jedis libraries)
- Minimal RESP implementation (reader/writer)
- Supports the following operations:
    - `RECORD`, `RETRIEVE`, `EXISTS`, `PURGE`
    - List operations: `APPEND`, `COUNT`, `SUMMON`
    - TTL expiration: `EXPIATE`

---

## Installation

### 1. Add the JitPack repository

Add the following to your `pom.xml` JitPack repo:

```xml
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>

Add dependency:

<dependency>
  <groupId>com.github.vkorol</groupId>
  <artifactId>memorius-client-spring-boot-starter</artifactId>
  <version>1.0.0</version>
</dependency>
