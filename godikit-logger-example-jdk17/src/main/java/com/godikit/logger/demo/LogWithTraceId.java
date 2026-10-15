package com.godikit.logger.demo;

import com.godikit.logger.Logger;
import com.godikit.logger.LoggerFactory;
import org.slf4j.MDC;

import java.util.UUID;

/**
 * Demo that populates the SLF4J MDC with a trace ID before logging.
 *
 * <p>Verifies that the {@code gtTraceId} logback converter picks up the
 * {@code traceId} MDC entry and prepends it to each log message.</p>
 */
public class LogWithTraceId {

    /**
     * Entry point: sets a random trace ID in MDC, then logs one message per level.
     *
     * @param args command-line arguments (ignored)
     */
    public static void main(String[] args) {
        MDC.put("traceId", UUID.randomUUID().toString());

        Logger logger = LoggerFactory.getLogger(LogWithTraceId.class);

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
