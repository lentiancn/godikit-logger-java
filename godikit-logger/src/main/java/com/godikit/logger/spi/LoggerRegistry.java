package com.godikit.logger.spi;

import com.godikit.exception.GlobalRuntimeException;
import com.godikit.logger.spi.provider.LoggerProvider;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.*;

/**
 * Registry for logging implementation providers.
 *
 * <p>Providers are discovered through {@link ServiceLoader}
 * and indexed by their names. Initialization is lazy and thread-safe.
 * Provider collection is published only after discovery succeeds.</p>
 *
 * <p>Discovery uses the context class loader of the initializing thread.
 * Provider constructors must not call back into this registry.</p>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoggerRegistry {

    private static final LoggerRegistry INSTANCE = new LoggerRegistry();

    private Map<String, LoggerProvider> providers = Collections.emptyMap();

    private volatile boolean initialized;
    private boolean initializing;

    /**
     * Returns the singleton registry.
     *
     * @return the singleton instance
     */
    public static LoggerRegistry getInstance() {
        return INSTANCE;
    }

    private static void register(Map<String, LoggerProvider> providers, String name, LoggerProvider provider) {
        if (name == null || name.trim().isEmpty() || !name.equals(name.trim())) {
            throw new IllegalStateException(
                "Invalid provider name '" + name
                    + "' declared by " + provider.getClass().getName());
        }

        LoggerProvider existing = providers.putIfAbsent(name, provider);
        if (existing != null) {
            throw new IllegalStateException(
                "Duplicate provider name '" + name + "': "
                    + existing.getClass().getName() + " and "
                    + provider.getClass().getName());
        }
    }

    /**
     * Discovers implementation providers.
     *
     * <p>An empty collection is valid. Discovery errors propagate to the
     * caller, and failed initialization may be retried.</p>
     *
     * @throws IllegalStateException               if provider names are invalid or
     *                                             duplicated, or initialization is recursive
     * @throws java.util.ServiceConfigurationError if a provider cannot be loaded
     */
    public synchronized void initialize() {
        if (initialized) {
            return;
        }
        if (initializing) {
            throw new GlobalRuntimeException(
                "Recursive logger provider initialization is not allowed.");
        }

        initializing = true;
        try {
            Map<String, LoggerProvider> loadedProviders = new LinkedHashMap<>();

            ServiceLoader<LoggerProvider> loader = ServiceLoader.load(LoggerProvider.class);
            for (LoggerProvider provider : loader) {
                register(loadedProviders, provider.getName(), provider);
            }

            providers = Collections.unmodifiableMap(loadedProviders);
            initialized = true;
        } finally {
            initializing = false;
        }
    }

    /**
     * Returns a copy of all registered providers.
     *
     * @return the providers; iteration order is not a selection priority
     */
    public List<LoggerProvider> getProviders() {
        ensureInitialized();
        return new ArrayList<>(providers.values());
    }

    private void ensureInitialized() {
        if (!initialized) {
            initialize();
        }
    }
}
