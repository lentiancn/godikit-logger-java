package com.godikit.logger.spi.provider.impl;

import com.godikit.logger.Logger;
import com.godikit.logger.enums.LoggerProviderEnum;
import com.godikit.logger.impl.NoOperationLoggerImpl;
import com.godikit.logger.spi.provider.LoggerProvider;

public class NoOperationLoggerProviderImpl implements LoggerProvider {

    @Override
    public String getName() {
        return LoggerProviderEnum.NO_OP.getName();
    }

    @Override
    public Logger getLogger(String name) {
        return new NoOperationLoggerImpl(name);
    }

    @Override
    public Logger getLogger(Class<?> clazz) {
        return new NoOperationLoggerImpl(clazz);
    }
}
