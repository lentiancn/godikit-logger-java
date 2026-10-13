package com.godikit.logger.spi.facade.impl;

import com.godikit.logger.Logger;
import com.godikit.logger.enums.LoggerFacadeEnum;
import com.godikit.logger.impl.NoOperationLoggerImpl;
import com.godikit.logger.spi.facade.LoggerFacade;
import com.godikit.logger.spi.provider.LoggerProvider;

public class NoOperationLoggerFacadeImpl implements LoggerFacade {

    @Override
    public String getName() {
        return LoggerFacadeEnum.NO_OP.getName();
    }

    @Override
    public void checkCompatibilityWith(LoggerProvider provider) {
        // TODO 这里按需实现，当provider与当前facade不兼容时，抛出异常（告知用户需要引用哪个jar包）
    }

    @Override
    public Logger getLogger(String name, LoggerProvider provider) {
        return new NoOperationLoggerImpl(name);
    }

    @Override
    public Logger getLogger(Class<?> clazz, LoggerProvider provider) {
        return new NoOperationLoggerImpl(clazz);
    }
}
