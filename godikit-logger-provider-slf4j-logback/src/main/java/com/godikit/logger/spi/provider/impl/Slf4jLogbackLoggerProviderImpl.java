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
package com.godikit.logger.spi.provider.impl;

import com.godikit.logger.Logger;
import com.godikit.logger.enums.LoggerProviderNameEnum;
import com.godikit.logger.impl.Slf4jLogbackLoggerImpl;
import com.godikit.logger.spi.provider.LoggerProvider;
import org.slf4j.LoggerFactory;

/**
 * Provider that creates {@link com.godikit.logger.impl.Slf4jLogbackLoggerImpl} instances
 * backed by SLF4J (typically bound to Logback).
 *
 * <p>Registered via {@code META-INF/services/com.godikit.logger.spi.provider.LoggerProvider}
 * so it is discovered automatically by {@link com.godikit.logger.spi.LoggerRegistry}.</p>
 */
public final class Slf4jLogbackLoggerProviderImpl implements LoggerProvider {

    /**
     * Returns the stable name of this provider.
     *
     * @return {@link LoggerProviderNameEnum#SLF4J_LOGBACK}
     */
    @Override
    public String getName() {
        return LoggerProviderNameEnum.SLF4J_LOGBACK.getName();
    }

    /**
     * Creates an SLF4J/Logback-backed logger for the given name.
     *
     * @param name the logger name
     * @return a logger delegating to {@link org.slf4j.LoggerFactory#getLogger(String)}
     */
    @Override
    public Logger getLogger(String name) {
        return new Slf4jLogbackLoggerImpl(name, LoggerFactory.getLogger(name), this);
    }

    /**
     * Creates an SLF4J/Logback-backed logger for the given class.
     *
     * @param clazz the class whose name identifies the logger
     * @return a logger delegating to {@link org.slf4j.LoggerFactory#getLogger(Class)}
     */
    @Override
    public Logger getLogger(Class<?> clazz) {
        return new Slf4jLogbackLoggerImpl(clazz.getName(), LoggerFactory.getLogger(clazz), this);
    }
}
