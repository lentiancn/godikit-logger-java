package com.godikit.logger.spi.provider.impl;

import com.godikit.logger.Logger;
import com.godikit.logger.impl.Log4j2LoggerImpl;
import com.godikit.logger.enums.LoggerProviderEnum;
import com.godikit.logger.spi.provider.LoggerProvider;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Log4j2LoggerProviderImpl implements LoggerProvider {

    @Override
    public String getName() {
        return LoggerProviderEnum.LOG4J2.getName();
    }

    @Override
    public Logger getLogger(String name) {
        return new Log4j2LoggerImpl(LogManager.getLogger(name), this);
    }

    @Override
    public Logger getLogger(Class<?> clazz) {
        return new Log4j2LoggerImpl(LogManager.getLogger(clazz), this);
    }
}
