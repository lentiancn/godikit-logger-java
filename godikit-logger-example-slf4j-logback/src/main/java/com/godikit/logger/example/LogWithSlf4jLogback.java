package com.godikit.logger.example;

import com.godikit.logger.Logger;
import com.godikit.logger.LoggerFactory;

/**
 * Minimal demo that obtains a {@link Logger} and logs a message at every level.
 *
 * <p>Intended to verify that a provider is discovered and basic logging works
 * without any trace ID context.</p>
 */
public class LogWithSlf4jLogback {

    private static final Logger LOG = LoggerFactory.getLogger(LogWithSlf4jLogback.class);

    /**
     * Entry point: creates a logger and emits one message per log level.
     *
     * @param args command-line arguments (ignored)
     */
    public static void main(String[] args) {
        System.out.println("=== SLF4J + Logback ===");
        System.out.println("Actual SLF4J implementation class: " + LOG.getClass().getName());
        System.out.println("Provider class: " + LOG.getProvider().getClass().getName());
        System.out.println("Original Logging class: " + LOG.getOriginalLogger().getClass().getName());

        LOG.trace("Hello world!");
        LOG.debug("Hello world!");
        LOG.info("Hello world!");
        LOG.warn("Hello world!");
        LOG.error("Hello world!");
    }
}
