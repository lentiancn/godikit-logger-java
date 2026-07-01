package com.godikit.logger.impl;

import com.godikit.logger.utils.LoggerSlf4jUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

public class JulToSlf4jLoggerTest {

    private static final Logger julLogger = Logger.getLogger(JulToSlf4jLoggerTest.class.getName());

    @BeforeAll
    public static void setUp() {
        LoggerSlf4jUtils.forceJulToSlf4j();
    }

    @Test
    public void finest() {
        julLogger.finest("JUL-to-SLF4J finest message");
    }

    @Test
    public void finer() {
        julLogger.finer("JUL-to-SLF4J finer message");
    }

    @Test
    public void fine() {
        julLogger.fine("JUL-to-SLF4J fine message");
    }

    @Test
    public void config() {
        julLogger.config("JUL-to-SLF4J config message");
    }

    @Test
    public void info() {
        julLogger.info("JUL-to-SLF4J info message");
    }

    @Test
    public void warning() {
        julLogger.warning("JUL-to-SLF4J warning message");
    }

    @Test
    public void severe() {
        julLogger.severe("JUL-to-SLF4J severe message");
    }

    @Test
    public void logWithLevel() {
        julLogger.log(Level.FINE, "JUL-to-SLF4J log with Level.FINE");
        julLogger.log(Level.INFO, "JUL-to-SLF4J log with Level.INFO");
        julLogger.log(Level.WARNING, "JUL-to-SLF4J log with Level.WARNING");
    }

    @Test
    public void logWithThrowable() {
        Throwable cause = new RuntimeException("Test exception");
        julLogger.log(Level.SEVERE, "JUL-to-SLF4J log with throwable", cause);
    }

    @Test
    public void entering() {
        julLogger.entering("JulToSlf4jLoggerTest", "testMethod");
    }

    @Test
    public void exiting() {
        julLogger.exiting("JulToSlf4jLoggerTest", "testMethod");
    }

    @Test
    public void throwing() {
        try {
            throw new RuntimeException("Test exception for throwing");
        } catch (RuntimeException e) {
            julLogger.throwing("JulToSlf4jLoggerTest", "throwing", e);
        }
    }

    @Test
    public void isLoggable() {
        Level level = Level.FINE;
        if (julLogger.isLoggable(level)) {
            julLogger.log(level, "JUL-to-SLF4J isLoggable check passed");
        }
    }
}