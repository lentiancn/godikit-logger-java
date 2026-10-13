package com.godikit.logger.spi.provider.facade;

import com.godikit.logger.Logger;
import com.godikit.logger.impl.Slf4jLoggerImpl;
import com.godikit.logger.spi.provider.impl.LoggerImplProvider;
import org.slf4j.ILoggerFactory;

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
    public Logger createLogger(String name, LoggerImplProvider implementation) {
        Objects.requireNonNull(name, "Logger name must not be null.");
        ILoggerFactory factory = validateFactory(implementation);
        return wrapValidated(factory.getLogger(name), implementation);
    }

    @Override
    public Logger wrapLogger(Object delegate, LoggerImplProvider implementation) {
        Objects.requireNonNull(delegate, "Delegate logger must not be null.");
        validateFactory(implementation);
        return wrapValidated(delegate, implementation);
    }

    private ILoggerFactory validateFactory(LoggerImplProvider implementation) {
        Objects.requireNonNull(
            implementation, "Implementation provider must not be null.");

        ILoggerFactory factory = getFacadeFactory();
        if (!implementation.supportsFactory(getName(), factory)) {
            throw new IllegalStateException(
                "Active SLF4J factory does not match implementation '"
                    + implementation.getName() + "': "
                    + (factory == null ? "null" : factory.getClass().getName()));
        }
        return factory;
    }

    private Logger wrapValidated(Object delegate, LoggerImplProvider implementation) {
        if (!(delegate instanceof org.slf4j.Logger)
            || !implementation.supportsLogger(getName(), delegate)) {
            throw new IllegalArgumentException(
                "Native logger does not match slf4j+" + implementation.getName()
                    + ": " + (delegate == null ? "null" : delegate.getClass().getName()));
        }

        return new Slf4jLoggerImpl(
            (org.slf4j.Logger) delegate, implementation.getName());
    }
}
