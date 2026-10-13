package com.godikit.logger.impl;

import com.godikit.logger.Logger;
import com.godikit.logger.enums.LoggerFacadeEnum;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.spi.ExtendedLogger;

import java.util.Objects;

/**
 * Adapts the GodiKit Logger API to Log4j2 without depending on a backend.
 */
public final class Log4j2LoggerImpl implements Logger {

    private static final String FQCN = Log4j2LoggerImpl.class.getName();

    private final Logger delegate;
    private final String providerName;

    public Log4j2LoggerImpl(Logger delegate, String providerName) {
        this.delegate = Objects.requireNonNull(delegate, "Delegate must not be null.");
        this.providerName = Objects.requireNonNull(
            providerName, "Provider name must not be null.");
    }

    @Override
    public String getFacadeName() {
        return LoggerFacadeEnum.LOG4J2.getName();
    }

    @Override
    public Logger getFacadeLogger() {
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
        log(Level.TRACE, null, msg, args);
    }

    @Override
    public void trace(Throwable cause) {
        log(Level.TRACE, cause, cause == null ? null : cause.getMessage(), null);
    }

    @Override
    public void trace(Throwable cause, String msg, Object... args) {
        log(Level.TRACE, cause, msg, args);
    }

    @Override
    public void debug(String msg, Object... args) {
        log(Level.DEBUG, null, msg, args);
    }

    @Override
    public void debug(Throwable cause) {
        log(Level.DEBUG, cause, cause == null ? null : cause.getMessage(), null);
    }

    @Override
    public void debug(Throwable cause, String msg, Object... args) {
        log(Level.DEBUG, cause, msg, args);
    }

    @Override
    public void info(String msg, Object... args) {
        log(Level.INFO, null, msg, args);
    }

    @Override
    public void info(Throwable cause) {
        log(Level.INFO, cause, cause == null ? null : cause.getMessage(), null);
    }

    @Override
    public void info(Throwable cause, String msg, Object... args) {
        log(Level.INFO, cause, msg, args);
    }

    @Override
    public void warn(String msg, Object... args) {
        log(Level.WARN, null, msg, args);
    }

    @Override
    public void warn(Throwable cause) {
        log(Level.WARN, cause, cause == null ? null : cause.getMessage(), null);
    }

    @Override
    public void warn(Throwable cause, String msg, Object... args) {
        log(Level.WARN, cause, msg, args);
    }

    @Override
    public void error(String msg, Object... args) {
        log(Level.ERROR, null, msg, args);
    }

    @Override
    public void error(Throwable cause) {
        log(Level.ERROR, cause, cause == null ? null : cause.getMessage(), null);
    }

    @Override
    public void error(Throwable cause, String msg, Object... args) {
        log(Level.ERROR, cause, msg, args);
    }

    private void log(Level level, Throwable cause, String msg, Object[] args) {
        if (!isEnabled(level)) {
            return;
        }

        if (delegate instanceof ExtendedLogger) {
            ExtendedLogger extLogger = (ExtendedLogger) delegate;
            if (cause != null && args != null && args.length > 0) {
                extLogger.logIfEnabled(FQCN, level, null, cause, msg, args);
            } else if (cause != null) {
                extLogger.logIfEnabled(FQCN, level, null, cause, msg);
            } else if (args != null && args.length > 0) {
                extLogger.logIfEnabled(FQCN, level, null, msg, args);
            } else {
                extLogger.logIfEnabled(FQCN, level, null, msg);
            }
            return;
        }

        if (cause != null) {
            delegate.log(FQCN, level, msg, cause);
        } else if (args != null && args.length > 0) {
            delegate.log(FQCN, level, msg, args);
        } else {
            delegate.log(FQCN, level, msg);
        }
    }

    private boolean isEnabled(Level level) {
        switch (level) {
            case TRACE:
                return delegate.isTraceEnabled();
            case DEBUG:
                return delegate.isDebugEnabled();
            case INFO:
                return delegate.isInfoEnabled();
            case WARN:
                return delegate.isWarnEnabled();
            case ERROR:
            case FATAL:
                return delegate.isErrorEnabled();
            default:
                return delegate.isEnabled(level);
        }
    }
}
