package com.godikit.logger.example;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Demo that uses the Apache Commons Logging API to verify that it is redirected
 * to SLF4J + Logback.
 *
 * <p>Intended to confirm that any library that internally uses Commons Logging
 * (e.g. Spring Framework, Apache HttpClient) can be transparently managed by
 * godikit-logger without changing the library's source code.</p>
 */
public class LogWithCommonsLogging {

    private static final Log LOG = LogFactory.getLog(LogWithCommonsLogging.class);

    /**
     * Entry point: logs one message per level using the Commons Logging API.
     *
     * @param args command-line arguments (ignored)
     */
    public static void main(String[] args) {
        System.out.println("=== Commons Logging API redirected to SLF4J + Logback ===");
        System.out.println("Actual Commons Logging implementation class: " + LOG.getClass().getName());

        LOG.trace("Hello world! from Commons Logging TRACE");
        LOG.debug("Hello world! from Commons Logging DEBUG");
        LOG.info("Hello world! from Commons Logging INFO");
        LOG.warn("Hello world! from Commons Logging WARN");
        LOG.error("Hello world! from Commons Logging ERROR");
        LOG.fatal("Hello world! from Commons Logging FATAL");
    }
}
