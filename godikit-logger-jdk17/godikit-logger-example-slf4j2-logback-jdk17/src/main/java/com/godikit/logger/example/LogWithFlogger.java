package com.godikit.logger.example;

import com.google.common.flogger.FluentLogger;

/**
 * Demo that uses Google Flogger (Fluent Logging API) to verify that it can
 * be redirected to SLF4J 2.x + Logback.
 */
public class LogWithFlogger {

    private static final FluentLogger LOG = FluentLogger.forEnclosingClass();

    public static void main(String[] args) {
        System.out.println("=== Flogger API redirected to SLF4J 2.x + Logback ===");
        System.out.println("Flogger backend class: " + LOG.getClass().getName());

        LOG.atFine().log("Hello world! from Flogger FINEST");
        LOG.atFiner().log("Hello world! from Flogger FINER");
        LOG.atFine().log("Hello world! from Flogger FINE");
        LOG.atInfo().log("Hello world! from Flogger INFO");
        LOG.atWarning().log("Hello world! from Flogger WARNING");
        LOG.atSevere().log("Hello world! from Flogger SEVERE");
    }
}
