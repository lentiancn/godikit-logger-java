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

/**
 * Logger implementation that outputs to System.out and System.err.
 *
 * <p>This implementation is used as a fallback when no other Logger provider
 * is available on the classpath. It outputs log messages to standard output
 * (INFO, DEBUG, TRACE) or standard error (WARN, ERROR).</p>
 *
 * <h2>Usage:</h2>
 * <pre>{@code
 * Logger logger = new SystemLoggerImpl(MyClass.class);
 * logger.info("Message to stdout");
 * logger.error("Message to stderr");
 * }</pre>
 *
 * @author Len (len782768@gmail.com)
 * @since 2025-11-22 09:19
 */
public class SystemLoggerImpl implements Logger {

    /**
     * The facade identifier for this logger implementation.
     */
    public static final String FACADE = "godikit";

    /**
     * The provider identifier for this logger implementation.
     */
    public static final String PROVIDER = "system";

    /**
     * Creates a system logger with the specified name.
     *
     * @param name the logger name (currently unused)
     */
    public SystemLoggerImpl(final String name) {
    }

    /**
     * Creates a system logger based on the specified class.
     *
     * @param clazz the class for the logger (currently unused)
     */
    public SystemLoggerImpl(final Class<?> clazz) {
    }

    /**
     * Creates a system logger wrapping the specified facade logger.
     *
     * @param facadeLogger the facade logger to wrap (ignored in this implementation)
     */
    public SystemLoggerImpl(final Object facadeLogger) {
    }

    /**
     * Returns the facade name identifier.
     *
     * @return the facade identifier, always "godikit"
     */
    @Override
    public String getFacadeName() {
        return FACADE;
    }

    /**
     * Returns a reference to this logger instance.
     *
     * @return this instance
     */
    @Override
    public Object getFacadeLogger() {
        return this;
    }

    /**
     * Returns the provider name identifier.
     *
     * @return the provider identifier, always "system"
     */
    @Override
    public String getProviderName() {
        return PROVIDER;
    }

    /**
     * Always returns true as all log levels are enabled in system logger.
     *
     * @return always true
     */
    @Override
    public boolean isTraceEnabled() {
        return true;
    }

    /**
     * Always returns true as all log levels are enabled in system logger.
     *
     * @return always true
     */
    @Override
    public boolean isDebugEnabled() {
        return true;
    }

    /**
     * Always returns true as all log levels are enabled in system logger.
     *
     * @return always true
     */
    @Override
    public boolean isInfoEnabled() {
        return true;
    }

    /**
     * Always returns true as all log levels are enabled in system logger.
     *
     * @return always true
     */
    @Override
    public boolean isWarnEnabled() {
        return true;
    }

    /**
     * Always returns true as all log levels are enabled in system logger.
     *
     * @return always true
     */
    @Override
    public boolean isErrorEnabled() {
        return true;
    }

    /**
     * Logs a message at TRACE level with optional arguments.
     *
     * @param msg  the message pattern
     * @param args the arguments to be substituted into the message pattern
     */
    @Override
    public void trace(final String msg, final Object... args) {
        log(null, msg, args, false);
    }

    /**
     * Logs an exception at TRACE level.
     *
     * @param cause the Throwable to log with its full stack trace
     */
    @Override
    public void trace(final Throwable cause) {
        trace(cause, null, (Object) null);
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
        log(cause, msg, args, false);
    }

    /**
     * Logs a message at DEBUG level with optional arguments.
     *
     * @param msg  the message pattern
     * @param args the arguments to be substituted into the message pattern
     */
    @Override
    public void debug(final String msg, final Object... args) {
        log(null, msg, args, false);
    }

    /**
     * Logs an exception at DEBUG level.
     *
     * @param cause the Throwable to log with its full stack trace
     */
    @Override
    public void debug(final Throwable cause) {
        debug(cause, null, (Object) null);
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
        log(cause, msg, args, false);
    }

    /**
     * Logs a message at INFO level with optional arguments.
     *
     * @param msg  the message pattern
     * @param args the arguments to be substituted into the message pattern
     */
    @Override
    public void info(final String msg, final Object... args) {
        log(null, msg, args, false);
    }

    /**
     * Logs an exception at INFO level.
     *
     * @param cause the Throwable to log with its full stack trace
     */
    @Override
    public void info(final Throwable cause) {
        info(cause, null, (Object) null);
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
        log(cause, msg, args, false);
    }

    /**
     * Logs a message at WARN level with optional arguments.
     *
     * @param msg  the message pattern
     * @param args the arguments to be substituted into the message pattern
     */
    @Override
    public void warn(final String msg, final Object... args) {
        log(null, msg, args, true);
    }

    /**
     * Logs an exception at WARN level.
     *
     * @param cause the Throwable to log with its full stack trace
     */
    @Override
    public void warn(final Throwable cause) {
        warn(cause, null, (Object) null);
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
        log(cause, msg, args, true);
    }

    /**
     * Logs a message at ERROR level with optional arguments.
     *
     * @param msg  the message pattern
     * @param args the arguments to be substituted into the message pattern
     */
    @Override
    public void error(final String msg, final Object... args) {
        log(null, msg, args, true);
    }

    /**
     * Logs an exception at ERROR level.
     *
     * @param cause the Throwable to log with its full stack trace
     */
    @Override
    public void error(final Throwable cause) {
        error(cause, null, (Object) null);
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
        log(cause, msg, args, true);
    }

    /**
     * Internal method to output log messages to System.out or System.err.
     *
     * @param cause the Throwable to log (may be null)
     * @param msg   the message pattern (may be null)
     * @param args  the arguments for message formatting (may be null)
     * @param isErr true to output to System.err, false for System.out
     */
    private void log(final Throwable cause, final String msg, final Object[] args, final boolean isErr) {
        StringBuilder sb = new StringBuilder();
        if (msg != null) {
            sb.append(formatMessage(msg, args));
        }
        if (cause != null) {
            sb.append(ThrowableUtils.toString(cause));
        }
        if (isErr) {
            System.err.println(sb);
        } else {
            System.out.println(sb);
        }
    }

    /**
     * Formats a message by replacing {} placeholders with argument values.
     *
     * @param msg  the message pattern with {} placeholders
     * @param args the arguments to substitute
     * @return the formatted message
     */
    private String formatMessage(final String msg, final Object... args) {
        if (args == null || args.length == 0) {
            return msg;
        }
        StringBuilder result = new StringBuilder(msg.length() + args.length * 10);
        int placeholderIndex = 0;
        int lastIndex = 0;
        while (placeholderIndex < args.length) {
            int next = msg.indexOf("{}", lastIndex);
            if (next == -1) {
                break;
            }
            result.append(msg, lastIndex, next);
            result.append(args[placeholderIndex++]);
            lastIndex = next + 2;
        }
        result.append(msg.substring(lastIndex));
        return result.toString();
    }
}
