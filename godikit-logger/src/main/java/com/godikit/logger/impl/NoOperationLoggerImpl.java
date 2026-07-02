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

/**
 * No-operation logger implementation that discards all log messages.
 *
 * <p>This implementation is used as a fallback when no other Logger provider
 * is available on the classpath.</p>
 *
 * @author Len (len782768@gmail.com)
 * @since 2025-11-07 22:53
 */
public class NoOperationLoggerImpl implements Logger {

    /**
     * The facade identifier for this logger implementation.
     */
    public static final String FACADE = "godikit";

    /**
     * The provider identifier for this logger implementation.
     */
    public static final String PROVIDER = "no-op";

    /**
     * Creates a no-operation logger with the specified name.
     *
     * @param name the logger name (ignored in this implementation)
     */
    public NoOperationLoggerImpl(final String name) {
    }

    /**
     * Creates a no-operation logger based on the specified class.
     *
     * @param clazz the class for the logger (ignored in this implementation)
     */
    public NoOperationLoggerImpl(final Class<?> clazz) {
    }

    /**
     * Creates a no-operation logger wrapping the specified facade logger.
     *
     * @param delegateLogger the facade logger to wrap (ignored in this implementation)
     */
    public NoOperationLoggerImpl(final Object delegateLogger) {
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
     * Returns a reference to this no-operation logger instance.
     *
     * @return this instance
     */
    @Override
    public Object getFacadeLogger() {
        return null;
    }

    /**
     * Returns the provider name identifier.
     *
     * @return the provider identifier, always "no-op"
     */
    @Override
    public String getProviderName() {
        return PROVIDER;
    }

    /**
     * Always returns false as no logging is performed.
     *
     * @return always false
     */
    @Override
    public boolean isTraceEnabled() {
        return false;
    }

    /**
     * Always returns false as no logging is performed.
     *
     * @return always false
     */
    @Override
    public boolean isDebugEnabled() {
        return false;
    }

    /**
     * Always returns false as no logging is performed.
     *
     * @return always false
     */
    @Override
    public boolean isInfoEnabled() {
        return false;
    }

    /**
     * Always returns false as no logging is performed.
     *
     * @return always false
     */
    @Override
    public boolean isWarnEnabled() {
        return false;
    }

    /**
     * Always returns false as no logging is performed.
     *
     * @return always false
     */
    @Override
    public boolean isErrorEnabled() {
        return false;
    }

    /**
     * Discards the message and arguments (no-operation).
     *
     * @param msg  the message pattern (ignored)
     * @param args the arguments (ignored)
     */
    @Override
    public void trace(final String msg, final Object... args) {
    }

    /**
     * Discards the exception (no-operation).
     *
     * @param cause the Throwable to discard (ignored)
     */
    @Override
    public void trace(final Throwable cause) {
    }

    /**
     * Discards the message, exception, and arguments (no-operation).
     *
     * @param cause the Throwable to discard (ignored)
     * @param msg   the message pattern (ignored)
     * @param args  the arguments (ignored)
     */
    @Override
    public void trace(final Throwable cause, final String msg, final Object... args) {
    }

    /**
     * Discards the message and arguments (no-operation).
     *
     * @param msg  the message pattern (ignored)
     * @param args the arguments (ignored)
     */
    @Override
    public void debug(final String msg, final Object... args) {
    }

    /**
     * Discards the exception (no-operation).
     *
     * @param cause the Throwable to discard (ignored)
     */
    @Override
    public void debug(final Throwable cause) {
    }

    /**
     * Discards the message, exception, and arguments (no-operation).
     *
     * @param cause the Throwable to discard (ignored)
     * @param msg   the message pattern (ignored)
     * @param args  the arguments (ignored)
     */
    @Override
    public void debug(final Throwable cause, final String msg, final Object... args) {
    }

    /**
     * Discards the message and arguments (no-operation).
     *
     * @param msg  the message pattern (ignored)
     * @param args the arguments (ignored)
     */
    @Override
    public void info(final String msg, final Object... args) {
    }

    /**
     * Discards the exception (no-operation).
     *
     * @param cause the Throwable to discard (ignored)
     */
    @Override
    public void info(final Throwable cause) {
    }

    /**
     * Discards the message, exception, and arguments (no-operation).
     *
     * @param cause the Throwable to discard (ignored)
     * @param msg   the message pattern (ignored)
     * @param args  the arguments (ignored)
     */
    @Override
    public void info(final Throwable cause, final String msg, final Object... args) {
    }

    /**
     * Discards the message and arguments (no-operation).
     *
     * @param msg  the message pattern (ignored)
     * @param args the arguments (ignored)
     */
    @Override
    public void warn(final String msg, final Object... args) {
    }

    /**
     * Discards the exception (no-operation).
     *
     * @param cause the Throwable to discard (ignored)
     */
    @Override
    public void warn(final Throwable cause) {
    }

    /**
     * Discards the message, exception, and arguments (no-operation).
     *
     * @param cause the Throwable to discard (ignored)
     * @param msg   the message pattern (ignored)
     * @param args  the arguments (ignored)
     */
    @Override
    public void warn(final Throwable cause, final String msg, final Object... args) {
    }

    /**
     * Discards the message and arguments (no-operation).
     *
     * @param msg  the message pattern (ignored)
     * @param args the arguments (ignored)
     */
    @Override
    public void error(final String msg, final Object... args) {
    }

    /**
     * Discards the exception (no-operation).
     *
     * @param cause the Throwable to discard (ignored)
     */
    @Override
    public void error(final Throwable cause) {
    }

    /**
     * Discards the message, exception, and arguments (no-operation).
     *
     * @param cause the Throwable to discard (ignored)
     * @param msg   the message pattern (ignored)
     * @param args  the arguments (ignored)
     */
    @Override
    public void error(final Throwable cause, final String msg, final Object... args) {
    }
}
