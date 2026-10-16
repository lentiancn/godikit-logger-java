package com.godikit.logger.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Demo that uses the Log4j 2.x API to verify that it is redirected
 * to SLF4J + Logback via the log4j-to-slf4j bridge.
 *
 * <p>Intended to confirm that any library that internally uses Log4j 2.x
 * can be transparently managed by godikit-logger without changing the library's source code.</p>
 */
public class LogWithLog4j2 {

    private static final Logger LOG = LogManager.getLogger(LogWithLog4j2.class);

    /**
     * Entry point: logs one message per level using the Log4j 2.x API.
     *
     * @param args command-line arguments (ignored)
     */
    public static void main(String[] args) {
        System.out.println("=== Log4j 2.x API redirected via log4j-to-slf4j ===");
        System.out.println("Actual Log4j 2 Logger class: " + LOG.getClass().getName());

        LOG.trace("Hello from Log4j 2 TRACE");
        LOG.debug("Hello from Log4j 2 DEBUG");
        LOG.info("Hello from Log4j 2 INFO");
        LOG.warn("Hello from Log4j 2 WARN");
        LOG.error("Hello from Log4j 2 ERROR");
        LOG.fatal("Hello from Log4j 2 FATAL");
    }
}
