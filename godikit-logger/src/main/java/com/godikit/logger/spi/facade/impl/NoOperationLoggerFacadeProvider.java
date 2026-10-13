package com.godikit.logger.spi.facade.impl;

import com.godikit.logger.Logger;
import com.godikit.logger.enums.LoggerFacadeEnum;
import com.godikit.logger.impl.NoOperationLoggerImpl;
import com.godikit.logger.spi.facade.LoggerFacade;
import com.godikit.logger.spi.provider.LoggerProvider;

public class NoOperationLoggerFacadeProvider implements LoggerFacade {

    @Override
    public String getName() {
        return LoggerFacadeEnum.NO_OP.getName();
    }

    @Override
    public Object getFacadeFactory() {
        return null;
    }

    @Override
    public Logger getLogger(String name, LoggerProvider provider) {
        return new NoOperationLoggerImpl(name);
    }

    @Override
    public Logger getLogger(Class<?> clazz, LoggerProvider provider) {
        return new NoOperationLoggerImpl(clazz);
    }

    @Override
    public Logger wrapLogger(Object delegate, LoggerProvider implementation) {
        return null;
    }
}
