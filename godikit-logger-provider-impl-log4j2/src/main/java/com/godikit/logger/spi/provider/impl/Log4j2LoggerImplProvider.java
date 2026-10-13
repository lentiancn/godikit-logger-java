package com.godikit.logger.spi.provider.impl;

import com.godikit.logger.enums.LoggerFacadeEnum;
import com.godikit.logger.enums.LoggerImplEnum;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.spi.LoggerContext;
import org.apache.logging.log4j.spi.LoggerContextFactory;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Log4j2LoggerImplProvider implements LoggerImplProvider {

    @Override
    public String getName() {
        return LoggerImplEnum.LOG4J2.getName();
    }

    @Override
    public boolean supportsFactory(String facadeName, Object factory) {
        if (!LoggerFacadeEnum.LOG4J2.getName().equals(facadeName)) {
            return false;
        }
        return factory instanceof LoggerContext
            || factory instanceof LoggerContextFactory
            || factory instanceof org.apache.logging.log4j.spi.LoggerContext;
    }

    @Override
    public boolean supportsLogger(String facadeName, Object logger) {
        return LoggerFacadeEnum.LOG4J2.getName().equals(facadeName) && logger instanceof Logger;
    }
}
