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
import com.godikit.logger.spi.facade.LoggerFacade;
import com.godikit.logger.spi.facade.impl.NoOperationLoggerFacadeImpl;
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
            List<LoggerFacade> allFacades = registry.getFacades();
            List<LoggerProvider> allProviders = registry.getProviders();

            if (allFacades.size() > 1) {
                throw new GlobalRuntimeException(
                    "Multiple facade providers found: [" + joinNames(allFacades) + "]"
                        + ". Only one facade JAR should be present in classpath.");
            }

            if (allProviders.size() > 1) {
                throw new GlobalRuntimeException(
                    "Multiple implementation providers found: [" + joinNames(allProviders) + "]"
                        + ". Only one provider JAR should be present in classpath.");
            }

            LoggerFacade facade = allFacades.isEmpty() ? new NoOperationLoggerFacadeImpl() : allFacades.get(0);
            Class<? extends LoggerFacade> facadeClass = facade.getClass();
            LoggerProvider provider = allProviders.isEmpty() ? new NoOperationLoggerProviderImpl() : allProviders.get(0);
            Class<? extends LoggerProvider> providerClass = provider.getClass();

            if (facadeClass == NoOperationLoggerFacadeImpl.class) {
                System.err.println("[GodiKit Logger] WARNING: No facade found. "
                    + "Please add a facade jar, such as 'godikit-logger-facade-slf4j'.");
            }
            if (providerClass == NoOperationLoggerProviderImpl.class) {
                System.err.println("[GodiKit Logger] WARNING: No provider found. "
                    + "Please add an impl jar, such as 'godikit-logger-provider-logback'.");
            }

            facade.checkCompatibilityWith(provider);
            provider.checkCompatibilityWith(facade);
            providers = new Providers(facade, provider);
            return providers;
        } finally {
            initializing = false;
        }
    }

    private static String joinNames(List<?> providers) {
        return providers.stream().map(p -> {
            if (p instanceof LoggerFacade) {
                return ((LoggerFacade) p).getName();
            } else if (p instanceof LoggerProvider) {
                return ((LoggerProvider) p).getName();
            }
            return p.getClass().getSimpleName();
        }).collect(Collectors.joining(", "));
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
        return LOGGER_CACHE.computeIfAbsent(name, key -> currentProviders.facade.getLogger(key, currentProviders.provider));
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
        return LOGGER_CACHE.computeIfAbsent(clazz.getName(), key -> currentProviders.facade.getLogger(clazz, currentProviders.provider));
    }

    private static final class Providers {
        private final LoggerFacade facade;
        private final LoggerProvider provider;

        private Providers(LoggerFacade facade, LoggerProvider provider) {
            this.facade = facade;
            this.provider = provider;
        }
    }
}
