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

import com.godikit.logger.enums.LoggerFacadeEnum;
import com.godikit.logger.enums.LoggerProviderEnum;
import com.godikit.logger.spi.LoggerRegistry;
import com.godikit.logger.spi.facade.LoggerFacade;
import com.godikit.logger.spi.facade.impl.NoOperationLoggerFacadeImpl;
import com.godikit.logger.spi.provider.LoggerProvider;
import com.godikit.logger.spi.provider.impl.NoOperationLoggerProviderImpl;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

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
            throw new IllegalStateException("Recursive LoggerFactory initialization is not allowed.");
        }
        initializing = true;
        try {
            LoggerFacadeEnum facadeEnum = LoggerProviderConfig.getFacade();
            LoggerProviderEnum implEnum = LoggerProviderConfig.getImpl();

            LoggerRegistry registry = LoggerRegistry.getInstance();
            LoggerFacade facade = registry.getFacade(facadeEnum.getName());
            LoggerProvider provider = registry.getProvider(implEnum.getName());

            boolean facadeMissing = facade == null;
            boolean implMissing = provider == null;

            if (facadeMissing) {
                System.err.println("[GodiKit Logger] WARNING: Facade provider '" + facadeEnum.getName()
                    + "' not found. Please ensure you have added the corresponding facade jar, "
                    + "such as 'godikit-logger-facade-slf4j'.");
                facade = new NoOperationLoggerFacadeImpl();
            }
            if (implMissing) {
                System.err.println("[GodiKit Logger] WARNING: Impl provider '" + implEnum.getName()
                    + "' not found. Please ensure you have added the corresponding impl jar, "
                    + "such as 'godikit-logger-provider-impl-logback'.");
                provider = new NoOperationLoggerProviderImpl();
            }

            if (!facadeMissing && !implMissing) {
                Object factory = facade.getFacadeFactory();
                if (!provider.supportsFactory(facade)) {
                    throw new IllegalStateException("Active logging factory does not match "
                        + facadeEnum.getName() + "+" + implEnum.getName() + ": "
                        + (factory == null ? "null" : factory.getClass().getName()));
                }
            }

            providers = new Providers(facade, provider);
            return providers;
        } finally {
            initializing = false;
        }
    }

    private static final class Providers {
        private final LoggerFacade facade;
        private final LoggerProvider provider;

        private Providers(LoggerFacade facade, LoggerProvider provider) {
            this.facade = facade;
            this.provider = provider;
        }
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

//    /**
//     * Creates a new Logger instance from an existing facade logger.
//     *
//     * <p>This method attempts to create a Logger implementation that wraps
//     * the provided facade logger.</p>
//     *
//     * @param delegateLogger the facade logger instance to wrap
//     * @return a new wrapper, or a no-op logger when no providers are installed
//     * @throws IllegalArgumentException if the delegate is incompatible
//     */
//    public static Logger getLogger(final Object delegateLogger) {
//        Objects.requireNonNull(delegateLogger, "Delegate logger must not be null.");
//        Providers selected = getProviders();
//        if (selected.facade == null) {
//            return new NoOperationLoggerImpl(delegateLogger);
//        }
//        return Objects.requireNonNull(
//            selected.facade.wrapLogger(delegateLogger, selected.implementation),
//            "Facade provider returned a null logger.");
//    }
}
