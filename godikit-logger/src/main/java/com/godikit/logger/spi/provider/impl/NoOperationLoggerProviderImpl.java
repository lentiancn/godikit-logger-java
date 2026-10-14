package com.godikit.logger.spi.provider.impl;

import com.godikit.logger.enums.LoggerProviderEnum;
import com.godikit.logger.spi.provider.LoggerProvider;

public class NoOperationLoggerProviderImpl implements LoggerProvider {

    @Override
    public String getName() {
        return LoggerProviderEnum.NO_OP.getName();
    }
}
