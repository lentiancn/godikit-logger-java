package com.godikit.logger.example;

import com.godikit.logger.utils.Slf4jUtils;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Demo that uses the java.util.logging (JUL) API to verify that it is redirected
 * to SLF4J + Logback.
 *
 * <p>Intended to confirm that any library or JDK-internal code that uses JUL
 * can be transparently managed by godikit-logger without changing the library's source code.</p>
 */
public class LogWithJUL {

    private static final Logger LOG = Logger.getLogger(LogWithJUL.class.getName());

    /**
     * Entry point: logs one message per level using the JUL API.
     *
     * @param args command-line arguments (ignored)
     */
    public static void main(String[] args) {
        // MUST be called before any JUL logger is obtained — this installs the bridge globally.
        // Once installed, all subsequent java.util.logging.Logger calls are routed to SLF4J.
        Slf4jUtils.redirectJulToSlf4j();

        System.out.println("=== java.util.logging (JUL) API redirected to SLF4J + Logback ===");
        System.out.println("Actual JUL Logger implementation class: " + LOG.getClass().getName());

        LOG.setLevel(Level.ALL);
        LOG.finest("Hello world! from JUL FINEST");
        LOG.finer("Hello world! from JUL FINER");
        LOG.fine("Hello world! from JUL FINE");
        LOG.config("Hello world! from JUL CONFIG");
        LOG.info("Hello world! from JUL INFO");
        LOG.warning("Hello world! from JUL WARNING");
        LOG.severe("Hello world! from JUL SEVERE");
    }
}
