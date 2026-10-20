package com.godikit.logger.example;

import com.godikit.logger.utils.Slf4jUtils;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Demo that uses the java.util.logging (JUL) API to verify that it is redirected
 * to SLF4J 2.x + Logback.
 */
public class LogWithJUL {

    private static final Logger LOG = Logger.getLogger(LogWithJUL.class.getName());

    public static void main(String[] args) {
        Slf4jUtils.redirectJulToSlf4j();

        System.out.println("=== java.util.logging (JUL) API redirected to SLF4J 2.x + Logback ===");
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
