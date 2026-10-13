package com.godikit.logger.spi.facade.impl;

import com.godikit.logger.Logger;
import com.godikit.logger.enums.LoggerFacadeEnum;
import com.godikit.logger.impl.Log4j2LoggerImpl;
import com.godikit.logger.spi.facade.LoggerFacade;
import com.godikit.logger.spi.provider.LoggerProvider;
import org.apache.logging.log4j.LogManager;

/**
 * GodiKit SPI provider for the Log4j2 facade.
 */
public final class Log4j2LoggerFacadeImpl implements LoggerFacade {

    @Override
    public String getName() {
        return LoggerFacadeEnum.LOG4J2.getName();
    }

    @Override
    public void checkCompatibilityWith(LoggerProvider provider) {
        // TODO 这里按需实现，当provider与当前facade不兼容时，抛出异常（告知用户需要引用哪个jar包）
    }

    @Override
    public Logger getLogger(String name, LoggerProvider provider) {
        return new Log4j2LoggerImpl(LogManager.getLogger(name), this, provider);
    }

    @Override
    public Logger getLogger(Class<?> clazz, LoggerProvider provider) {
        return new Log4j2LoggerImpl(LogManager.getLogger(clazz), this, provider);
    }
}
