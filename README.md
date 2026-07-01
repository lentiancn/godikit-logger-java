# godikit-logger

## Features

- Unified Logger facade for Java 17+
- SPI-based provider discovery (auto-detect backend)
- Runtime logging provider switching
- Logger instance caching for performance
- Full log levels support: trace / debug / info / warn / error
- Supports multiple backends: Log4j2, Logback, JUL, JCL, System.out fallback

## Modules

| Module | Purpose |
|--------|--------|
| `godikit-logger` | Core API (Logger + LoggerFactory) |
| `godikit-logger-with-slf4j-log4j2` | Log4j2 backend via SLF4J |
| `godikit-logger-with-slf4j-log4j2-config` | Log4j2 default config (colored console) |
| `godikit-logger-with-slf4j-logback` | Logback backend via SLF4J |
| `godikit-logger-with-slf4j-logback-config` | Logback default config (colored console) |

## Supported Backends

- Log4j2
- Logback
- Java Util Logging (via jul-to-slf4j)
- Commons Logging (via jcl-over-slf4j)
- System.out (fallback / no-op mode)

## Quick Start

### Maven (Core)

```xml
<dependency>
  <groupId>com.godikit.logger</groupId>
  <artifactId>godikit-logger</artifactId>
  <version>1.0.0-jdk17</version>
</dependency>
```

### With Log4j2

```xml
<dependency>
  <groupId>com.godikit.logger</groupId>
  <artifactId>godikit-logger-with-slf4j-log4j2</artifactId>
  <version>1.0.0-jdk17</version>
</dependency>
```

### With Logback

```xml
<dependency>
  <groupId>com.godikit.logger</groupId>
  <artifactId>godikit-logger-with-slf4j-logback</artifactId>
  <version>1.0.0-jdk17</version>
</dependency>
```

## Usage

```java
Logger logger = LoggerFactory.getLogger(MyClass.class);

logger.info("Started at {}", LocalDateTime.now());
logger.debug("Request: {}", requestId);
logger.warn("Usage high: {}%", usage);

logger.error(cause, "Failed: {}", requestId);

// switch backend
LoggerFactory.setProvider("godikit", "logback");
```

## Requirements

- Java 17+
- Maven 3.6+

## Dependencies

- SLF4J 2.0.18
- Log4j2 2.26.0 (optional)
- Logback 1.5.x (optional)

## License

MIT License

## Links

- Docs: https://www.godikit.com/logger
- Issues: https://gitee.com/lentiancn/godikit-logger-java/issues
