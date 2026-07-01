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
import org.slf4j.LoggerFactory;

/**
 * SLF4J JBoss Logging implementation for GodiKit Logger.
 *
 * <p>This implementation uses slf4j-jboss-logging as the underlying logging framework.</p>
 *
 * @author Len (len782768@gmail.com)
 * @since 2025-11-09
 */
public class Slf4jJBossLoggerImpl implements Logger {

    public static final String FACADE = "slf4j";
    public static final String PROVIDER = "jboss";

    private final org.slf4j.Logger logger;

    public Slf4jJBossLoggerImpl(Class<?> clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
    }

    public Slf4jJBossLoggerImpl(String name) {
        this.logger = LoggerFactory.getLogger(name);
    }

    @Override
    public String getFacadeName() {
        return FACADE;
    }

    @Override
    public Object getFacadeLogger() {
        return logger;
    }

    @Override
    public String getProviderName() {
        return PROVIDER;
    }

    @Override
    public boolean isTraceEnabled() {
        return logger.isTraceEnabled();
    }

    @Override
    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    @Override
    public boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

    @Override
    public boolean isWarnEnabled() {
        return logger.isWarnEnabled();
    }

    @Override
    public boolean isErrorEnabled() {
        return logger.isErrorEnabled();
    }

    @Override
    public void trace(Throwable cause, String msg, Object... args) {
        logger.trace(msg, args, cause);
    }

    @Override
    public void trace(Throwable cause) {
        logger.trace(cause.getMessage(), cause);
    }

    @Override
    public void trace(String msg, Object... args) {
        logger.trace(msg, args);
    }

    @Override
    public void debug(Throwable cause, String msg, Object... args) {
        logger.debug(msg, args, cause);
    }

    @Override
    public void debug(Throwable cause) {
        logger.debug(cause.getMessage(), cause);
    }

    @Override
    public void debug(String msg, Object... args) {
        logger.debug(msg, args);
    }

    @Override
    public void info(Throwable cause, String msg, Object... args) {
        logger.info(msg, args, cause);
    }

    @Override
    public void info(Throwable cause) {
        logger.info(cause.getMessage(), cause);
    }

    @Override
    public void info(String msg, Object... args) {
        logger.info(msg, args);
    }

    @Override
    public void warn(Throwable cause, String msg, Object... args) {
        logger.warn(msg, args, cause);
    }

    @Override
    public void warn(Throwable cause) {
        logger.warn(cause.getMessage(), cause);
    }

    @Override
    public void warn(String msg, Object... args) {
        logger.warn(msg, args);
    }

    @Override
    public void error(Throwable cause, String msg, Object... args) {
        logger.error(msg, args, cause);
    }

    @Override
    public void error(Throwable cause) {
        logger.error(cause.getMessage(), cause);
    }

    @Override
    public void error(String msg, Object... args) {
        logger.error(msg, args);
    }
}