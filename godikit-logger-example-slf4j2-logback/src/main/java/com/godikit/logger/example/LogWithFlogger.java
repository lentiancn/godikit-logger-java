package com.godikit.logger.example;

import com.google.common.flogger.FluentLogger;

/**
 * Demo that uses Google Flogger (Fluent Logging API) to verify that it can
 * be redirected to SLF4J 2.x + Logback.
 *
 * <p>Flogger is Google's open-source fluent logging API that provides a more
 * readable and efficient way of constructing log messages compared to
 * traditional printf-style logging.</p>
 *
 * <p>Flogger uses a fluent chain API where logging statements read like
 * natural language: {@code LOG.atInfo().log("message")}</p>
 *
 * <p>Intended to confirm that any library using Flogger can be transparently
 * managed by godikit-logger through the SLF4J backend.</p>
 *
 * <p>To run this example:</p>
 * <pre>
 * mvn compile exec:java -Dexec.mainClass="com.godikit.logger.example.LogWithFlogger" \
 *     -DskipTests -Pflogger
 * </pre>
 */
public class LogWithFlogger {

    private static final FluentLogger LOG = FluentLogger.forEnclosingClass();

    /**
     * Entry point: creates a logger and emits one message per log level
     * using Flogger's fluent API.
     *
     * @param args command-line arguments (ignored)
     */
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
