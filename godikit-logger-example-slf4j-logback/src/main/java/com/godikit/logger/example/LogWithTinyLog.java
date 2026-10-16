package com.godikit.logger.example;

import org.tinylog.Logger;

/**
 * Demo that uses the TinyLog API to verify that it is redirected
 * to SLF4J + Logback.
 *
 * <p>TinyLog is a minimalist logging API for Java applications that provides
 * a clean and simple interface. It supports various logging backends through
 * a provider mechanism.</p>
 *
 * <p>Intended to confirm that any library that internally uses TinyLog
 * can be transparently managed by godikit-logger without changing the
 * library's source code.</p>
 *
 * <p>To run this example:</p>
 * <pre>
 * mvn compile exec:java -Dexec.mainClass="com.godikit.logger.example.LogWithTinyLog" \
 *     -DskipTests -Ptinylog
 * </pre>
 */
public class LogWithTinyLog {

    /**
     * Entry point: logs one message per level using the TinyLog API.
     *
     * @param args command-line arguments (ignored)
     */
    public static void main(String[] args) {
        System.out.println("=== TinyLog API redirected to SLF4J + Logback ===");
        System.out.println("Actual TinyLog Logger implementation class: " + Logger.class.getName());

        Logger.trace("Hello world! from TinyLog TRACE");
        Logger.debug("Hello world! from TinyLog DEBUG");
        Logger.info("Hello world! from TinyLog INFO");
        Logger.warn("Hello world! from TinyLog WARN");
        Logger.error("Hello world! from TinyLog ERROR");
    }
}
