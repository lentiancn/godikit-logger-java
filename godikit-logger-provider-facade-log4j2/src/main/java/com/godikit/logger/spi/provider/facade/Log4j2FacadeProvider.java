package com.godikit.logger.spi.provider.facade;

import com.godikit.logger.Logger;
import com.godikit.logger.enums.LoggerFacadeEnum;
import com.godikit.logger.impl.Log4j2LoggerImpl;
import com.godikit.logger.spi.provider.impl.LoggerImplProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.spi.LoggerContext;

import java.util.Objects;

/**
 * GodiKit SPI provider for the Log4j2 facade.
 */
public final class Log4j2FacadeProvider implements LoggerFacadeProvider {

    @Override
    public String getName() {
        return LoggerFacadeEnum.LOG4J2.getName();
    }

    @Override
    public LoggerContext getFacadeFactory() {
        return LogManager.getContext();
    }

    @Override
    public Logger getLogger(String name, LoggerImplProvider implProvider) {
        return new Log4j2LoggerImpl(LogManager.getLogger(name), this, implProvider);
    }

    @Override
    public Logger getLogger(Class<?> clazz, LoggerImplProvider implProvider) {
        return new Log4j2LoggerImpl(LogManager.getLogger(clazz), this, implProvider);
    }

    @Override
    public Logger wrapLogger(Object delegate, LoggerImplProvider implProvider) {
        Objects.requireNonNull(delegate, "Delegate logger must not be null.");
        validateFactory(implProvider);
        return wrapValidated(delegate, implProvider);
    }

    private LoggerContext validateFactory(LoggerImplProvider implProvider) {
        Objects.requireNonNull(
            implProvider, "Implementation provider must not be null.");

        LoggerContext context = getFacadeFactory();
        if (!implProvider.supportsFactory(this)) {
            throw new IllegalStateException(
                "Active Log4j2 context does not match implementation '"
                    + implProvider.getName() + "': "
                    + (context == null ? "null" : context.getClass().getName()));
        }
        return context;
    }

    private Logger wrapValidated(Object delegate, LoggerImplProvider implProvider) {
        if (!(delegate instanceof org.apache.logging.log4j.Logger)
            || !implProvider.supportsLogger(getName(), delegate)) {
            throw new IllegalArgumentException(
                "Native logger does not match log4j2+" + implProvider.getName()
                    + ": " + (delegate == null ? "null" : delegate.getClass().getName()));
        }

        return new Log4j2LoggerImpl(
            (org.apache.logging.log4j.Logger) delegate, this, implProvider);
    }
}
