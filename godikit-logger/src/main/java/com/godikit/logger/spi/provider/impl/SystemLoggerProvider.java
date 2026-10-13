package com.godikit.logger.spi.provider.impl;

import com.godikit.logger.enums.LoggerProviderEnum;
import com.godikit.logger.spi.facade.LoggerFacade;
import com.godikit.logger.spi.provider.LoggerProvider;

public class SystemLoggerProvider implements LoggerProvider {

    @Override
    public String getName() {
        return LoggerProviderEnum.SYSTEM.getName();
    }

    @Override
    public boolean supportsFactory(LoggerFacade facade) {
        return true;
    }

    @Override
    public boolean supportsLogger(String facadeName, Object logger) {
        return true;
    }
}
