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

import com.godikit.exception.utils.StackTraceUtils;
import com.godikit.logger.Logger;
import com.godikit.logger.spi.provider.LoggerProvider;
import com.godikit.logger.utils.StringUtils;
import org.slf4j.helpers.MessageFormatter;
import org.slf4j.spi.LocationAwareLogger;

import java.util.Objects;

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

    /**
     * Fully-qualified class name used for caller-location resolution by LocationAwareLogger.
     */
    private static final String FQCN = Slf4jLogbackLoggerImpl.class.getName();

    /**
     * Empty argument array reused for throwable-only log calls.
     */
    private static final Object[] NO_ARGUMENTS = new Object[0];

    /**
     * The logger name.
     */
    private final String name;

    /**
     * The underlying SLF4J logger that performs the actual logging.
     */
    private final org.slf4j.Logger delegate;

    /**
     * The provider that created this logger.
     */
    private final LoggerProvider provider;

    /**
     * Constructs an SLF4J/Logback logger.
     *
     * @param name     the logger name
     * @param delegate the underlying SLF4J logger; must not be {@code null}
     * @param provider the provider that created this logger
     */
    public Slf4jLogbackLoggerImpl(final String name, final org.slf4j.Logger delegate, final LoggerProvider provider) {
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
    public void trace(final String msg, final Object... args) {
        delegate.trace(msg, args);
    }

    @Override
    public void trace(final Throwable cause) {
        delegate.trace(StackTraceUtils.toString(cause));
    }

    @Override
    public void trace(Throwable cause, String msg, Object... args) {
        delegate.trace(mergeMsgAndThrowable(msg, cause), args);
    }

    @Override
    public void debug(String msg, Object... args) {
        delegate.debug(msg, args);
    }

    @Override
    public void debug(Throwable cause) {
        delegate.debug(StackTraceUtils.toString(cause));
    }

    @Override
    public void debug(Throwable cause, String msg, Object... args) {
        delegate.debug(mergeMsgAndThrowable(msg, cause), args);
    }

    @Override
    public void info(String msg, Object... args) {
        delegate.info(msg, args);
    }

    @Override
    public void info(Throwable cause) {
        delegate.info(StackTraceUtils.toString(cause));
    }

    @Override
    public void info(Throwable cause, String msg, Object... args) {
        delegate.info(mergeMsgAndThrowable(msg, cause), args);
    }

    @Override
    public void warn(String msg, Object... args) {
        delegate.warn(msg, args);
    }

    @Override
    public void warn(Throwable cause) {
        delegate.warn(StackTraceUtils.toString(cause));
    }

    @Override
    public void warn(Throwable cause, String msg, Object... args) {
        delegate.warn(mergeMsgAndThrowable(msg, cause), args);
    }

    @Override
    public void error(String msg, Object... args) {
        delegate.error(msg, args);
    }

    @Override
    public void error(Throwable cause) {
        delegate.error(StackTraceUtils.toString(cause));
    }

    @Override
    public void error(Throwable cause, String msg, Object... args) {
        delegate.error(mergeMsgAndThrowable(msg, cause), args);
    }

    private String mergeMsgAndThrowable(final String msg, final Throwable cause) {
        return (StringUtils.isBlank(msg) ? "" : msg) + (cause != null ? StackTraceUtils.toString(cause) : "");
    }
}
