package com.godikit.logger.impl;

import org.apache.log4j.Category;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;

public class Log4jOverSlf4jLoggerTest {

    private static final Logger log4jLogger = Logger.getLogger(Log4jOverSlf4jLoggerTest.class);

    @Test
    public void trace() {
        log4jLogger.trace("Log4j-over-SLF4J trace message");
    }

    @Test
    public void debug() {
        log4jLogger.debug("Log4j-over-SLF4J debug message");
    }

    @Test
    public void info() {
        log4jLogger.info("Log4j-over-SLF4J info message");
    }

    @Test
    public void warn() {
        log4jLogger.warn("Log4j-over-SLF4J warn message");
    }

    @Test
    public void error() {
        log4jLogger.error("Log4j-over-SLF4J error message");
    }

    @Test
    public void fatal() {
        log4jLogger.fatal("Log4j-over-SLF4J fatal message");
    }

    @Test
    public void logWithLevel() {
        log4jLogger.log(Level.DEBUG, "Log4j-over-SLF4J log with Level.DEBUG");
        log4jLogger.log(Level.INFO, "Log4j-over-SLF4J log with Level.INFO");
        log4jLogger.log(Level.WARN, "Log4j-over-SLF4J log with Level.WARN");
    }

    @Test
    public void logWithThrowable() {
        Throwable cause = new RuntimeException("Test exception");
        log4jLogger.error("Log4j-over-SLF4J error with throwable", cause);
    }

    @Test
    public void logWithMessageAndThrowable() {
        Throwable cause = new RuntimeException("Test exception for message");
        log4jLogger.log(Level.ERROR, "Log4j-over-SLF4J log with message and throwable", cause);
    }

    @Test
    public void forcedLog() {
        log4jLogger.log(Level.INFO, "Log4j-over-SLF4J forced log message");
    }

    @Test
    public void isDebugEnabled() {
        if (log4jLogger.isDebugEnabled()) {
            log4jLogger.debug("Debug is enabled");
        }
    }

    @Test
    public void isInfoEnabled() {
        if (log4jLogger.isInfoEnabled()) {
            log4jLogger.info("Info is enabled");
        }
    }

    @Test
    public void isEnabledFor() {
        if (log4jLogger.isEnabledFor(Level.DEBUG)) {
            log4jLogger.debug("DEBUG is enabled");
        }
        if (log4jLogger.isEnabledFor(Level.INFO)) {
            log4jLogger.info("INFO is enabled");
        }
    }

    @Test
    public void setLevelAndGetParent() {
        log4jLogger.setLevel(Level.DEBUG);
        Category parent = log4jLogger.getParent();
        Level level = log4jLogger.getLevel();
        log4jLogger.debug("Log4j-over-SLF4J setLevel test");
    }

    @Test
    public void getLoggerWithDifferentNames() {
        Logger loggerA = Logger.getLogger("com.godikit.logger.ClassA");
        Logger loggerB = Logger.getLogger("com.godikit.logger.ClassB");
        loggerA.info("Log4j-over-SLF4J logger for ClassA");
        loggerB.info("Log4j-over-SLF4J logger for ClassB");
    }

    @Test
    public void categoryApi() {
        Category category = Category.getInstance("com.godikit.logger.CategoryTest");
        category.info("Log4j-over-SLF4J Category API message");
        Category rootCategory = Category.getRoot();
        rootCategory.info("Log4j-over-SLF4J Root Category message");
    }
}