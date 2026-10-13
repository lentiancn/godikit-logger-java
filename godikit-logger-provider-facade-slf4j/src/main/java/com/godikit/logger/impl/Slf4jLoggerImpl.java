package com.godikit.logger.impl;

import com.godikit.logger.Logger;
import com.godikit.logger.enums.LoggerFacadeEnum;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;
import org.slf4j.spi.LocationAwareLogger;

import java.util.Objects;

import static org.slf4j.spi.LocationAwareLogger.DEBUG_INT;
import static org.slf4j.spi.LocationAwareLogger.ERROR_INT;
import static org.slf4j.spi.LocationAwareLogger.INFO_INT;
import static org.slf4j.spi.LocationAwareLogger.TRACE_INT;
import static org.slf4j.spi.LocationAwareLogger.WARN_INT;

/**
 * Adapts the GodiKit Logger API to SLF4J without depending on a backend.
 */
public final class Slf4jLoggerImpl implements Logger {

    private static final String FQCN = Slf4jLoggerImpl.class.getName();
    private static final Object[] NO_ARGUMENTS = new Object[0];

    private final org.slf4j.Logger delegate;
    private final String providerName;

    /**
     * Called by the facade provider after implementation validation.
     */
    public Slf4jLoggerImpl(org.slf4j.Logger delegate, String providerName) {
        this.delegate = Objects.requireNonNull(delegate, "Delegate must not be null.");
        this.providerName = Objects.requireNonNull(
            providerName, "Provider name must not be null.");
    }

    @Override
    public String getFacadeName() {
        return LoggerFacadeEnum.SLF4J.getName();
    }

    @Override
    public org.slf4j.Logger getFacadeLogger() {
        return delegate;
    }

    @Override
    public String getProviderName() {
        return providerName;
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
        log(TRACE_INT, null, msg, args);
    }

    @Override
    public void trace(Throwable cause) {
        logCause(TRACE_INT, cause);
    }

    @Override
    public void trace(Throwable cause, String msg, Object... args) {
        log(TRACE_INT, cause, msg, args);
    }

    @Override
    public void debug(String msg, Object... args) {
        log(DEBUG_INT, null, msg, args);
    }

    @Override
    public void debug(Throwable cause) {
        logCause(DEBUG_INT, cause);
    }

    @Override
    public void debug(Throwable cause, String msg, Object... args) {
        log(DEBUG_INT, cause, msg, args);
    }

    @Override
    public void info(String msg, Object... args) {
        log(INFO_INT, null, msg, args);
    }

    @Override
    public void info(Throwable cause) {
        logCause(INFO_INT, cause);
    }

    @Override
    public void info(Throwable cause, String msg, Object... args) {
        log(INFO_INT, cause, msg, args);
    }

    @Override
    public void warn(String msg, Object... args) {
        log(WARN_INT, null, msg, args);
    }

    @Override
    public void warn(Throwable cause) {
        logCause(WARN_INT, cause);
    }

    @Override
    public void warn(Throwable cause, String msg, Object... args) {
        log(WARN_INT, cause, msg, args);
    }

    @Override
    public void error(String msg, Object... args) {
        log(ERROR_INT, null, msg, args);
    }

    @Override
    public void error(Throwable cause) {
        logCause(ERROR_INT, cause);
    }

    @Override
    public void error(Throwable cause, String msg, Object... args) {
        log(ERROR_INT, cause, msg, args);
    }

    private void logCause(int level, Throwable cause) {
        log(level, cause, cause == null ? null : cause.getMessage(), NO_ARGUMENTS);
    }

    private void log(int level, Throwable cause, String msg, Object[] args) {
        if (!isEnabled(level)) {
            return;
        }

        // Preserve SLF4J's trailing-Throwable convention unless an explicit
        // cause is provided. The explicit cause always takes precedence.
        FormattingTuple formatted = cause == null
            ? MessageFormatter.arrayFormat(msg, args)
            : MessageFormatter.arrayFormat(msg, args, cause);

        String message = formatted.getMessage();
        Throwable throwable = formatted.getThrowable();

        if (delegate instanceof LocationAwareLogger) {
            ((LocationAwareLogger) delegate).log(
                null, FQCN, level, message, null, throwable);
            return;
        }

        switch (level) {
            case TRACE_INT:
                delegate.trace(message, throwable);
                break;
            case DEBUG_INT:
                delegate.debug(message, throwable);
                break;
            case INFO_INT:
                delegate.info(message, throwable);
                break;
            case WARN_INT:
                delegate.warn(message, throwable);
                break;
            case ERROR_INT:
                delegate.error(message, throwable);
                break;
            default:
                throw new IllegalArgumentException("Unsupported log level: " + level);
        }
    }

    private boolean isEnabled(int level) {
        switch (level) {
            case TRACE_INT:
                return delegate.isTraceEnabled();
            case DEBUG_INT:
                return delegate.isDebugEnabled();
            case INFO_INT:
                return delegate.isInfoEnabled();
            case WARN_INT:
                return delegate.isWarnEnabled();
            case ERROR_INT:
                return delegate.isErrorEnabled();
            default:
                throw new IllegalArgumentException("Unsupported log level: " + level);
        }
    }
}
