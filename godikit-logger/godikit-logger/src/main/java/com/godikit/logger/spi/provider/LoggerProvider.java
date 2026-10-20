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
package com.godikit.logger.spi.provider;

import com.godikit.logger.Logger;

/**
 * SPI contract for logging implementation providers.
 *
 * <p>Implementations must provide a public no-argument constructor and
 * register in META-INF/services using this interface's fully qualified name.</p>
 */
public interface LoggerProvider {

    /**
     * Returns the unique implementation name, such as "logback" or "log4j2".
     *
     * @return a non-blank, stable name without surrounding whitespace
     */
    String getName();

    /**
     * Creates a logger with the specified name.
     *
     * @param name the logger name
     * @return a non-null, thread-safe logger
     */
    Logger getLogger(final String name);

    /**
     * Creates a logger based on the specified class.
     *
     * @param clazz the class for the logger
     * @return a non-null, thread-safe logger
     */
    Logger getLogger(final Class<?> clazz);
}
