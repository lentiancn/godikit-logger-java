package com.godikit.logger.example;

import org.jboss.logging.Logger;

/**
 * Demo that uses the JBoss Logging API to verify that it is redirected
 * to SLF4J 2.x + Logback.
 */
public class LogWithJBossLogging {

    private static final Logger LOG = Logger.getLogger(LogWithJBossLogging.class);

    public static void main(String[] args) {
        System.out.println("=== JBoss Logging API redirected to SLF4J 2.x + Logback ===");
        System.out.println("Actual JBoss Logging implementation class: " + LOG.getClass().getName());

        LOG.trace("Hello world! from JBoss Logging TRACE");
        LOG.debug("Hello world! from JBoss Logging DEBUG");
        LOG.info("Hello world! from JBoss Logging INFO");
        LOG.warn("Hello world! from JBoss Logging WARN");
        LOG.error("Hello world! from JBoss Logging ERROR");
    }
}
