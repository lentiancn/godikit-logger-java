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

import com.godikit.logger.impl.NoOperationLoggerImpl;
import com.godikit.logger.impl.SystemLoggerImpl;

/**
 * Core logger interface that serves as a facade for various logging implementations.
 *
 * <p>GodiKit Logger provides a unified logging API that can delegate to different
 * logging frameworks (Log4j2, Logback, System.out, etc.) based on the configured
 * provider.</p>
 *
 * <h2>Usage Example:</h2>
 * <pre>{@code
 * Logger logger = LoggerFactory.getLogger(MyClass.class);
 * logger.info("Application started at {}", LocalDateTime.now());
 * }</pre>
 *
 * @author Len (len782768@gmail.com)
 * @since 2025-11-07 22:53
 */
public interface Logger {

    Logger NO_OP_LOGGER = new NoOperationLoggerImpl("");

    Logger SYSTEM_LOGGER = new SystemLoggerImpl("");

    /**
     * Returns the facade name.
     *
     * @return the facade identifier (e.g., "godikit")
     */
    String getFacadeName();

    /**
     * Returns the underlying logger instance.
     *
     * @return the native logger object (e.g., org.slf4j.Logger, org.apache.logging.log4j.Logger)
     */
    Object getFacadeLogger();

    /**
     * Returns the provider name.
     *
     * @return the provider identifier (e.g., "log4j2", "logback", "system")
     */
    String getProviderName();

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
     * @param cause the Throwable to log
     */
    void trace(final Throwable cause);

    /**
     * Logs a message at TRACE level with an exception and optional arguments.
     *
     * @param cause the Throwable to log
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
     * @param cause the Throwable to log
     */
    void debug(final Throwable cause);

    /**
     * Logs a message at DEBUG level with an exception and optional arguments.
     *
     * @param cause the Throwable to log
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
     * @param cause the Throwable to log
     */
    void info(final Throwable cause);

    /**
     * Logs a message at INFO level with an exception and optional arguments.
     *
     * @param cause the Throwable to log
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
     * @param cause the Throwable to log
     */
    void warn(final Throwable cause);

    /**
     * Logs a message at WARN level with an exception and optional arguments.
     *
     * @param cause the Throwable to log
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
     * @param cause the Throwable to log
     */
    void error(final Throwable cause);

    /**
     * Logs a message at ERROR level with an exception and optional arguments.
     *
     * @param cause the Throwable to log
     * @param msg   the message pattern
     * @param args  the arguments to be substituted into the message pattern
     */
    void error(final Throwable cause, final String msg, final Object... args);
}
