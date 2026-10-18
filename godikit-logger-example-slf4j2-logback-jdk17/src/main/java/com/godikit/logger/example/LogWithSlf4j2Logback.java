package com.godikit.logger.example;

import com.godikit.logger.Logger;
import com.godikit.logger.LoggerFactory;

/**
 * Minimal demo that obtains a {@link Logger} and logs a message at every level.
 */
public class LogWithSlf4j2Logback {

    private static final Logger LOG = LoggerFactory.getLogger(LogWithSlf4j2Logback.class);

    public static void main(String[] args) {
        System.out.println("=== SLF4J 2.x + Logback (JDK17) ===");
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
