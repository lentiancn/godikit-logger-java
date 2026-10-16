package com.godikit.logger.example;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Demo that uses the Apache Commons Logging API to verify that it is redirected
 * to SLF4J + Logback via the jcl-over-slf4j bridge.
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
        System.out.println("=== Commons Logging API redirected via jcl-over-slf4j ===");
        System.out.println("Actual Commons Logging implementation class: " + LogFactory.getFactory().getClass());

        LOG.trace("Hello from Commons Logging TRACE");
        LOG.debug("Hello from Commons Logging DEBUG");
        LOG.info("Hello from Commons Logging INFO");
        LOG.warn("Hello from Commons Logging WARN");
        LOG.error("Hello from Commons Logging ERROR");
        LOG.fatal("Hello from Commons Logging FATAL");
    }
}
