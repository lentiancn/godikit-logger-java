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
import org.slf4j.spi.LoggingEventBuilder;

import java.util.Objects;

/**
 * {@link Logger} implementation backed by an SLF4J 2.x {@link org.slf4j.Logger} (typically Logback).
 *
 * <p>Delegates all log calls to the underlying SLF4J logger using the fluent API
 * introduced in SLF4J 2.x via {@link LoggingEventBuilder}. Message formatting
 * follows SLF4J's {@code {}} placeholder convention.</p>
 */
public final class Slf4j2LogbackLoggerImpl implements Logger {

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
     * Constructs an SLF4J 2.x/Logback logger.
     *
     * @param name     the logger name
     * @param delegate the underlying SLF4J logger; must not be {@code null}
     * @param provider the provider that created this logger
     */
    public Slf4j2LogbackLoggerImpl(final String name, final org.slf4j.Logger delegate, final LoggerProvider provider) {
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
        delegate.atTrace().log(msg, args);
    }

    @Override
    public void trace(final Throwable cause) {
        delegate.atTrace().setCause(cause).log();
    }

    @Override
    public void trace(Throwable cause, String msg, Object... args) {
        delegate.atTrace().setCause(cause).log(msg, args);
    }

    @Override
    public void debug(String msg, Object... args) {
        delegate.atDebug().log(msg, args);
    }

    @Override
    public void debug(Throwable cause) {
        delegate.atDebug().setCause(cause).log();
    }

    @Override
    public void debug(Throwable cause, String msg, Object... args) {
        delegate.atDebug().setCause(cause).log(msg, args);
    }

    @Override
    public void info(String msg, Object... args) {
        delegate.atInfo().log(msg, args);
    }

    @Override
    public void info(Throwable cause) {
        delegate.atInfo().setCause(cause).log();
    }

    @Override
    public void info(Throwable cause, String msg, Object... args) {
        delegate.atInfo().setCause(cause).log(msg, args);
    }

    @Override
    public void warn(String msg, Object... args) {
        delegate.atWarn().log(msg, args);
    }

    @Override
    public void warn(Throwable cause) {
        delegate.atWarn().setCause(cause).log();
    }

    @Override
    public void warn(Throwable cause, String msg, Object... args) {
        delegate.atWarn().setCause(cause).log(msg, args);
    }

    @Override
    public void error(String msg, Object... args) {
        delegate.atError().log(msg, args);
    }

    @Override
    public void error(Throwable cause) {
        delegate.atError().setCause(cause).log();
    }

    @Override
    public void error(Throwable cause, String msg, Object... args) {
        delegate.atError().setCause(cause).log(msg, args);
    }
}
