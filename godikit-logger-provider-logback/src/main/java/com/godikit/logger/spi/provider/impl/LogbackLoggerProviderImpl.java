package com.godikit.logger.spi.provider.impl;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.godikit.logger.enums.LoggerFacadeEnum;
import com.godikit.logger.enums.LoggerProviderEnum;
import com.godikit.logger.spi.facade.LoggerFacade;
import com.godikit.logger.spi.provider.LoggerProvider;

/**
 * Identifies and validates the Logback implementation behind SLF4J.
 */
public final class LogbackLoggerProviderImpl implements LoggerProvider {

    @Override
    public String getName() {
        return LoggerProviderEnum.LOGBACK.getName();
    }

    @Override
    public boolean supportsFactory(LoggerFacade facade) {
        return LoggerFacadeEnum.SLF4J.getName().equals(facade.getName()) && facade.getFacadeFactory() instanceof LoggerContext;
    }

    @Override
    public boolean supportsLogger(String facadeName, Object logger) {
        return LoggerFacadeEnum.SLF4J.getName().equals(facadeName) && logger instanceof Logger;
    }
}
