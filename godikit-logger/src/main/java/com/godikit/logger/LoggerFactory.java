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

import com.godikit.exception.GlobalRuntimeException;
import com.godikit.logger.spi.LoggerRegistry;
import com.godikit.logger.spi.provider.LoggerProvider;
import com.godikit.logger.spi.provider.impl.NoOperationLoggerProviderImpl;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Factory for creating and managing Logger instances.
 *
 * <p>This factory uses SPI (Service Provider Interface) to discover and load
 * Logger implementations from the classpath. It supports caching of
 * Logger instances for better performance.</p>
 *
 * <h2>Usage:</h2>
 * <pre>{@code
 * Logger log = LoggerFactory.getLogger(MyClass.class);
 * }</pre>
 *
 * @author Len (len@godikit.com)
 * @since 2025-11-07 22:53
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LoggerFactory {

    private static final Map<String, Logger> LOGGER_CACHE = new ConcurrentHashMap<>();

    private static volatile Providers providers;
    private static boolean initializing;

    private static Providers getProviders() {
        Providers current = providers;
        return current != null ? current : initializeProviders();
    }

    private static synchronized Providers initializeProviders() {
        if (providers != null) {
            return providers;
        }
        if (initializing) {
            throw new GlobalRuntimeException("Recursive LoggerFactory initialization is not allowed.");
        }
        initializing = true;
        try {
            LoggerRegistry registry = LoggerRegistry.getInstance();
            List<LoggerProvider> allProviders = registry.getProviders();

            if (allProviders.size() > 1) {
                throw new GlobalRuntimeException(
                    "Multiple implementation providers found: [" + joinNames(allProviders) + "]"
                        + ". Only one provider JAR should be present in classpath.");
            }

            LoggerProvider provider = allProviders.isEmpty() ? new NoOperationLoggerProviderImpl() : allProviders.get(0);
            Class<? extends LoggerProvider> providerClass = provider.getClass();

            if (providerClass == NoOperationLoggerProviderImpl.class) {
                System.err.println("[GodiKit Logger] WARNING: No provider found. "
                    + "Please add a provider jar, such as 'godikit-logger-provider-slf4j-logback'.");
            }

            providers = new Providers(provider);
            return providers;
        } finally {
            initializing = false;
        }
    }

    private static String joinNames(List<?> providers) {
        return providers.stream().map(p -> ((LoggerProvider) p).getName())
            .collect(Collectors.joining(", "));
    }

    /**
     * Gets a Logger instance by name.
     *
     * @param name the logger name
     * @return Logger instance (cached by name)
     */
    public static Logger getLogger(final String name) {
        Objects.requireNonNull(name, "Logger name must not be null.");
        Providers currentProviders = getProviders();
        return LOGGER_CACHE.computeIfAbsent(name, key -> currentProviders.provider.getLogger(key));
    }

    /**
     * Gets a Logger instance by class.
     *
     * @param clazz the class for the logger
     * @return Logger instance (cached by class name)
     */
    public static Logger getLogger(final Class<?> clazz) {
        Objects.requireNonNull(clazz, "Logger name must not be null.");
        Providers currentProviders = getProviders();
        return LOGGER_CACHE.computeIfAbsent(clazz.getName(), key -> currentProviders.provider.getLogger(clazz));
    }

    private static final class Providers {
        private final LoggerProvider provider;

        private Providers(LoggerProvider provider) {
            this.provider = provider;
        }
    }
}
