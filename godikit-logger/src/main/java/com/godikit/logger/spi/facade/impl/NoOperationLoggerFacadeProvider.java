package com.godikit.logger.spi.facade.impl;

import com.godikit.logger.Logger;
import com.godikit.logger.enums.LoggerFacadeEnum;
import com.godikit.logger.impl.NoOperationLoggerImpl;
import com.godikit.logger.spi.facade.LoggerFacadeProvider;
import com.godikit.logger.spi.provider.LoggerImplProvider;

public class NoOperationLoggerFacadeProvider implements LoggerFacadeProvider {

    @Override
    public String getName() {
        return LoggerFacadeEnum.NO_OP.getName();
    }

    @Override
    public Object getFacadeFactory() {
        return null;
    }

    @Override
    public Logger getLogger(String name, LoggerImplProvider implementation) {
        return new NoOperationLoggerImpl(name);
    }

    @Override
    public Logger getLogger(Class<?> clazz, LoggerImplProvider implProvider) {
        return new NoOperationLoggerImpl(clazz);
    }

    @Override
    public Logger wrapLogger(Object delegate, LoggerImplProvider implementation) {
        return null;
    }
}
