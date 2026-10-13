package com.godikit.logger.impl;

import com.godikit.logger.Logger;
import com.godikit.logger.spi.provider.facade.LoggerFacadeProvider;
import com.godikit.logger.spi.provider.impl.LoggerImplProvider;

/**
 * Adapts the GodiKit Logger API to Log4j2 without depending on a backend.
 */
public final class Log4j2LoggerImpl implements Logger {

    private final org.apache.logging.log4j.Logger delegate;

    private final LoggerFacadeProvider facadeProvider;
    private final LoggerImplProvider implProvider;

    public Log4j2LoggerImpl(org.apache.logging.log4j.Logger delegate, LoggerFacadeProvider facadeProvider, LoggerImplProvider implProvider) {
        this.delegate = delegate;
        this.facadeProvider = facadeProvider;
        this.implProvider = implProvider;
    }

    @Override
    public LoggerFacadeProvider getFacadeProvider() {
        return facadeProvider;
    }

    @Override
    public LoggerImplProvider getImplProvider() {
        return implProvider;
    }

    @Override
    public Object getOriginalLogger() {
        return delegate;
    }

    @Override
    public boolean isTraceEnabled() {
        return delegate.isTraceEnabled();
    }

    @Override
    public boolean isDebugEnabled() {
        return delegate.isDebugEnabled();
    }

    @Override
    public boolean isInfoEnabled() {
        return delegate.isInfoEnabled();
    }

    @Override
    public boolean isWarnEnabled() {
        return delegate.isWarnEnabled();
    }

    @Override
    public boolean isErrorEnabled() {
        return delegate.isErrorEnabled();
    }

    @Override
    public void trace(String msg, Object... args) {
        delegate.trace(msg, args);
    }

    @Override
    public void trace(Throwable cause) {
        delegate.trace(cause.getMessage(), cause);
    }

    @Override
    public void trace(Throwable cause, String msg, Object... args) {
        delegate.trace(msg, cause);
    }

    @Override
    public void debug(String msg, Object... args) {
        delegate.debug(msg, args);
    }

    @Override
    public void debug(Throwable cause) {
        delegate.debug(cause.getMessage(), cause);
    }

    @Override
    public void debug(Throwable cause, String msg, Object... args) {
        delegate.debug(msg, cause);
    }

    @Override
    public void info(String msg, Object... args) {
        delegate.info(msg, args);
    }

    @Override
    public void info(Throwable cause) {
        delegate.info(cause.getMessage(), cause);
    }

    @Override
    public void info(Throwable cause, String msg, Object... args) {
        delegate.info(msg, cause);
    }

    @Override
    public void warn(String msg, Object... args) {
        delegate.warn(msg, args);
    }

    @Override
    public void warn(Throwable cause) {
        delegate.warn(cause.getMessage(), cause);
    }

    @Override
    public void warn(Throwable cause, String msg, Object... args) {
        delegate.warn(msg, cause);
    }

    @Override
    public void error(String msg, Object... args) {
        delegate.error(msg, args);
    }

    @Override
    public void error(Throwable cause) {
        delegate.error(cause.getMessage(), cause);
    }

    @Override
    public void error(Throwable cause, String msg, Object... args) {
        delegate.error(msg, cause);
    }
}
