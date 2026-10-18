# godikit-logger

[![License](https://img.shields.io/github/license/lentiancn/godikit-logger-java.svg)](https://github.com/lentiancn/godikit-logger-java/blob/main/LICENSE)
[![Codecov](https://img.shields.io/codecov/c/github/lentiancn/godikit-logger-java.svg)](https://codecov.io/gh/lentiancn/godikit-logger-java)
[![GitHub Release](https://img.shields.io/github/tag/lentiancn/godikit-logger-java.svg?label=release)](https://github.com/lentiancn/godikit-logger-java/releases)

English

## Why godikit-logger?

| Scenario | Without godikit-logger | With godikit-logger |
|----------|------------------------|---------------------|
| Switch logging backend | Modify multiple dependency configs | Swap provider JAR |
| Multiple logging APIs in dependencies | Configure bridges manually | Bundled bridges, auto-installed |
| No provider on classpath | `NoClassDefFoundError` | Built-in No-Op/System fallback |
| MDC trace ID | Manual configuration | Auto-injected in log pattern |

## Features

- **Zero-Config Provider Discovery**: JDK `ServiceLoader` auto-discovers providers — just add a JAR
- **Built-in Fallback**: No provider? No problem — silently falls back to No-Op or System.out
- **Unified Multi-Framework Bridge**: Redirects Commons Logging, JUL, Log4j, Log4j2, Flogger, and tinylog to your chosen backend
- **MDC Trace ID Out-of-the-Box**: Auto-injects `traceId` from MDC into every log line
- **Fluent Throwable Logging**: `logger.info(cause)` — log exceptions without manual message extraction
- **Logger Instance Caching**: ConcurrentHashMap-based caching for optimal performance
- **Full Log Level Support**: trace / debug / info / warn / error with colorized console output

## Architecture

```
┌──────────────────────────────────────────────────────────────┐
│                         godikit-logger                       │
│  (Core API: Logger interface + LoggerFactory entry)          │
│  Defines LoggerProvider SPI protocol + LoggerRegistry        │
│  Built-in providers: No-Op, System-out                       │
└──────────────────────────────────────────────────────────────┘
                              ▲
                      ServiceLoader
                              │
┌──────────────────────────────────────────────────────────────┐
│        godikit-logger-provider-slf4j2-logback                │
│  Facade adapter: Slf4j2LogbackLoggerImpl → SLF4J 2.x       │
│  Provides: colorized levels + MDC trace ID formatting        │
└──────────────────────────────────────────────────────────────┘
```

## Quick Start

### Maven

**Step 1 — Core API (always required)**

```xml
<dependency>
  <groupId>com.godikit.logger</groupId>
  <artifactId>godikit-logger</artifactId>
  <version>1.0.2</version>
</dependency>
```

**Step 2 — Provider (choose one)**

```xml
<!-- SLF4J 2.x + Logback — JDK 8 compatible (Recommended) -->
<dependency>
  <groupId>com.godikit.logger</groupId>
  <artifactId>godikit-logger-provider-slf4j2-logback</artifactId>
  <version>1.0.2</version>
</dependency>

<!-- SLF4J 2.x + Logback — JDK 17 (enables preview features) -->
<dependency>
  <groupId>com.godikit.logger</groupId>
  <artifactId>godikit-logger-provider-slf4j2-logback-jdk17</artifactId>
  <version>1.0.2</version>
</dependency>
```

### Usage

```java
Logger logger = LoggerFactory.getLogger(MyClass.class);

logger.info("Application started at {}", LocalDateTime.now());
logger.debug("Processing request: {}", requestId);
logger.warn("Usage high: {}%", usage);
logger.error(cause, "Request {} failed", requestId);
```

### MDC Trace ID

Set a `traceId` in SLF4J's MDC and it is automatically prepended to every log line
(when using the SLF4J+Logback provider):

```java
import org.slf4j.MDC;

MDC.put("traceId", UUID.randomUUID().toString());
Logger logger = LoggerFactory.getLogger(MyClass.class);
logger.info("Processing"); // → [a1b2c3d4...] Processing
```

### Without Any Provider

If only `godikit-logger` is on the classpath, two built-in fallback providers are
available automatically:

| Provider   | Output target               | Priority    |
|------------|-----------------------------|-------------|
| No-Op      | All messages discarded      | 0 (highest) |
| System-out | `System.out` / `System.err` | 1           |

## Requirements

- Java 8+
- Maven 3.6+

### Excluding Unused Logging Bridges

The `godikit-logger-provider-slf4j2-logback` module bundles all major logging bridges by default. Exclude unused ones:

```xml
<dependency>
    <groupId>com.godikit.logger</groupId>
    <artifactId>godikit-logger-provider-slf4j2-logback</artifactId>
    <version>1.0.2</version>
    <exclusions>
        <exclusion>
            <groupId>org.slf4j</groupId>
            <artifactId>jcl-over-slf4j</artifactId>
        </exclusion>
        <exclusion>
            <groupId>org.slf4j</groupId>
            <artifactId>jul-to-slf4j</artifactId>
        </exclusion>
        <exclusion>
            <groupId>org.slf4j</groupId>
            <artifactId>log4j-over-slf4j</artifactId>
        </exclusion>
        <exclusion>
            <groupId>org.apache.logging.log4j</groupId>
            <artifactId>log4j-to-slf4j</artifactId>
        </exclusion>
        <exclusion>
            <groupId>com.google.flogger</groupId>
            <artifactId>flogger-slf4j-backend</artifactId>
        </exclusion>
        <exclusion>
            <groupId>org.tinylog</groupId>
            <artifactId>tinylog-impl</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```

**Bundled bridges:** Commons Logging, JUL, Log4j, Log4j2, Flogger, tinylog → SLF4J

## License

`godikit-logger` is licensed under the [MIT License](LICENSE).

## Links

- Docs: https://www.godikit.com/logger
- Issues: https://github.com/lentiancn/godikit-logger-java/issues
