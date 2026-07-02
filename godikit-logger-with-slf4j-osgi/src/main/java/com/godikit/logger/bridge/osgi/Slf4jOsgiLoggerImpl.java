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
package com.godikit.logger.bridge.osgi;

import com.godikit.error.utils.ThrowableUtils;
import com.godikit.logger.Logger;
import org.osgi.service.log.LoggerFactory;

import java.util.Iterator;
import java.util.ServiceLoader;

import static com.godikit.logger.utils.LoggerUtils.mergeMsgAndThrowable;

/**
 * SLF4J OSGi implementation for GodiKit Logger.
 *
 * <p>This implementation uses OSGi Log Service which bridges to SLF4J
 * through osgi-over-slf4j.</p>
 *
 * <p>Usage in OSGi environment:</p>
 * <pre>{@code
 * // This module delegates to OSGi Log Service
 * // osgi-over-slf4j bridges OSGi logs to SLF4J
 * Logger logger = LoggerFactory.getLogger(MyClass.class);
 * logger.info("Message in OSGi environment");
 * }</pre>
 *
 * @author Len (len782768@gmail.com)
 * @since 2026-07-01
 */
public class Slf4jOsgiLoggerImpl implements Logger {

    /**
     * The facade identifier for this logger implementation.
     */
    public static final String FACADE = "slf4j";

    /**
     * The provider identifier for this logger implementation.
     */
    public static final String PROVIDER = "osgi";

    /**
     * The shared OSGi LoggerFactory instance, lazily initialized using double-checked locking.
     */
    private static volatile LoggerFactory loggerFactory;

    /**
     * The underlying OSGi Logger instance used for actual logging operations.
     */
    private final org.osgi.service.log.Logger osgiLogger;

    /**
     * Creates a logger based on the specified class.
     *
     * @param clazz the class for the logger
     */
    public Slf4jOsgiLoggerImpl(final Class<?> clazz) {
        this.osgiLogger = getLoggerFactory().getLogger(clazz);
    }

    /**
     * Creates a logger with the specified name.
     *
     * @param name the logger name
     */
    public Slf4jOsgiLoggerImpl(final String name) {
        this.osgiLogger = getLoggerFactory().getLogger(name);
    }

    /**
     * Creates a logger from an existing OSGi Logger instance.
     *
     * @param facadeLogger the OSGi Logger instance to wrap
     */
    public Slf4jOsgiLoggerImpl(final org.osgi.service.log.Logger facadeLogger) {
        osgiLogger = facadeLogger;
    }

