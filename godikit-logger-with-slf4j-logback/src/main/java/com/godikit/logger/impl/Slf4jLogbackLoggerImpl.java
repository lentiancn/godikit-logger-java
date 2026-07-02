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

import com.godikit.error.utils.ThrowableUtils;
import com.godikit.logger.Logger;

import static com.godikit.logger.utils.LoggerUtils.mergeMsgAndThrowable;

/**
 * Logger implementation that delegates to SLF4J with Logback as the underlying provider.
 *
 * <p>This implementation wraps {@code org.slf4j.Logger} and uses Logback as the logging backend.
 * The method ordering follows usage frequency for better code readability.</p>
 *
 * <h2>Usage:</h2>
 * <pre>{@code
 * Logger logger = new Slf4jLogbackLoggerImpl(MyClass.class);
 * logger.info("Application started");
 * }</pre>
 *
 * @author Len (len782768@gmail.com)
 * @since 2025-11-07 23:22
 */
public class Slf4jLogbackLoggerImpl implements Logger {

    /**
     * The facade identifier for this logger implementation.
     */
    public static final String FACADE = "slf4j";

    /**
     * The provider identifier for this logger implementation.
     */
    public static final String PROVIDER = "logback";

    /**
     * The underlying SLF4J Logger instance used for actual logging operations.
     */
    private final org.slf4j.Logger FACADE_LOGGER;

    /**
     * Creates a logger with the specified name.
     *
     * @param name the logger name
     */
    public Slf4jLogbackLoggerImpl(final String name) {
        FACADE_LOGGER = org.slf4j.LoggerFactory.getLogger(name);
    }

    /**
     * Creates a logger based on the specified class.
     *
     * @param clazz the class for the logger
     */
    public Slf4jLogbackLoggerImpl(final Class<?> clazz) {
        FACADE_LOGGER = org.slf4j.LoggerFactory.getLogger(clazz);
    }

    /**
     * Creates a logger from an existing SLF4J Logger instance.
     *
     * @param logger the SLF4J Logger instance to wrap
     */
    public Slf4jLogbackLoggerImpl(final org.slf4j.Logger logger) {
        FACADE_LOGGER = logger;
    }

    /**
     * Returns the facade name identifier.
     *
     * @return the facade identifier, always "slf4j"
     */
    @Override
    public String getFacadeName() {
        return FACADE;
    }

    /**
     * Returns the underlying SLF4J Logger instance.
     *
     * @return the wrapped {@code org.slf4j.Logger} instance
     */
    @Override
    public Object getFacadeLogger() {
        return FACADE_LOGGER;
    }

    /**
     * Returns the provider name identifier.
     *
     * @return the provider identifier, always "logback"
     */
    @Override
    public String getProviderName() {
        return PROVIDER;
    }

    /**
     * Checks if TRACE level logging is enabled.
     *
     * @return true if TRACE level is enabled in the underlying logger
     */
    @Override
    public boolean isTraceEnabled() {
        return FACADE_LOGGER.isTraceEnabled();
    }

    /**
     * Checks if DEBUG level logging is enabled.
     *
     * @return true if DEBUG level is enabled in the underlying logger
     */
    @Override
    public boolean isDebugEnabled() {
        return FACADE_LOGGER.isDebugEnabled();
    }

    /**
     * Checks if INFO level logging is enabled.
     *
     * @return true if INFO level is enabled in the underlying logger
     */
    @Override
    public boolean isInfoEnabled() {
        return FACADE_LOGGER.isInfoEnabled();
    }

    /**
     * Checks if WARN level logging is enabled.
     *
     * @return true if WARN level is enabled in the underlying logger
     */
    @Override
    public boolean isWarnEnabled() {
        return FACADE_LOGGER.isWarnEnabled();
    }

    /**
     * Checks if ERROR level logging is enabled.
     *
     * @return true if ERROR level is enabled in the underlying logger
     */
    @Override
    public boolean isErrorEnabled() {
        return FACADE_LOGGER.isErrorEnabled();
    }

    /**
     * Logs a message at TRACE level with optional arguments.
     *
     * @param msg  the message pattern
     * @param args the arguments to be substituted into the message pattern
     */
    @Override
    public void trace(final String msg, final Object... args) {
        FACADE_LOGGER.trace(msg, args);
    }

