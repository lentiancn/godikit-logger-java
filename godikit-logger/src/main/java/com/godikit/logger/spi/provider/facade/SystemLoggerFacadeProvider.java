package com.godikit.logger.spi.provider.facade;

import com.godikit.logger.Logger;
import com.godikit.logger.enums.LoggerFacadeEnum;
import com.godikit.logger.impl.NoOperationLoggerImpl;
import com.godikit.logger.impl.SystemLoggerImpl;
import com.godikit.logger.spi.provider.impl.LoggerImplProvider;

public class SystemLoggerFacadeProvider implements LoggerFacadeProvider {

    @Override
    public String getName() {
        return LoggerFacadeEnum.SYSTEM.getName();
    }

    @Override
    public Object getFacadeFactory() {
        return null;
    }

    @Override
    public Logger getLogger(String name, LoggerImplProvider implProvider) {
        return new SystemLoggerImpl(name);
    }

    @Override
    public Logger getLogger(Class<?> clazz, LoggerImplProvider implProvider) {
        return new SystemLoggerImpl(clazz);
    }

    @Override
    public Logger wrapLogger(Object delegate, LoggerImplProvider implProvider) {
        return null;
    }
}
