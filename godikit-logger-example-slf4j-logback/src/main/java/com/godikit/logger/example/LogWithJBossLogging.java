package com.godikit.logger.example;

import org.jboss.logging.Logger;

/**
 * Demo that uses the JBoss Logging API to verify that it is redirected
 * to SLF4J + Logback.
 *
 * <p>Unlike the other bridges (jcl-over-slf4j, log4j-over-slf4j, jul-to-slf4j),
 * JBoss Logging does not have a dedicated SLF4J bridge JAR. Instead, JBoss Logging
 * detects the available logging framework on the classpath at runtime through its
 * service provider mechanism and auto-delegates to it.</p>
 *
 * <p>When SLF4J (via logback-classic) is present on the classpath, JBoss Logging
 * automatically delegates all logging calls to SLF4J, effectively achieving the
 * same goal as a bridge — unifying all logging output through Logback.</p>
 *
 * <p>Intended to confirm that any library that internally uses JBoss Logging
 * (e.g. Hibernate, Infinispan) can be transparently managed by godikit-logger
 * without changing the library's source code.</p>
 */
public class LogWithJBossLogging {

    private static final Logger LOG = Logger.getLogger(LogWithJBossLogging.class);

    /**
     * Entry point: logs one message per level using the JBoss Logging API.
     *
     * @param args command-line arguments (ignored)
     */
    public static void main(String[] args) {
        System.out.println("=== JBoss Logging API redirected to SLF4J + Logback ===");
        System.out.println("Actual JBoss Logging implementation class: " + LOG.getClass().getName());

        LOG.trace("Hello world! from JBoss Logging TRACE");
        LOG.debug("Hello world! from JBoss Logging DEBUG");
        LOG.info("Hello world! from JBoss Logging INFO");
        LOG.warn("Hello world! from JBoss Logging WARN");
        LOG.error("Hello world! from JBoss Logging ERROR");
    }
}
