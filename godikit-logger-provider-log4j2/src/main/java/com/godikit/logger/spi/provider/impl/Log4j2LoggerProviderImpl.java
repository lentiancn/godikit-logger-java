package com.godikit.logger.spi.provider.impl;

import com.godikit.logger.enums.LoggerFacadeEnum;
import com.godikit.logger.enums.LoggerProviderEnum;
import com.godikit.logger.spi.facade.LoggerFacade;
import com.godikit.logger.spi.provider.LoggerProvider;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.spi.LoggerContext;
import org.apache.logging.log4j.spi.LoggerContextFactory;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Log4j2LoggerProviderImpl implements LoggerProvider {

    @Override
    public String getName() {
        return LoggerProviderEnum.LOG4J2.getName();
    }

    @Override
    public boolean supportsFactory(LoggerFacade facade) {
        if (!LoggerFacadeEnum.LOG4J2.getName().equals(facade.getName())) {
            return false;
        }
        Object factory = facade.getFacadeFactory();

        return factory instanceof LoggerContext
            || factory instanceof LoggerContextFactory
            || factory instanceof org.apache.logging.log4j.spi.LoggerContext;
    }

    @Override
    public boolean supportsLogger(String facadeName, Object logger) {
        return LoggerFacadeEnum.LOG4J2.getName().equals(facadeName) && logger instanceof Logger;
    }
}
