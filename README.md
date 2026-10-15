# godikit-logger

[![License](https://img.shields.io/github/license/lentiancn/godikit-logger-java.svg)](https://github.com/lentiancn/godikit-logger-java/blob/main/LICENSE)
[![Codecov](https://img.shields.io/codecov/c/github/lentiancn/godikit-logger-java.svg)](https://codecov.io/gh/lentiancn/godikit-logger-java)
[![GitHub Release](https://img.shields.io/github/tag/lentiancn/godikit-logger-java.svg?label=release)](https://github.com/lentiancn/godikit-logger-java/releases)

English

## Features

- Unified Logger facade for Java 8+
- JDK standard `ServiceLoader` SPI discovery — no manual configuration required
- Automatic provider selection: when multiple providers are present, the one with the lowest priority wins
- Built-in No-Op and System-out fallback providers (zero external dependency when only `godikit-logger` is used)
- Logger instance caching via `ConcurrentHashMap`
- Full log level support: `trace / debug / info / warn / error`
- Throwable-only overloads for each level (e.g. `logger.info(cause)`)
- Colorized console output and MDC-based trace ID injection (via SLF4J+Logback provider)

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
│        godikit-logger-provider-slf4j-logback                 │
│  Facade adapter: Slf4jLogbackLoggerImpl → SLF4J Logger       │
│  Provides: colorized levels + MDC trace ID formatting        │
└──────────────────────────────────────────────────────────────┘
```

## Modules

| Module                                         | Purpose                                                           |
|------------------------------------------------|-------------------------------------------------------------------|
| `godikit-logger`                               | Core API: `Logger` interface, `LoggerFactory`, SPI registry       |
| `godikit-logger-jdk17`                         | JDK 17 compilation verification (pattern matching for switch)     |
| `godikit-logger-provider-slf4j-logback`        | SLF4J + Logback adapter (colorized console, trace ID from MDC)    |
| `godikit-logger-provider-slf4j-logback-config` | Default Logback configuration (auto-registered via Configurator)  |
| `godikit-logger-demo`                          | Minimal usage demos (`LogBasic`, `LogWithTraceId`)                |

## Quick Start

### Maven

**Step 1 — Core API (always required)**

```xml
<dependency>
  <groupId>com.godikit.logger</groupId>
  <artifactId>godikit-logger</artifactId>
  <version>1.0.1</version>
</dependency>
```

**Step 2 — Provider (choose one)**

```xml
<!-- SLF4J + Logback with colorized console and MDC trace ID support -->
<dependency>
  <groupId>com.godikit.logger</groupId>
  <artifactId>godikit-logger-provider-slf4j-logback</artifactId>
  <version>1.0.1</version>
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

| Provider     | Output target                   | Priority |
|--------------|---------------------------------|----------|
| No-Op        | All messages discarded          | 0 (highest) |
| System-out   | `System.out` / `System.err`     | 1        |

## Requirements

- Java 8+
- Maven 3.6+

## Dependencies

| Dependency           | Scope    | Notes                                                |
|----------------------|----------|------------------------------------------------------|
| `godikit-exception`  | compile  | Required by `LoggerFactory`                          |
| `slf4j-api`          | compile  | Only when a provider is present                      |
| `logback-classic`    | compile  | Only with `godikit-logger-provider-slf4j-logback`    |
| `lombok`             | provided | Code generation for `@NoArgsConstructor`, etc.       |
| `junit-jupiter`      | test     | Test framework                                       |

## License

`godikit-logger` is licensed under the [MIT License](LICENSE).

## Links

- Docs: https://www.godikit.com/logger
- Issues: https://github.com/lentiancn/godikit-logger-java/issues
