package com.godikit.logger.example;

import com.godikit.logger.Logger;
import com.godikit.logger.LoggerFactory;

/**
 * Minimal demo that obtains a {@link Logger} and logs a message at every level.
 *
 * <p>Intended to verify that a provider is discovered and basic logging works
 * without any trace ID context.</p>
 */
public class LogBasic {

    /**
     * Entry point: creates a logger and emits one message per log level.
     *
     * @param args command-line arguments (ignored)
     */
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(LogBasic.class);

        System.out.println(logger.getName());
        System.out.println(logger.getProvider());
        System.out.println(logger.getOriginalLogger() + " " + logger.getOriginalLogger().getClass());

        logger.trace("Hello world!");
        logger.debug("Hello world!");
        logger.info("Hello world!");
        logger.warn("Hello world!");
        logger.error("Hello world!");
    }
}