    /**
     * Gets the OSGi LoggerFactory instance using double-checked locking for thread-safe lazy initialization.
     *
     * @return the OSGi LoggerFactory instance
     * @throws IllegalStateException if no OSGi LoggerFactory implementation is found on the classpath
     */
    private static LoggerFactory getLoggerFactory() {
        if (loggerFactory == null) {
            synchronized (Slf4jOsgiLoggerImpl.class) {
                if (loggerFactory == null) {
                    ServiceLoader<LoggerFactory> loader = ServiceLoader.load(LoggerFactory.class);
                    Iterator<LoggerFactory> iterator = loader.iterator();
                    if (iterator.hasNext()) {
                        loggerFactory = iterator.next();
                    } else {
                        throw new IllegalStateException(
                                "No OSGi LoggerFactory implementation found. " +
                                        "Ensure 'osgi-over-slf4j' is on the classpath to enable OSGi logging."
                        );
                    }
                }
            }
        }
        return loggerFactory;
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
     * Returns the underlying OSGi Logger instance.
     *
     * @return the wrapped {@code org.osgi.service.log.Logger} instance
     */
    @Override
    public Object getFacadeLogger() {
        return osgiLogger;
    }

    /**
     * Returns the provider name identifier.
     *
     * @return the provider identifier, always "osgi"
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
        return osgiLogger.isTraceEnabled();
    }

    /**
     * Checks if DEBUG level logging is enabled.
     *
     * @return true if DEBUG level is enabled in the underlying logger
     */
    @Override
    public boolean isDebugEnabled() {
        return osgiLogger.isDebugEnabled();
    }

    /**
     * Checks if INFO level logging is enabled.
     *
     * @return true if INFO level is enabled in the underlying logger
     */
    @Override
    public boolean isInfoEnabled() {
        return osgiLogger.isInfoEnabled();
    }

    /**
     * Checks if WARN level logging is enabled.
     *
     * @return true if WARN level is enabled in the underlying logger
     */
    @Override
    public boolean isWarnEnabled() {
        return osgiLogger.isWarnEnabled();
    }

    /**
     * Checks if ERROR level logging is enabled.
     *
     * @return true if ERROR level is enabled in the underlying logger
     */
    @Override
    public boolean isErrorEnabled() {
        return osgiLogger.isErrorEnabled();
    }

    /**
     * Logs a message at TRACE level with optional arguments.
     *
     * @param msg  the message pattern
     * @param args the arguments to be substituted into the message pattern
     */
    @Override
    public void trace(final String msg, final Object... args) {
        osgiLogger.trace(msg, args);
    }

    /**
     * Logs an exception at TRACE level.
     *
     * @param cause the Throwable to log with its full stack trace
     */
    @Override
    public void trace(final Throwable cause) {
        osgiLogger.trace(ThrowableUtils.toString(cause));
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
        osgiLogger.trace(mergeMsgAndThrowable(msg, cause), args);
    }

    /**
     * Logs a message at DEBUG level with optional arguments.
     *
     * @param msg  the message pattern
     * @param args the arguments to be substituted into the message pattern
     */
    @Override
    public void debug(final String msg, final Object... args) {
        osgiLogger.debug(msg, args);
    }

    /**
     * Logs an exception at DEBUG level.
     *
     * @param cause the Throwable to log with its full stack trace
     */
    @Override
    public void debug(final Throwable cause) {
        osgiLogger.debug(ThrowableUtils.toString(cause));
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
        osgiLogger.debug(mergeMsgAndThrowable(msg, cause), args);
    }

    /**
     * Logs a message at INFO level with optional arguments.
     *
     * @param msg  the message pattern
     * @param args the arguments to be substituted into the message pattern
     */
    @Override
    public void info(final String msg, final Object... args) {
        osgiLogger.info(msg, args);
    }

    /**
     * Logs an exception at INFO level.
     *
     * @param cause the Throwable to log with its full stack trace
     */
    @Override
    public void info(final Throwable cause) {
        osgiLogger.info(ThrowableUtils.toString(cause));
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
        osgiLogger.info(mergeMsgAndThrowable(msg, cause), args);
    }

    /**
     * Logs a message at WARN level with optional arguments.
     *
     * @param msg  the message pattern
     * @param args the arguments to be substituted into the message pattern
     */
    @Override
    public void warn(final String msg, final Object... args) {
        osgiLogger.warn(msg, args);
    }

    /**
     * Logs an exception at WARN level.
     *
     * @param cause the Throwable to log with its full stack trace
     */
    @Override
    public void warn(final Throwable cause) {
        osgiLogger.warn(ThrowableUtils.toString(cause));
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
        osgiLogger.warn(mergeMsgAndThrowable(msg, cause), args);
    }

    /**
     * Logs a message at ERROR level with optional arguments.
     *
     * @param msg  the message pattern
     * @param args the arguments to be substituted into the message pattern
     */
    @Override
    public void error(final String msg, final Object... args) {
        osgiLogger.error(msg, args);
    }

    /**
     * Logs an exception at ERROR level.
     *
     * @param cause the Throwable to log with its full stack trace
     */
    @Override
    public void error(final Throwable cause) {
        osgiLogger.error(ThrowableUtils.toString(cause));
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
        osgiLogger.error(mergeMsgAndThrowable(msg, cause), args);
    }
}