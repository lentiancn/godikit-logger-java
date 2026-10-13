package com.godikit.logger.spi.provider.facade;

import com.godikit.logger.Logger;
import com.godikit.logger.impl.Slf4jLoggerImpl;
import com.godikit.logger.spi.facade.LoggerFacade;
import com.godikit.logger.spi.provider.impl.LoggerProvider;
import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * GodiKit SPI provider for the SLF4J facade.
 */
public final class Slf4jFacadeProvider implements LoggerFacade {

    @Override
    public String getName() {
        return "slf4j";
    }

    @Override
    public ILoggerFactory getFacadeFactory() {
        return org.slf4j.LoggerFactory.getILoggerFactory();
    }

    @Override
    public Logger getLogger(String name, LoggerProvider provider) {
        return new Slf4jLoggerImpl(LoggerFactory.getLogger(name), this, provider);
    }

    @Override
    public Logger getLogger(Class<?> clazz, LoggerProvider provider) {
        return new Slf4jLoggerImpl(LoggerFactory.getLogger(clazz), this, provider);
    }

    @Override
    public Logger wrapLogger(Object delegate, LoggerProvider provider) {
        Objects.requireNonNull(delegate, "Delegate logger must not be null.");
        validateFactory(provider);
        return wrapValidated(delegate, provider);
    }

    private ILoggerFactory validateFactory(LoggerProvider provider) {
        Objects.requireNonNull(
            provider, "Implementation provider must not be null.");

        ILoggerFactory factory = getFacadeFactory();
        if (!provider.supportsFactory(this)) {
            throw new IllegalStateException(
                "Active SLF4J factory does not match implementation '"
                    + provider.getName() + "': "
                    + (factory == null ? "null" : factory.getClass().getName()));
        }
        return factory;
    }

    private Logger wrapValidated(Object delegate, LoggerProvider provider) {
        if (!(delegate instanceof org.slf4j.Logger)
            || !provider.supportsLogger(getName(), delegate)) {
            throw new IllegalArgumentException(
                "Native logger does not match slf4j+" + provider.getName()
                    + ": " + (delegate == null ? "null" : delegate.getClass().getName()));
        }

        return new Slf4jLoggerImpl(
            (org.slf4j.Logger) delegate, this, provider);
    }
}
