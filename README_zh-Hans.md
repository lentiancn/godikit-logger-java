# godikit-logger

[![License](https://img.shields.io/github/license/lentiancn/godikit-logger-java.svg)](https://github.com/lentiancn/godikit-logger-java/blob/main/LICENSE)
[![Codecov](https://img.shields.io/codecov/c/github/lentiancn/godikit-logger-java.svg)](https://codecov.io/gh/lentiancn/godikit-logger-java)
[![GitHub Release](https://img.shields.io/github/tag/lentiancn/godikit-logger-java.svg?label=release)](https://github.com/lentiancn/godikit-logger-java/releases)

简体中文 | [繁體中文](README_zh-Hant.md) | [English](README.md)

## 为什么选择 godikit-logger？

| 场景                     | 不使用 godikit-logger     | 使用 godikit-logger    |
|------------------------|------------------------|----------------------|
| 切换日志后端                 | 修改多个依赖配置               | 更换 Provider JAR 包    |
| 依赖中存在多个日志 API          | 手动配置桥接器                | 桥接器已内置，自动安装          |
| classpath 中没有 Provider | `NoClassDefFoundError` | 内置 No-Op/System 回退方案 |
| MDC 链路追踪 ID            | 手动配置                   | 日志模式中自动注入            |

## 功能特性

- **零配置 Provider 发现**：JDK `ServiceLoader` 自动发现 Provider — 只需添加一个 JAR 包

- **内置回退方案**：没有 Provider？没问题 — 自动静默回退到 No-Op 或 System.out

- **统一多框架桥接**：将 Commons Logging、JUL、Log4j、Log4j2、Flogger 和 tinylog 重定向到您选择的后端

- **开箱即用的 MDC 链路 ID**：自动将 MDC 中的 `traceId` 注入到每条日志

- **流畅的异常日志记录**：`logger.info(cause)` — 记录异常无需手动提取信息

- **Logger 实例缓存**：基于 ConcurrentHashMap 的缓存机制，提供最优性能

- **完整的日志级别支持**：trace / debug / info / warn / error，支持彩色控制台输出

## 架构设计

```
┌──────────────────────────────────────────────────────────────┐
│                         godikit-logger                       │
│  (核心 API: Logger 接口 + LoggerFactory 入口)                │
│  定义 LoggerProvider SPI 协议 + LoggerRegistry               │
│  内置 Provider: No-Op, System-out                            │
└──────────────────────────────────────────────────────────────┘
                              ▲
                      ServiceLoader
                              │
┌──────────────────────────────────────────────────────────────┐
│        godikit-logger-provider-slf4j2-logback                │
│  门面适配器: Slf4j2LogbackLoggerImpl → SLF4J 2.x            │
│  提供: 彩色日志级别 + MDC 链路 ID 格式化                      │
└──────────────────────────────────────────────────────────────┘
```

## 环境要求

- Java 8+
- Maven 3.6+

## 使用方法

### Maven 依赖

```xml
<!-- SLF4J 2.x + Logback — JDK 8 兼容 (推荐) -->
<dependency>
  <groupId>com.godikit.logger</groupId>
  <artifactId>godikit-logger-provider-slf4j2-logback</artifactId>
  <version>1.0.4</version>
</dependency>

<!-- SLF4J 2.x + Logback — JDK 17 -->
<dependency>
<groupId>com.godikit.logger</groupId>
<artifactId>godikit-logger-provider-slf4j2-logback-jdk17</artifactId>
<version>1.0.4</version>
</dependency>
```

这将自动将 Commons Logging、JUL、Log4j、Log4j2、Flogger 和 tinylog 重定向到 SLF4J。

### 代码示例

```java
import com.godikit.logger.Logger;
import com.godikit.logger.LoggerFactory;

Logger logger = LoggerFactory.getLogger(MyClass.class);

logger.info("Application started at {}", LocalDateTime.now());
logger.debug("Processing request: {}", requestId);
logger.warn("Usage high: {}%", usage);
logger.error(cause, "Request {} failed", requestId);
```

### 自定义配置（可选）

`-config` 模块（如 `godikit-logger-provider-slf4j2-logback-config`）绑定了默认的 `logback.xml`。要使用您自己的配置，请排除
config 包：

```xml
<dependency>
  <groupId>com.godikit.logger</groupId>
  <artifactId>godikit-logger-provider-slf4j2-logback</artifactId>
  <version>1.0.4</version>
  <exclusions>
    <exclusion>
      <groupId>com.godikit.logger</groupId>
      <artifactId>godikit-logger-provider-slf4j2-logback-config</artifactId>
    </exclusion>
  </exclusions>
</dependency>
```

### 彩色日志级别（可选）

要在控制台输出中为日志级别着色（TRACE=青色，DEBUG=蓝色，INFO=绿色，WARN=橙色，ERROR=红色+粗体）：

```xml

<conversionRule conversionWord="gtLevelBasedColor"
                converterClass="com.godikit.logger.spi.provider.converter.LevelBasedColorCompositeConverter"/>

<property name="LOG_PATTERN"
          value="%gtLevelBasedColor([%d{yyyy-MM-dd HH:mm:ss}]) %gtLevelBasedColor(%level) %gtTraceId(%msg)%n"/>
```

### 链路 ID 配置（可选）

链路 ID 是可选的。要启用它，请在 MDC 中设置 `traceId`：

```java
import org.slf4j.MDC;

MDC.put("traceId", UUID.randomUUID().toString());
Logger logger = LoggerFactory.getLogger(MyClass.class);
logger.info("Processing"); // → [a1b2c3d4...] Processing
```

在 `logback.xml` 中，添加转换规则并在模式中使用 `%gtTraceId(...)`：

```xml
<conversionRule conversionWord="gtTraceId"
                converterClass="com.godikit.logger.spi.provider.converter.TraceIdCompositeConverter"/>

<property name="LOG_PATTERN"
          value="%d{yyyy-MM-dd HH:mm:ss} %level %gtTraceId(%msg)%n"/>
```

如果 MDC 中未设置 `traceId`，`%gtTraceId(...)` 默认不输出任何内容。

### 排除不需要的日志桥接器（可选）

`godikit-logger-provider-slf4j2-logback` 模块默认绑定了所有主流日志桥接器。如果您不需要某些桥接器，可以排除它们以减少不必要的依赖：

```xml
<dependency>
  <groupId>com.godikit.logger</groupId>
  <artifactId>godikit-logger-provider-slf4j2-logback</artifactId>
  <version>1.0.4</version>
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

**已绑定的桥接器：** Commons Logging, JUL, Log4j, Log4j2, Flogger, tinylog → SLF4J

## 许可证

`godikit-logger` 采用 [MIT 许可证](LICENSE)。

## 相关链接

- 文档: https://www.godikit.com/logger
- 问题反馈: https://github.com/lentiancn/godikit-logger-java/issues
