package com.godikit.logger.example;

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
public class LogWithSlf4jLogbackUsingTraceId {

    private static final Logger LOG = LoggerFactory.getLogger(LogWithSlf4jLogbackUsingTraceId.class);

    /**
     * Entry point: sets a random trace ID in MDC, then logs one message per level.
     *
     * @param args command-line arguments (ignored)
     */
    public static void main(String[] args) {
        MDC.put("traceId", UUID.randomUUID().toString());

        System.out.println("=== SLF4J + Logback (Include traceId) ===");
        System.out.println("Actual SLF4J implementation class: " + LOG.getClass().getName());
        System.out.println("Provider class: " + LOG.getProvider().getClass().getName());
        System.out.println("Original Logging class: " + LOG.getOriginalLogger().getClass().getName());
        System.out.println("traceId: " + MDC.get("traceId"));

        LOG.trace("Hello world!");
        LOG.debug("Hello world!");
        LOG.info("Hello world!");
        LOG.warn("Hello world!");
        LOG.error("Hello world!");
    }
}
