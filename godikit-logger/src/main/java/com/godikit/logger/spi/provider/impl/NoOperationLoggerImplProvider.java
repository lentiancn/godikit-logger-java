package com.godikit.logger.spi.provider.impl;

import com.godikit.logger.Logger;
import com.godikit.logger.enums.LoggerFacadeEnum;
import com.godikit.logger.enums.LoggerImplEnum;
import com.godikit.logger.spi.provider.facade.LoggerFacadeProvider;

public class NoOperationLoggerImplProvider implements LoggerImplProvider {

    @Override
    public String getName() {
        return LoggerImplEnum.NO_OP.getName();
    }

    @Override
    public boolean supportsFactory(String facadeName, Object factory) {
        return true;
    }

    @Override
    public boolean supportsLogger(String facadeName, Object logger) {
        return false;
    }
}
