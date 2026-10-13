package com.godikit.logger.spi.provider.impl;

import com.godikit.logger.enums.LoggerImplEnum;
import com.godikit.logger.spi.provider.facade.LoggerFacadeProvider;

public class SystemLoggerImplProvider implements LoggerImplProvider {

    @Override
    public String getName() {
        return LoggerImplEnum.SYSTEM.getName();
    }

    @Override
    public boolean supportsFactory(LoggerFacadeProvider facadeProvider) {
        return true;
    }

    @Override
    public boolean supportsLogger(String facadeName, Object logger) {
        return true;
    }
}
