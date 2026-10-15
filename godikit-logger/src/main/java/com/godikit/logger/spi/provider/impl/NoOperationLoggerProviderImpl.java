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
import com.godikit.logger.impl.NoOperationLoggerImpl;
import com.godikit.logger.spi.provider.LoggerProvider;

/**
 * Built-in provider that creates {@link com.godikit.logger.impl.NoOperationLoggerImpl} instances.
 *
 * <p>Used as the automatic fallback when no external provider jar is present on
 * the classpath. All log calls are silently discarded.</p>
 */
public class NoOperationLoggerProviderImpl implements LoggerProvider {

    /**
     * Returns the stable name of this provider.
     *
     * @return {@link LoggerProviderNameEnum#NO_OP}
     */
    @Override
    public String getName() {
        return LoggerProviderNameEnum.NO_OP.getName();
    }

    /**
     * Creates a no-operation logger for the given name.
     *
     * @param name the logger name
     * @return a no-operation logger instance
     */
    @Override
    public Logger getLogger(String name) {
        return new NoOperationLoggerImpl(name);
    }

    /**
     * Creates a no-operation logger for the given class.
     *
     * @param clazz the class whose name identifies the logger
     * @return a no-operation logger instance
     */
    @Override
    public Logger getLogger(Class<?> clazz) {
        return new NoOperationLoggerImpl(clazz);
    }
}
