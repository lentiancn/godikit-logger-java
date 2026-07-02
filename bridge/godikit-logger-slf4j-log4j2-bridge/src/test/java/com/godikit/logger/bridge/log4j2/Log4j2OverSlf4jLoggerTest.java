package com.godikit.logger.bridge.log4j2;

import org.apache.logging.log4j.*;
import org.junit.jupiter.api.Test;

public class Log4j2OverSlf4jLoggerTest {

    private static final Logger log4j2Logger = LogManager.getLogger(Log4j2OverSlf4jLoggerTest.class);

    @Test
    public void trace() {
        log4j2Logger.trace("Log4j2-over-SLF4J trace message");
    }

    @Test
    public void debug() {
        log4j2Logger.debug("Log4j2-over-SLF4J debug message");
    }

    @Test
    public void info() {
        log4j2Logger.info("Log4j2-over-SLF4J info message");
    }

    @Test
    public void warn() {
        log4j2Logger.warn("Log4j2-over-SLF4J warn message");
    }

    @Test
    public void error() {
        log4j2Logger.error("Log4j2-over-SLF4J error message");
    }

    @Test
    public void fatal() {
        log4j2Logger.fatal("Log4j2-over-SLF4J fatal message");
    }

    @Test
    public void logWithLevel() {
        log4j2Logger.log(Level.DEBUG, "Log4j2-over-SLF4J log with Level.DEBUG");
        log4j2Logger.log(Level.INFO, "Log4j2-over-SLF4J log with Level.INFO");
        log4j2Logger.log(Level.WARN, "Log4j2-over-SLF4J log with Level.WARN");
    }

    @Test
    public void logWithThrowable() {
        Throwable cause = new RuntimeException("Test exception");
        log4j2Logger.error("Log4j2-over-SLF4J error with throwable", cause);
    }

    @Test
    public void logWithMessageAndThrowable() {
        Throwable cause = new RuntimeException("Test exception for message");
        log4j2Logger.error("Log4j2-over-SLF4J log with message and throwable", cause);
    }

    @Test
    public void logWithMarker() {
        Marker marker = MarkerManager.getMarker("CUSTOM");
        log4j2Logger.info(marker, "Log4j2-over-SLF4J log with marker");
    }

    @Test
    public void logWithMarkerAndThrowable() {
        Marker marker = MarkerManager.getMarker("ERROR_MARKER");
        Throwable cause = new RuntimeException("Test exception");
        log4j2Logger.error(marker, "Log4j2-over-SLF4J log with marker and throwable", cause);
    }

    @Test
    public void isDebugEnabled() {
        if (log4j2Logger.isDebugEnabled()) {
            log4j2Logger.debug("Debug is enabled");
        }
    }

    @Test
    public void isInfoEnabled() {
        if (log4j2Logger.isInfoEnabled()) {
            log4j2Logger.info("Info is enabled");
        }
    }

    @Test
    public void isWarnEnabled() {
        if (log4j2Logger.isWarnEnabled()) {
            log4j2Logger.warn("Warn is enabled");
        }
    }

    @Test
    public void isErrorEnabled() {
        if (log4j2Logger.isErrorEnabled()) {
            log4j2Logger.error("Error is enabled");
        }
    }

    @Test
    public void getLoggerWithDifferentClasses() {
        Logger loggerA = LogManager.getLogger("com.godikit.logger.ClassA");
        Logger loggerB = LogManager.getLogger(Log4j2OverSlf4jLoggerTest.class);
        loggerA.info("Log4j2-over-SLF4J logger for ClassA");
        loggerB.info("Log4j2-over-SLF4J logger for Log4j2OverSlf4jLoggerTest");
    }

    @Test
    public void getRootLogger() {
        Logger rootLogger = LogManager.getRootLogger();
        rootLogger.info("Log4j2-over-SLF4J root logger message");
    }

    @Test
    public void logWithFormattedMessage() {
        log4j2Logger.info("Log4j2-over-SLF4J formatted message: {} - {}", "arg1", "arg2");
        log4j2Logger.warn("Log4j2-over-SLF4J formatted warning: {} - {}", 123, 456);
    }

    @Test
    public void logWithLocation() {
        log4j2Logger.info("Log4j2-over-SLF4J log with location info");
    }
}