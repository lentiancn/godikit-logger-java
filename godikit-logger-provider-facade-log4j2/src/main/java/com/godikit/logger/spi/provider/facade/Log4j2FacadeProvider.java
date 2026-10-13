package com.godikit.logger.spi.provider.facade;

import com.godikit.logger.Logger;
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
        return "log4j2";
    }

    @Override
    public LoggerContext getFacadeFactory() {
        return LogManager.getContext();
    }

    @Override
    public Logger getLogger(String name, LoggerImplProvider implementation) {
        return new Log4j2LoggerImpl(LogManager.getLogger(name), getName());
    }

    @Override
    public Logger wrapLogger(Object delegate, LoggerImplProvider implementation) {
        Objects.requireNonNull(delegate, "Delegate logger must not be null.");
        validateFactory(implementation);
        return wrapValidated(delegate, implementation);
    }

    private LoggerContext validateFactory(LoggerImplProvider implementation) {
        Objects.requireNonNull(
            implementation, "Implementation provider must not be null.");

        LoggerContext context = getFacadeFactory();
        if (!implementation.supportsFactory(getName(), context)) {
            throw new IllegalStateException(
                "Active Log4j2 context does not match implementation '"
                    + implementation.getName() + "': "
                    + (context == null ? "null" : context.getClass().getName()));
        }
        return context;
    }

    private Logger wrapValidated(Object delegate, LoggerImplProvider implementation) {
        if (!(delegate instanceof org.apache.logging.log4j.Logger)
            || !implementation.supportsLogger(getName(), delegate)) {
            throw new IllegalArgumentException(
                "Native logger does not match log4j2+" + implementation.getName()
                    + ": " + (delegate == null ? "null" : delegate.getClass().getName()));
        }

        return new Log4j2LoggerImpl(
            (org.apache.logging.log4j.Logger) delegate, implementation.getName());
    }
}
