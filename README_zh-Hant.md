# godikit-logger

[![License](https://img.shields.io/github/license/lentiancn/godikit-logger-java.svg)](https://github.com/lentiancn/godikit-logger-java/blob/main/LICENSE)
[![Codecov](https://img.shields.io/codecov/c/github/lentiancn/godikit-logger-java.svg)](https://codecov.io/gh/lentiancn/godikit-logger-java)
[![GitHub Release](https://img.shields.io/github/tag/lentiancn/godikit-logger-java.svg?label=release)](https://github.com/lentiancn/godikit-logger-java/releases)

繁體中文 | [English](README.md) | [简体中文](README_zh-Hans.md)

## 為什麼選擇 godikit-logger？

| 場景                     | 不使用 godikit-logger     | 使用 godikit-logger    |
|------------------------|------------------------|----------------------|
| 切換日誌後端                 | 修改多個依賴配置               | 更換 Provider JAR 套件   |
| 依賴中存在多個日誌 API          | 手動配置橋接器                | 橋接器已內建，自動安裝          |
| classpath 中沒有 Provider | `NoClassDefFoundError` | 內建 No-Op/System 降級方案 |
| MDC 鏈路追蹤 ID            | 手動配置                   | 日誌模式中自動注入            |

## 功能特性

- **零配置 Provider 發現**：JDK `ServiceLoader` 自動發現 Provider — 只需新增一個 JAR 套件

- **內建降級方案**：沒有 Provider？沒問題 — 自動靜默降級到 No-Op 或 System.out

- **統一多框架橋接**：將 Commons Logging、JUL、Log4j、Log4j2、Flogger 和 tinylog 重新導向到您選擇的後端

- **開箱即用的 MDC 鏈路 ID**：自動將 MDC 中的 `traceId` 注入到每條日誌

- **流暢的例外日誌記錄**：`logger.info(cause)` — 記錄例外無需手動提取資訊

- **Logger 執行個體快取**：基於 ConcurrentHashMap 的快取機制，提供最佳效能

- **完整的日誌級別支援**：trace / debug / info / warn / error，支援彩色主控台輸出

## 架構設計

```
┌──────────────────────────────────────────────────────────────┐
│                         godikit-logger                       │
│  (核心 API: Logger 介面 + LoggerFactory 入口)                │
│  定義 LoggerProvider SPI 協定 + LoggerRegistry               │
│  內建 Provider: No-Op, System-out                            │
└──────────────────────────────────────────────────────────────┘
                              ▲
                      ServiceLoader
                              │
┌──────────────────────────────────────────────────────────────┐
│        godikit-logger-provider-slf4j2-logback                │
│  門面適配器: Slf4j2LogbackLoggerImpl → SLF4J 2.x             │
│  提供: 彩色日誌級別 + MDC 鏈路 ID 格式化                      │
└──────────────────────────────────────────────────────────────┘
```

## 環境需求

- Java 8+
- Maven 3.6+

## 使用方法

### Maven 依賴

```xml
<!-- SLF4J 2.x + Logback — JDK 8 相容 (推薦) -->
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

這將自動將 Commons Logging、JUL、Log4j、Log4j2、Flogger 和 tinylog 重新導向到 SLF4J。

### 程式碼範例

```java
import com.godikit.logger.Logger;
import com.godikit.logger.LoggerFactory;

Logger logger = LoggerFactory.getLogger(MyClass.class);

logger.info("Application started at {}", LocalDateTime.now());
logger.debug("Processing request: {}", requestId);
logger.warn("Usage high: {}%", usage);
logger.error(cause, "Request {} failed", requestId);
```

### 自訂配置（可選）

`-config` 模組（如 `godikit-logger-provider-slf4j2-logback-config`）綁定了預設的 `logback.xml`。要使用您自己的配置，請排除
config 套件：

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

### 彩色日誌級別（可選）

要在主控台輸出中為日誌級別上色（TRACE=青色，DEBUG=藍色，INFO=綠色，WARN=橙色，ERROR=紅色+粗體）：

```xml

<conversionRule conversionWord="gtLevelBasedColor"
                converterClass="com.godikit.logger.spi.provider.converter.LevelBasedColorCompositeConverter"/>

<property name="LOG_PATTERN"
          value="%gtLevelBasedColor([%d{yyyy-MM-dd HH:mm:ss}]) %gtLevelBasedColor(%level) %gtTraceId(%msg)%n"/>
```

### 鏈路 ID 配置（可選）

鏈路 ID 是可選的。要啟用它，請在 MDC 中設定 `traceId`：

```java
import org.slf4j.MDC;

MDC.put("traceId", UUID.randomUUID().toString());
Logger logger = LoggerFactory.getLogger(MyClass.class);
logger.info("Processing"); // → [a1b2c3d4...] Processing
```

在 `logback.xml` 中，新增轉換規則並在模式中使用 `%gtTraceId(...)`：

```xml
<conversionRule conversionWord="gtTraceId"
                converterClass="com.godikit.logger.spi.provider.converter.TraceIdCompositeConverter"/>

<property name="LOG_PATTERN"
          value="%d{yyyy-MM-dd HH:mm:ss} %level %gtTraceId(%msg)%n"/>
```

如果 MDC 中未設定 `traceId`，`%gtTraceId(...)` 預設不輸出任何內容。

### 排除不需要的日誌橋接器（可選）

`godikit-logger-provider-slf4j2-logback` 模組預設綁定了所有主流日誌橋接器。如果您不需要某些橋接器，可以排除它們以減少不必要的依賴：

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

**已綁定的橋接器：** Commons Logging, JUL, Log4j, Log4j2, Flogger, tinylog → SLF4J

## 授權條款

`godikit-logger` 採用 [MIT 授權條款](LICENSE)。

## 相關連結

- 文件: https://www.godikit.com/logger
- 問題回饋: https://github.com/lentiancn/godikit-logger-java/issues
