package com.godikit.logger.spi.facade.impl;

import com.godikit.logger.Logger;
import com.godikit.logger.enums.LoggerFacadeEnum;
import com.godikit.logger.impl.Log4j2LoggerImpl;
import com.godikit.logger.spi.facade.LoggerFacade;
import com.godikit.logger.spi.provider.LoggerProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.spi.LoggerContext;

import java.util.Objects;

/**
 * GodiKit SPI provider for the Log4j2 facade.
 */
public final class Log4j2LoggerFacadeImpl implements LoggerFacade {

    @Override
    public String getName() {
        return LoggerFacadeEnum.LOG4J2.getName();
    }

    @Override
    public LoggerContext getFacadeFactory() {
        return LogManager.getContext();
    }

    @Override
    public Logger getLogger(String name, LoggerProvider provider) {
        return new Log4j2LoggerImpl(LogManager.getLogger(name), this, provider);
    }

    @Override
    public Logger getLogger(Class<?> clazz, LoggerProvider provider) {
        return new Log4j2LoggerImpl(LogManager.getLogger(clazz), this, provider);
    }

    @Override
    public Logger wrapLogger(Object delegate, LoggerProvider provider) {
        Objects.requireNonNull(delegate, "Delegate logger must not be null.");
        validateFactory(provider);
        return wrapValidated(delegate, provider);
    }

    private LoggerContext validateFactory(LoggerProvider provider) {
        Objects.requireNonNull(
            provider, "Implementation provider must not be null.");

        LoggerContext context = getFacadeFactory();
        if (!provider.supportsFactory(this)) {
            throw new IllegalStateException(
                "Active Log4j2 context does not match implementation '"
                    + provider.getName() + "': "
                    + (context == null ? "null" : context.getClass().getName()));
        }
        return context;
    }

    private Logger wrapValidated(Object delegate, LoggerProvider provider) {
        if (!(delegate instanceof org.apache.logging.log4j.Logger)
            || !provider.supportsLogger(getName(), delegate)) {
            throw new IllegalArgumentException(
                "Native logger does not match log4j2+" + provider.getName()
                    + ": " + (delegate == null ? "null" : delegate.getClass().getName()));
        }

        return new Log4j2LoggerImpl(
            (org.apache.logging.log4j.Logger) delegate, this, provider);
    }
}
