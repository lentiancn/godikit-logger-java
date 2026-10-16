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

| Module                                               | Purpose                                                                           |
|------------------------------------------------------|-----------------------------------------------------------------------------------|
| `godikit-logger`                                     | Core API: `Logger` interface, `LoggerFactory`, SPI registry                       |
| `godikit-logger-jdk17`                               | JDK 17 build of core API (enables JDK 17 preview features)                        |
| `godikit-logger-provider-slf4j-logback`              | SLF4J + Logback adapter — JDK 8 compatible (colorized console, trace ID from MDC) |
| `godikit-logger-provider-slf4j-logback-jdk17`        | JDK 17 build of the SLF4J + Logback provider (enables JDK 17 preview features)    |
| `godikit-logger-provider-slf4j-logback-config`       | Default Logback configuration (auto-registered via Configurator SPI)              |
| `godikit-logger-provider-slf4j-logback-config-jdk17` | JDK 17 build of Logback configuration (enables JDK 17 preview features)           |
| `godikit-logger-example`                             | Minimal usage examples (`LogBasic`, `LogWithTraceId`)                             |
| `godikit-logger-example-jdk17`                       | JDK 17 build of usage examples (enables JDK 17 preview features)                  |

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
<!-- SLF4J + Logback — JDK 8 compatible -->
<dependency>
  <groupId>com.godikit.logger</groupId>
  <artifactId>godikit-logger-provider-slf4j-logback</artifactId>
  <version>1.0.2</version>
</dependency>

  <!-- SLF4J + Logback — JDK 17 (enables preview features) -->
<dependency>
  <groupId>com.godikit.logger</groupId>
  <artifactId>godikit-logger-provider-slf4j-logback-jdk17</artifactId>
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

## Dependencies

| Dependency          | Scope    | Notes                                             |
|---------------------|----------|---------------------------------------------------|
| `godikit-exception` | compile  | Required by `LoggerFactory`                       |
| `slf4j-api`         | compile  | Only when a provider is present                   |
| `logback-classic`   | compile  | Only with `godikit-logger-provider-slf4j-logback` |
| `lombok`            | provided | Code generation for `@NoArgsConstructor`, etc.    |
| `junit-jupiter`     | test     | Test framework                                    |

## Third-Party Logging Framework Bridging

When integrating `godikit-logger`, you may need to maintain compatibility with existing logging frameworks used by your dependencies (e.g., Spring Framework uses Commons Logging). Add bridges as needed:

| Legacy Framework | Bridge | Maven Coordinates |
|------------------|--------|-------------------|
| Commons Logging | jcl-over-slf4j | `org.slf4j:jcl-over-slf4j` |
| Log4j 1.x | log4j-over-slf4j | `org.slf4j:log4j-over-slf4j` |
| Log4j 2.x | log4j-to-slf4j | `org.apache.logging.log4j:log4j-to-slf4j` |
| java.util.logging | jul-to-slf4j | `org.slf4j:jul-to-slf4j` |
| Flogger | flogger-slf4j-backend | `com.google.flogger:flogger-slf4j-backend` |

> **Tip:** Use `provided` scope for bridge dependencies to avoid bundling them into your final artifact.

### Example: Integrate with Commons Logging

```xml
<dependencies>
    <!-- Core: godikit-logger + Logback -->
    <dependency>
        <groupId>com.godikit.logger</groupId>
        <artifactId>godikit-logger-provider-slf4j-logback</artifactId>
        <version>1.0.2</version>
    </dependency>

    <!-- Exclude or set provided for original Commons Logging -->
    <dependency>
        <groupId>commons-logging</groupId>
        <artifactId>commons-logging</artifactId>
        <version>1.3.4</version>
        <scope>provided</scope>
    </dependency>

    <!-- Bridge: redirect Commons Logging to SLF4J -->
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>jcl-over-slf4j</artifactId>
        <version>2.0.16</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```

## Examples

See `godikit-logger-example-slf4j-logback` module for usage examples covering various logging frameworks:

| Example | Demonstrates |
|---------|--------------|
| `LogWithSlf4jLogback` | Basic godikit-logger usage |
| `LogWithSlf4jLogbackUsingTraceId` | MDC-based trace ID injection |
| `LogWithCommonsLogging` | Commons Logging → SLF4J bridge |
| `LogWithJBossLogging` | JBoss Logging → SLF4J bridge |
| `LogWithJUL` | java.util.logging → SLF4J bridge |
| `LogWithLog4j1` | Log4j 1.x → SLF4J bridge |
| `LogWithLog4j2` | Log4j 2.x native API |
| `LogWithFlogger` | Flogger → SLF4J bridge |

## License

`godikit-logger` is licensed under the [MIT License](LICENSE).

## Links

- Docs: https://www.godikit.com/logger
- Issues: https://github.com/lentiancn/godikit-logger-java/issues
