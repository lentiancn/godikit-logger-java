package com.godikit.logger.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Demo that uses the Log4j 2.x API to verify that it is redirected
 * to SLF4J 2.x + Logback.
 */
public class LogWithLog4j2 {

    private static final Logger LOG = LogManager.getLogger(LogWithLog4j2.class);

    public static void main(String[] args) {
        System.out.println("=== Log4j 2.x API redirected to SLF4J 2.x + Logback ===");
        System.out.println("Actual Log4j 2 Logger implementation class: " + LOG.getClass().getName());

        LOG.trace("Hello world! from Log4j 2 TRACE");
        LOG.debug("Hello world! from Log4j 2 DEBUG");
        LOG.info("Hello world! from Log4j 2 INFO");
        LOG.warn("Hello world! from Log4j 2 WARN");
        LOG.error("Hello world! from Log4j 2 ERROR");
        LOG.fatal("Hello world! from Log4j 2 FATAL");
    }
}
