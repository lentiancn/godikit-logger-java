//package com.godikit.logger.bridge.osgi;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.osgi.service.log.Logger;
//import org.osgi.service.log.LoggerFactory;
//
//import java.util.Iterator;
//import java.util.ServiceLoader;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//public class OsgiOverSlf4jLoggerTest {
//
//    private Logger osgiLogger;
//    private LoggerFactory loggerFactory;
//
//    @BeforeEach
//    public void setUp() {
//        ServiceLoader<LoggerFactory> loader = ServiceLoader.load(LoggerFactory.class);
//        Iterator<LoggerFactory> iterator = loader.iterator();
//        assertTrue(iterator.hasNext(), "LoggerFactory implementation not found. Ensure 'osgi-over-slf4j' is on classpath.");
//        loggerFactory = iterator.next();
//        osgiLogger = loggerFactory.getLogger(OsgiOverSlf4jLoggerTest.class);
//    }
//
//    @Test
//    public void trace() {
//        osgiLogger.trace("OSGi-over-SLF4J trace message");
//    }
//
//    @Test
//    public void debug() {
//        osgiLogger.debug("OSGi-over-SLF4J debug message");
//    }
//
//    @Test
//    public void info() {
//        osgiLogger.info("OSGi-over-SLF4J info message");
//    }
//
//    @Test
//    public void warn() {
//        osgiLogger.warn("OSGi-over-SLF4J warn message");
//    }
//
//    @Test
//    public void error() {
//        osgiLogger.error("OSGi-over-SLF4J error message");
//    }
//
//    @Test
//    public void audit() {
//        osgiLogger.audit("OSGi-over-SLF4J audit message");
//    }
//
//    @Test
//    public void logWithThrowable() {
//        Throwable cause = new RuntimeException("Test exception");
//        osgiLogger.error("OSGi-over-SLF4J log with throwable", cause);
//    }
//
//    @Test
//    public void logWithFormat() {
//        osgiLogger.trace("OSGi-over-SLF4J trace with format: {}", "value");
//        osgiLogger.debug("OSGi-over-SLF4J debug with format: {}", "value");
//        osgiLogger.info("OSGi-over-SLF4J info with format: {}", "value");
//        osgiLogger.warn("OSGi-over-SLF4J warn with format: {}", "value");
//        osgiLogger.error("OSGi-over-SLF4J error with format: {}", "value");
//        osgiLogger.audit("OSGi-over-SLF4J audit with format: {}", "value");
//    }
//
//    @Test
//    public void logWithMultipleArgs() {
//        osgiLogger.info("OSGi-over-SLF4J info with multiple args: {}, {}, {}", "a", "b", "c");
//        osgiLogger.warn("OSGi-over-SLF4J warn with multiple args: {}, {}, {}", 1, 2, 3);
//    }
//
//    @Test
//    public void loggerWithName() {
//        Logger namedLogger = loggerFactory.getLogger("com.godikit.logger.impl");
//        assertNotNull(namedLogger);
//        namedLogger.info("OSGi-over-SLF4J named logger message");
//    }
//
//    @Test
//    public void loggerWithContext() {
//        Logger contextLogger = loggerFactory.getLogger(OsgiOverSlf4jLoggerTest.class);
//        assertNotNull(contextLogger);
//        contextLogger.info("OSGi-over-SLF4J logger with context message");
//    }
//}