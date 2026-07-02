package com.godikit.logger.bridge.jcl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;

public class JclOverSlf4jLoggerTest {

    private static final Log log = LogFactory.getLog(JclOverSlf4jLoggerTest.class);

    @Test
    public void trace() {
        log.trace("JCL-over-SLF4J trace message");
        System.out.println(log.getClass());
    }

    @Test
    public void debug() {
        log.debug("JCL-over-SLF4J debug message");
    }

    @Test
    public void info() {
        log.info("JCL-over-SLF4J info message");
    }

    @Test
    public void warn() {
        log.warn("JCL-over-SLF4J warn message");
    }

    @Test
    public void error() {
        log.error("JCL-over-SLF4J error message");
    }

    @Test
    public void fatal() {
        log.fatal("JCL-over-SLF4J fatal message");
    }

    @Test
    public void errorWithThrowable() {
        Throwable cause = new RuntimeException("Test exception");
        log.error("JCL-over-SLF4J error with throwable", cause);
    }

    @Test
    public void fatalWithThrowable() {
        Throwable cause = new RuntimeException("Test exception");
        log.fatal("JCL-over-SLF4J fatal with throwable", cause);
    }

    @Test
    public void isTraceEnabled() {
        if (log.isTraceEnabled()) {
            log.trace("Trace is enabled");
        }
    }

    @Test
    public void isDebugEnabled() {
        if (log.isDebugEnabled()) {
            log.debug("Debug is enabled");
        }
    }

    @Test
    public void isInfoEnabled() {
        if (log.isInfoEnabled()) {
            log.info("Info is enabled");
        }
    }

    @Test
    public void isWarnEnabled() {
        if (log.isWarnEnabled()) {
            log.warn("Warn is enabled");
        }
    }

    @Test
    public void isErrorEnabled() {
        if (log.isErrorEnabled()) {
            log.error("Error is enabled");
        }
    }

    @Test
    public void isFatalEnabled() {
        if (log.isFatalEnabled()) {
            log.fatal("Fatal is enabled");
        }
    }

    @Test
    public void logWithDifferentClasses() {
        Log classALog = LogFactory.getLog("com.godikit.logger.ClassA");
        Log classBLog = LogFactory.getLog(JclOverSlf4jLoggerTest.class);
        classALog.info("JCL-over-SLF4J logger for ClassA");
        classBLog.info("JCL-over-SLF4J logger for JclOverSlf4jLoggerTest");
    }
}