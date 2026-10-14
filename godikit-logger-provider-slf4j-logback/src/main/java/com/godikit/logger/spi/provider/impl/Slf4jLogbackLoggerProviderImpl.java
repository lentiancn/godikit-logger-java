package com.godikit.logger.spi.provider.impl;

import com.godikit.logger.Logger;
import com.godikit.logger.enums.LoggerFacadeEnum;
import com.godikit.logger.impl.Slf4jLogbackLoggerImpl;
import com.godikit.logger.spi.facade.LoggerFacade;
import com.godikit.logger.spi.provider.LoggerProvider;
import org.slf4j.LoggerFactory;

public final class Slf4jLogbackLoggerProviderImpl implements LoggerFacade, LoggerProvider {

    @Override
    public String getName() {
        return LoggerFacadeEnum.SLF4J.getName();
    }

    @Override
    public void checkCompatibilityWith(LoggerProvider provider) {
    }

    @Override
    public void checkCompatibilityWith(LoggerFacade facade) {
    }

    @Override
    public Logger getLogger(String name, LoggerProvider provider) {
        return new Slf4jLogbackLoggerImpl(LoggerFactory.getLogger(name), this, provider);
    }

    @Override
    public Logger getLogger(Class<?> clazz, LoggerProvider provider) {
        return new Slf4jLogbackLoggerImpl(LoggerFactory.getLogger(clazz), this, provider);
    }
}
