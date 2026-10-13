package com.godikit.logger.spi.facade.impl;

import com.godikit.logger.Logger;
import com.godikit.logger.impl.Slf4jLoggerImpl;
import com.godikit.logger.spi.facade.LoggerFacade;
import com.godikit.logger.spi.provider.LoggerProvider;
import org.slf4j.LoggerFactory;

/**
 * GodiKit SPI provider for the SLF4J facade.
 */
public final class Slf4jLoggerFacadeImpl implements LoggerFacade {

    @Override
    public String getName() {
        return "slf4j";
    }

    @Override
    public void checkCompatibilityWith(LoggerProvider provider) {
        // TODO 这里按需实现，当provider与当前facade不兼容时，抛出异常（告知用户需要引用哪个jar包）
    }

    @Override
    public Logger getLogger(String name, LoggerProvider provider) {
        return new Slf4jLoggerImpl(LoggerFactory.getLogger(name), this, provider);
    }

    @Override
    public Logger getLogger(Class<?> clazz, LoggerProvider provider) {
        return new Slf4jLoggerImpl(LoggerFactory.getLogger(clazz), this, provider);
    }
}
