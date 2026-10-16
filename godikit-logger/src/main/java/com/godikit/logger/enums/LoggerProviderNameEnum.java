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
package com.godikit.logger.enums;

import lombok.Getter;

/**
 * Enumeration of built-in logger provider names and their selection priorities.
 *
 * <p>When multiple providers are present on the classpath, the factory selects
 * the one with the lowest priority value. A lower number means a higher
 * selection priority.</p>
 */
@Getter
public enum LoggerProviderNameEnum {

    /**
     * No-operation provider that discards all log messages. Lowest selection priority.
     */
    NO_OP("No-OP", 0),

    /**
     * System-out provider that writes to {@code System.out}/{@code System.err}.
     */
    SYSTEM("System", 1),

    /**
     * SLF4J + Logback provider. Highest selection priority among built-in providers.
     */
    SLF4J_LOGBACK("SLF4J+Logback", 100);

    /**
     * The stable, human-readable provider name returned by {@link com.godikit.logger.spi.provider.LoggerProvider#getName()}.
     */
    private final String name;

    /**
     * Selection priority; providers with smaller values are preferred when multiple are found.
     */
    private final int priority;

    /**
     * Constructs a provider name constant.
     *
     * @param name     the stable provider name
     * @param priority the selection priority (lower wins)
     */
    LoggerProviderNameEnum(final String name, final int priority) {
        this.name = name;
        this.priority = priority;
    }
}
