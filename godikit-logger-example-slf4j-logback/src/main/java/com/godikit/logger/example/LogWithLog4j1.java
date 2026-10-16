package com.godikit.logger.example;

import org.apache.log4j.Logger;

/**
 * Demo that uses the Log4j 1.x API to verify that it is redirected
 * to SLF4J + Logback.
 *
 * <p>Intended to confirm that any legacy library that internally uses Log4j 1.x
 * can be transparently managed by godikit-logger without changing the library's source code.</p>
 */
public class LogWithLog4j1 {

    private static final Logger LOG = Logger.getLogger(LogWithLog4j1.class);

    /**
     * Entry point: logs one message per level using the Log4j 1.x API.
     *
     * @param args command-line arguments (ignored)
     */
    public static void main(String[] args) {
        System.out.println("=== Log4j 1.x API redirected to SLF4J + Logback ===");
        System.out.println("Actual Log4j Logger implementation class: " + LOG.getClass().getName());

        LOG.trace("Hello world! from Log4j 1 TRACE");
        LOG.debug("Hello world! from Log4j 1 DEBUG");
        LOG.info("Hello world! from Log4j 1 INFO");
        LOG.warn("Hello world! from Log4j 1 WARN");
        LOG.error("Hello world! from Log4j 1 ERROR");
        LOG.fatal("Hello world! from Log4j 1 FATAL");
    }
}
