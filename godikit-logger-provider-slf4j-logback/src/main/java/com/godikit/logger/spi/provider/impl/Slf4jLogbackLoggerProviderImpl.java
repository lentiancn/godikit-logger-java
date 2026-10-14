package com.godikit.logger.spi.provider.impl;

import com.godikit.logger.Logger;
import com.godikit.logger.enums.LoggerFacadeEnum;
import com.godikit.logger.impl.Slf4jLogbackLoggerImpl;
import com.godikit.logger.spi.provider.LoggerProvider;
import org.slf4j.LoggerFactory;

public final class Slf4jLogbackLoggerProviderImpl implements LoggerProvider {

    @Override
    public String getName() {
        return LoggerFacadeEnum.SLF4J.getName();
    }

    @Override
    public Logger getLogger(String name) {
        return new Slf4jLogbackLoggerImpl(LoggerFactory.getLogger(name), this);
    }

    @Override
    public Logger getLogger(Class<?> clazz) {
        return new Slf4jLogbackLoggerImpl(LoggerFactory.getLogger(clazz), this);
    }
}
