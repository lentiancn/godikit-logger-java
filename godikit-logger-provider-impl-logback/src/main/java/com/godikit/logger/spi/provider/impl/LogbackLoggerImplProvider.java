package com.godikit.logger.spi.provider.impl;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.godikit.logger.enums.LoggerFacadeEnum;
import com.godikit.logger.enums.LoggerImplEnum;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Identifies and validates the Logback implementation behind SLF4J.
 */
public final class LogbackLoggerImplProvider implements LoggerImplProvider {

    @Override
    public String getName() {
        return LoggerImplEnum.LOGBACK.getName();
    }

    @Override
    public boolean supportsFactory(String facadeName, Object factory) {
        return LoggerFacadeEnum.SLF4J.getName().equals(facadeName) && factory instanceof LoggerContext;
    }

    @Override
    public boolean supportsLogger(String facadeName, Object logger) {
        return LoggerFacadeEnum.SLF4J.getName().equals(facadeName) && logger instanceof Logger;
    }
}
