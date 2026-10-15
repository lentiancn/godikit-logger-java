/*
 * MIT License
 *
 * Copyright (c) 2026 Len (田隆)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.godikit.logger.impl;

import com.godikit.logger.Logger;
import com.godikit.logger.spi.provider.LoggerProvider;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;
import org.slf4j.spi.LocationAwareLogger;

import java.util.Objects;

import static org.slf4j.spi.LocationAwareLogger.*;

/**
 * {@link Logger} implementation backed by an SLF4J {@link org.slf4j.Logger} (typically Logback).
 *
 * <p>Delegates all log calls to the underlying SLF4J logger. When the delegate
 * implements {@link LocationAwareLogger}, the fully-qualified caller class name
 * (FQCN) is supplied so that Logback can report the correct caller location.
 * Message formatting follows SLF4J's {@code {}} placeholder convention via
 * {@link MessageFormatter}.</p>
 */
public final class Slf4jLogbackLoggerImpl implements Logger {

    /** Fully-qualified class name used for caller-location resolution by LocationAwareLogger. */
    private static final String FQCN = Slf4jLogbackLoggerImpl.class.getName();

    /** Empty argument array reused for throwable-only log calls. */
    private static final Object[] NO_ARGUMENTS = new Object[0];

    /** The logger name. */
    private final String name;

    /** The underlying SLF4J logger that performs the actual logging. */
    private final org.slf4j.Logger delegate;

    /** The provider that created this logger. */
    private final LoggerProvider provider;

    /**
     * Constructs an SLF4J/Logback logger.
     *
     * @param name     the logger name
     * @param delegate the underlying SLF4J logger; must not be {@code null}
     * @param provider the provider that created this logger
     */
    public Slf4jLogbackLoggerImpl(String name, org.slf4j.Logger delegate, LoggerProvider provider) {
        this.name = name;
        this.delegate = Objects.requireNonNull(delegate, "Delegate must not be null.");
        this.provider = provider;
    }

    @Override
    public LoggerProvider getProvider() {
        return provider;
    }

    @Override
    public Object getOriginalLogger() {
        return delegate;
    }

    @Override
    public String getName() {
        return name;
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

    /**
     * Performs the actual logging at the given SLF4J level.
     *
     * <p>If the delegate is a {@link LocationAwareLogger}, the FQCN is passed so
     * that the caller's location is resolved correctly. Otherwise the level is
     * dispatched through the standard SLF4J API.</p>
     *
     * @param level   the SLF4J level integer (e.g. {@link #INFO_INT})
     * @param cause   the throwable to log (may be {@code null})
     * @param message the message pattern with {@code {}} placeholders
     * @param args    the arguments to substitute
     */
    private void log(int level, Throwable cause, String message, Object[] args) {
        if (!isEnabled(level)) {
            return;
        }

        FormattingTuple formatted = cause == null
            ? MessageFormatter.arrayFormat(message, args)
            : MessageFormatter.arrayFormat(message, args, cause);

        String msg = formatted.getMessage();
        Throwable throwable = formatted.getThrowable();

        if (delegate instanceof LocationAwareLogger) {
            ((LocationAwareLogger) delegate).log(null, FQCN, level, msg, null, throwable);
            return;
        }

        switch (level) {
            case TRACE_INT:
                delegate.trace(msg, throwable);
                break;
            case DEBUG_INT:
                delegate.debug(msg, throwable);
                break;
            case INFO_INT:
                delegate.info(msg, throwable);
                break;
            case WARN_INT:
                delegate.warn(msg, throwable);
                break;
            case ERROR_INT:
                delegate.error(msg, throwable);
                break;
            default:
                throw new IllegalArgumentException("Unsupported log level: " + level);
        }
    }

    /**
     * Logs a throwable-only message by extracting its message as the pattern.
     *
     * @param level the SLF4J level integer
     * @param cause the throwable to log
     */
    private void logCause(int level, Throwable cause) {
        log(level, cause, cause == null ? null : cause.getMessage(), NO_ARGUMENTS);
    }

    /**
     * Checks whether the given level is enabled on the underlying logger.
     *
     * @param level the SLF4J level integer
     * @return {@code true} if the level is enabled
     * @throws IllegalArgumentException if the level is not one of the SLF4J level constants
     */
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

    @Override
    public String toString() {
        return delegate.toString();
    }
}
