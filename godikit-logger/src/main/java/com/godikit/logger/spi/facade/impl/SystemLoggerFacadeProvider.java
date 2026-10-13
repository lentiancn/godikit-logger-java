package com.godikit.logger.spi.facade.impl;

import com.godikit.logger.Logger;
import com.godikit.logger.enums.LoggerFacadeEnum;
import com.godikit.logger.impl.SystemLoggerImpl;
import com.godikit.logger.spi.facade.LoggerFacade;
import com.godikit.logger.spi.provider.LoggerProvider;

public class SystemLoggerFacadeProvider implements LoggerFacade {

    @Override
    public String getName() {
        return LoggerFacadeEnum.SYSTEM.getName();
    }

    @Override
    public Object getFacadeFactory() {
        return null;
    }

    @Override
    public Logger getLogger(String name, LoggerProvider provider) {
        return new SystemLoggerImpl(name);
    }

    @Override
    public Logger getLogger(Class<?> clazz, LoggerProvider provider) {
        return new SystemLoggerImpl(clazz);
    }

    @Override
    public Logger wrapLogger(Object delegate, LoggerProvider provider) {
        return null;
    }
}