    /**
     * Logs an exception at TRACE level.
     *
     * @param cause the Throwable to log with its full stack trace
     */
    @Override
    public void trace(final Throwable cause) {
        FACADE_LOGGER.trace(ThrowableUtils.toString(cause));
    }

    /**
     * Logs a message at TRACE level with an exception and optional arguments.
     *
     * @param cause the Throwable to log
     * @param msg   the message pattern
     * @param args  the arguments to be substituted into the message pattern
     */
    @Override
    public void trace(final Throwable cause, final String msg, final Object... args) {
        FACADE_LOGGER.trace(mergeMsgAndThrowable(msg, cause), args);
    }

    /**
     * Logs a message at DEBUG level with optional arguments.
     *
     * @param msg  the message pattern
     * @param args the arguments to be substituted into the message pattern
     */
    @Override
    public void debug(final String msg, final Object... args) {
        FACADE_LOGGER.debug(msg, args);
    }

    /**
     * Logs an exception at DEBUG level.
     *
     * @param cause the Throwable to log with its full stack trace
     */
    @Override
    public void debug(final Throwable cause) {
        FACADE_LOGGER.debug(ThrowableUtils.toString(cause));
    }

    /**
     * Logs a message at DEBUG level with an exception and optional arguments.
     *
     * @param cause the Throwable to log
     * @param msg   the message pattern
     * @param args  the arguments to be substituted into the message pattern
     */
    @Override
    public void debug(final Throwable cause, final String msg, final Object... args) {
        FACADE_LOGGER.debug(mergeMsgAndThrowable(msg, cause), args);
    }

    /**
     * Logs a message at INFO level with optional arguments.
     *
     * @param msg  the message pattern
     * @param args the arguments to be substituted into the message pattern
     */
    @Override
    public void info(final String msg, final Object... args) {
        FACADE_LOGGER.info(msg, args);
    }

    /**
     * Logs an exception at INFO level.
     *
     * @param cause the Throwable to log with its full stack trace
     */
    @Override
    public void info(final Throwable cause) {
        FACADE_LOGGER.info(ThrowableUtils.toString(cause));
    }

    /**
     * Logs a message at INFO level with an exception and optional arguments.
     *
     * @param cause the Throwable to log
     * @param msg   the message pattern
     * @param args  the arguments to be substituted into the message pattern
     */
    @Override
    public void info(final Throwable cause, final String msg, final Object... args) {
        FACADE_LOGGER.info(mergeMsgAndThrowable(msg, cause), args);
    }

    /**
     * Logs a message at WARN level with optional arguments.
     *
     * @param msg  the message pattern
     * @param args the arguments to be substituted into the message pattern
     */
    @Override
    public void warn(final String msg, final Object... args) {
        FACADE_LOGGER.warn(msg, args);
    }

    /**
     * Logs an exception at WARN level.
     *
     * @param cause the Throwable to log with its full stack trace
     */
    @Override
    public void warn(final Throwable cause) {
        FACADE_LOGGER.warn(ThrowableUtils.toString(cause));
    }

    /**
     * Logs a message at WARN level with an exception and optional arguments.
     *
     * @param cause the Throwable to log
     * @param msg   the message pattern
     * @param args  the arguments to be substituted into the message pattern
     */
    @Override
    public void warn(final Throwable cause, final String msg, final Object... args) {
        FACADE_LOGGER.warn(mergeMsgAndThrowable(msg, cause), args);
    }

    /**
     * Logs a message at ERROR level with optional arguments.
     *
     * @param msg  the message pattern
     * @param args the arguments to be substituted into the message pattern
     */
    @Override
    public void error(final String msg, final Object... args) {
        FACADE_LOGGER.error(msg, args);
    }

    /**
     * Logs an exception at ERROR level.
     *
     * @param cause the Throwable to log with its full stack trace
     */
    @Override
    public void error(final Throwable cause) {
        FACADE_LOGGER.error(ThrowableUtils.toString(cause));
    }

    /**
     * Logs a message at ERROR level with an exception and optional arguments.
     *
     * @param cause the Throwable to log
     * @param msg   the message pattern
     * @param args  the arguments to be substituted into the message pattern
     */
    @Override
    public void error(final Throwable cause, final String msg, final Object... args) {
        FACADE_LOGGER.error(mergeMsgAndThrowable(msg, cause), args);
    }
}
