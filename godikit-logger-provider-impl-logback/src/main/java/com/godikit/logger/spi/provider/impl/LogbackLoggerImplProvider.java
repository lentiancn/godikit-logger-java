package com.godikit.logger.spi.provider.impl;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Identifies and validates the Logback implementation behind SLF4J.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LogbackLoggerImplProvider implements LoggerImplProvider {

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
