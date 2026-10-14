package com.godikit.logger.spi.provider.impl;

import com.godikit.logger.Logger;
import com.godikit.logger.enums.LoggerProviderEnum;
import com.godikit.logger.impl.SystemLoggerImpl;
import com.godikit.logger.spi.provider.LoggerProvider;

public class SystemLoggerProviderImpl implements LoggerProvider {

    @Override
    public String getName() {
        return LoggerProviderEnum.SYSTEM.getName();
    }

    @Override
    public Logger getLogger(String name) {
        return new SystemLoggerImpl(name);
    }

    @Override
    public Logger getLogger(Class<?> clazz) {
        return new SystemLoggerImpl(clazz);
    }
}
