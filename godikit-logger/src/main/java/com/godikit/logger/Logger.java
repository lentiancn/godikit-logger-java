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
package com.godikit.logger;

import com.godikit.logger.spi.provider.LoggerProvider;

/**
 * Core logger interface that serves as a facade for various logging implementations.
 *
 * <p>GodiKit Logger provides a unified logging API that can delegate to different
 * logging frameworks (SLF4J, Log4j, Log4j2, etc.) based on the configured
 * implementation providers.</p>
 *
 * <h2>Supported Providers:</h2>
 * <ul>
 *   <li>slf4j + logback</li>
 *   <li>log4j + log4j</li>
 *   <li>log4j2 + log4j2</li>
 *   <li>no-op</li>
 * </ul>
 *
 * <h2>Usage:</h2>
 * <pre>{@code
 * Logger logger = LoggerFactory.getLogger(MyClass.class);
 * logger.info("Application started at {}", LocalDateTime.now());
 * }</pre>
 *
 * @author Len (len@godikit.com)
 * @since 2025-11-07 22:53
 */
public interface Logger {

    /**
     * Returns the implementation provider instance.
     *
     * @return the impl provider (e.g., LogbackLoggerProviderImpl, Log4j2LoggerImplProvider)
     */
    LoggerProvider getProvider();

    /**
     * Returns the original (native) logger instance that this logger delegates to.
     *
     * @return the native logger (e.g., org.slf4j.Logger, org.apache.logging.log4j.Logger, java.util.logging.Logger)
     */
    Object getOriginalLogger();

    /**
     * Checks if TRACE level logging is enabled.
     *
     * @return true if TRACE level is enabled
     */
    boolean isTraceEnabled();

    /**
     * Checks if DEBUG level logging is enabled.
     *
     * @return true if DEBUG level is enabled
     */
    boolean isDebugEnabled();

    /**
     * Checks if INFO level logging is enabled.
     *
     * @return true if INFO level is enabled
     */
    boolean isInfoEnabled();

    /**
     * Checks if WARN level logging is enabled.
     *
     * @return true if WARN level is enabled
     */
    boolean isWarnEnabled();

    /**
     * Checks if ERROR level logging is enabled.
     *
     * @return true if ERROR level is enabled
     */
    boolean isErrorEnabled();

    /**
     * Logs a message at TRACE level with optional arguments.
     *
     * @param msg  the message pattern
     * @param args the arguments to be substituted into the message pattern
     */
    void trace(final String msg, final Object... args);

    /**
     * Logs an exception at TRACE level.
     *
     * @param cause the exception to log (message will be extracted from cause.getMessage())
     */
    void trace(final Throwable cause);

    /**
     * Logs a message at TRACE level with an exception.
     *
     * @param cause the exception to log
     * @param msg   the message pattern
     * @param args  the arguments to be substituted into the message pattern
     */
    void trace(final Throwable cause, final String msg, final Object... args);

    /**
     * Logs a message at DEBUG level with optional arguments.
     *
     * @param msg  the message pattern
     * @param args the arguments to be substituted into the message pattern
     */
    void debug(final String msg, final Object... args);

    /**
     * Logs an exception at DEBUG level.
     *
     * @param cause the exception to log (message will be extracted from cause.getMessage())
     */
    void debug(final Throwable cause);

    /**
     * Logs a message at DEBUG level with an exception.
     *
     * @param cause the exception to log
     * @param msg   the message pattern
     * @param args  the arguments to be substituted into the message pattern
     */
    void debug(final Throwable cause, final String msg, final Object... args);

    /**
     * Logs a message at INFO level with optional arguments.
     *
     * @param msg  the message pattern
     * @param args the arguments to be substituted into the message pattern
     */
    void info(final String msg, final Object... args);

    /**
     * Logs an exception at INFO level.
     *
     * @param cause the exception to log (message will be extracted from cause.getMessage())
     */
    void info(final Throwable cause);

    /**
     * Logs a message at INFO level with an exception.
     *
     * @param cause the exception to log
     * @param msg   the message pattern
     * @param args  the arguments to be substituted into the message pattern
     */
    void info(final Throwable cause, final String msg, final Object... args);

    /**
     * Logs a message at WARN level with optional arguments.
     *
     * @param msg  the message pattern
     * @param args the arguments to be substituted into the message pattern
     */
    void warn(final String msg, final Object... args);

    /**
     * Logs an exception at WARN level.
     *
     * @param cause the exception to log (message will be extracted from cause.getMessage())
     */
    void warn(final Throwable cause);

    /**
     * Logs a message at WARN level with an exception.
     *
     * @param cause the exception to log
     * @param msg   the message pattern
     * @param args  the arguments to be substituted into the message pattern
     */
    void warn(final Throwable cause, final String msg, final Object... args);

    /**
     * Logs a message at ERROR level with optional arguments.
     *
     * @param msg  the message pattern
     * @param args the arguments to be substituted into the message pattern
     */
    void error(final String msg, final Object... args);

    /**
     * Logs an exception at ERROR level.
     *
     * @param cause the exception to log (message will be extracted from cause.getMessage())
     */
    void error(final Throwable cause);

    /**
     * Logs a message at ERROR level with an exception.
     *
     * @param cause the exception to log
     * @param msg   the message pattern
     * @param args  the arguments to be substituted into the message pattern
     */
    void error(final Throwable cause, final String msg, final Object... args);
}
