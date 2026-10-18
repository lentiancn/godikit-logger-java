package com.godikit.logger.example;

import org.apache.log4j.Logger;

/**
 * Demo that uses the Log4j 1.x API to verify that it is redirected
 * to SLF4J 2.x + Logback.
 */
public class LogWithLog4j {

    private static final Logger LOG = Logger.getLogger(LogWithLog4j.class);

    public static void main(String[] args) {
        System.out.println("=== Log4j 1.x API redirected to SLF4J 2.x + Logback ===");
        System.out.println("Actual Log4j Logger implementation class: " + LOG.getClass().getName());

        LOG.trace("Hello world! from Log4j 1 TRACE");
        LOG.debug("Hello world! from Log4j 1 DEBUG");
        LOG.info("Hello world! from Log4j 1 INFO");
        LOG.warn("Hello world! from Log4j 1 WARN");
        LOG.error("Hello world! from Log4j 1 ERROR");
        LOG.fatal("Hello world! from Log4j 1 FATAL");
    }
}
