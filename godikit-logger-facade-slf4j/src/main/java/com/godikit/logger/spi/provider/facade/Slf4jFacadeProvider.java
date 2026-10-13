package com.godikit.logger.spi.provider.facade;

import com.godikit.logger.Logger;
import com.godikit.logger.impl.Slf4jLoggerImpl;
import com.godikit.logger.spi.facade.LoggerFacadeProvider;
import com.godikit.logger.spi.provider.impl.LoggerImplProvider;
import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * GodiKit SPI provider for the SLF4J facade.
 */
public final class Slf4jFacadeProvider implements LoggerFacadeProvider {

    @Override
    public String getName() {
        return "slf4j";
    }

    @Override
    public ILoggerFactory getFacadeFactory() {
        return org.slf4j.LoggerFactory.getILoggerFactory();
    }

    @Override
    public Logger getLogger(String name, LoggerImplProvider implProvider) {
        return new Slf4jLoggerImpl(LoggerFactory.getLogger(name), this, implProvider);
    }

    @Override
    public Logger getLogger(Class<?> clazz, LoggerImplProvider implProvider) {
        return new Slf4jLoggerImpl(LoggerFactory.getLogger(clazz), this, implProvider);
    }

    @Override
    public Logger wrapLogger(Object delegate, LoggerImplProvider implProvider) {
        Objects.requireNonNull(delegate, "Delegate logger must not be null.");
        validateFactory(implProvider);
        return wrapValidated(delegate, implProvider);
    }

    private ILoggerFactory validateFactory(LoggerImplProvider implProvider) {
        Objects.requireNonNull(
            implProvider, "Implementation provider must not be null.");

        ILoggerFactory factory = getFacadeFactory();
        if (!implProvider.supportsFactory(this)) {
            throw new IllegalStateException(
                "Active SLF4J factory does not match implementation '"
                    + implProvider.getName() + "': "
                    + (factory == null ? "null" : factory.getClass().getName()));
        }
        return factory;
    }

    private Logger wrapValidated(Object delegate, LoggerImplProvider implProvider) {
        if (!(delegate instanceof org.slf4j.Logger)
            || !implProvider.supportsLogger(getName(), delegate)) {
            throw new IllegalArgumentException(
                "Native logger does not match slf4j+" + implProvider.getName()
                    + ": " + (delegate == null ? "null" : delegate.getClass().getName()));
        }

        return new Slf4jLoggerImpl(
            (org.slf4j.Logger) delegate, this, implProvider);
    }
}
