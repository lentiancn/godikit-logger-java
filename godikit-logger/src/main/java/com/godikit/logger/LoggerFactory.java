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
import com.godikit.logger.enums.LoggerProviderNameEnum;
import com.godikit.logger.spi.LoggerRegistry;
import com.godikit.logger.spi.provider.LoggerProvider;
import com.godikit.logger.spi.provider.impl.NoOperationLoggerProviderImpl;
import com.godikit.logger.utils.StringUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
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

    /**
     * Cache of {@link Logger} instances keyed by logger name.
     */
    private static final Map<String, Logger> LOGGER_CACHE = new ConcurrentHashMap<>();

    /**
     * The resolved provider; lazily initialized under synchronization.
     */
    private static volatile LoggerProvider provider;

    /**
     * Guards against recursive initialization triggered from within a provider constructor.
     */
    private static boolean initializing;

    /**
     * Returns the resolved provider, performing lazy initialization if needed.
     *
     * @return the active {@link LoggerProvider}
     */
    private static LoggerProvider getProvider() {
        LoggerProvider current = provider;
        return (current != null) ? current : initializeProvider();
    }

    /**
     * Resolves and caches the active provider.
     *
     * <p>Thread-safe via synchronization. Recursive initialization (e.g. a
     * provider constructor that calls back into this factory) is rejected.</p>
     *
     * @return the resolved provider
     */
    private static synchronized LoggerProvider initializeProvider() {
        if (provider != null) {
            return provider;
        }
        if (initializing) {
            throw new GlobalRuntimeException("Recursive LoggerFactory initialization is not allowed.");
        }
        initializing = true;
        try {
            LoggerRegistry registry = LoggerRegistry.getInstance();
            List<LoggerProvider> allProviders = registry.getProviders();

            provider = selectProvider(allProviders);
            return provider;
        } finally {
            initializing = false;
        }
    }

    /**
     * Selects a single provider from the discovered list.
     *
     * <p>Selection rules:
     * <ul>
     *   <li>No provider &rarr; fall back to {@link NoOperationLoggerProviderImpl}.</li>
     *   <li>Exactly one provider &rarr; use it directly.</li>
     *   <li>Multiple providers &rarr; pick the one with the lowest priority value
     *       (see {@link LoggerProviderNameEnum}) and emit a warning.</li>
     * </ul>
     *
     * @param allProviders the providers discovered via SPI
     * @return the selected provider
     */
    private static LoggerProvider selectProvider(List<LoggerProvider> allProviders) {
        if (allProviders.isEmpty()) {
            System.err.println("[GodiKit Logger] WARNING: No provider found. " + "Please add a provider jar, such as 'godikit-logger-provider-slf4j-logback'.");
            return new NoOperationLoggerProviderImpl();
        }
        if (allProviders.size() == 1) {
            return allProviders.get(0);
        }

        LoggerProvider selectedProvider = allProviders.stream()
            .min(Comparator.comparingInt(p -> getPriority(p.getName()))).get();

        System.err.println("[GodiKit Logger] WARNING: Multiple providers found: [" + joinNames(allProviders) + "]. "
            + "Using the recommended one: [" + selectedProvider.getName() + "]. "
            + "Having multiple provider jars may cause dependency conflicts. "
            + "Please keep only one provider jar in your classpath.");
        return selectedProvider;
    }

    /**
     * Looks up the selection priority for the given provider name.
     *
     * @param name the provider name
     * @return the priority value, or {@link Integer#MAX_VALUE} if the name is unknown
     */
    private static int getPriority(String name) {
        for (LoggerProviderNameEnum e : LoggerProviderNameEnum.values()) {
            if (e.getName().equals(name)) {
                return e.getPriority();
            }
        }
        return Integer.MAX_VALUE;
    }

    /**
     * Joins provider names into a comma-separated string for warning messages.
     *
     * @param providers the providers
     * @return comma-separated provider names
     */
    private static String joinNames(List<LoggerProvider> providers) {
        return providers.stream().map(LoggerProvider::getName)
            .collect(java.util.stream.Collectors.joining(", "));
    }

    /**
     * Returns a logger for the given name, creating and caching it on first access.
     *
     * @param name the logger name; if blank, the factory class name is used
     * @return a cached {@link Logger} instance
     */
    public static Logger getLogger(final String name) {
        return LOGGER_CACHE.computeIfAbsent(StringUtils.defaultIfBlank(name, Logger.class.getName()), key -> getProvider().getLogger(key));
    }

    /**
     * Returns a logger for the given class, creating and caching it on first access.
     *
     * @param clazz the class whose name identifies the logger; if {@code null}, the factory class is used
     * @return a cached {@link Logger} instance
     */
    public static Logger getLogger(final Class<?> clazz) {
        Class<?> target = (clazz != null) ? clazz : Logger.class;
        return LOGGER_CACHE.computeIfAbsent(target.getName(), key -> getProvider().getLogger(target));
    }
}
