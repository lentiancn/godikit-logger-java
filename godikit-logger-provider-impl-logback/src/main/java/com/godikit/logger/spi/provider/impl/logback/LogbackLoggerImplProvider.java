package com.godikit.logger.spi.provider.impl.logback;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.godikit.logger.spi.provider.LoggerImplProvider;

/**
 * Identifies and validates the Logback implementation behind SLF4J.
 */
public final class LogbackLoggerImplProvider implements LoggerImplProvider {

    public LogbackLoggerImplProvider() {
    }

    @Override
    public String getName() {
        return "logback";
    }

    @Override
    public boolean supportsFactory(String facadeName, Object factory) {
        return "slf4j".equals(facadeName) && factory instanceof LoggerContext;
    }

    @Override
    public boolean supportsLogger(String facadeName, Object logger) {
        return "slf4j".equals(facadeName) && logger instanceof Logger;
    }
}
