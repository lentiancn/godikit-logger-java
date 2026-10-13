package com.godikit.logger.spi;

import com.godikit.logger.spi.provider.facade.LoggerFacadeProvider;
import com.godikit.logger.spi.provider.impl.LoggerImplProvider;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ServiceLoader;

/**
 * Registry for logging facade and implementation providers.
 *
 * <p>Providers are discovered independently through {@link ServiceLoader}
 * and indexed by their names. Initialization is lazy and thread-safe.
 * Both provider collections are published only after discovery succeeds.</p>
 *
 * <p>Discovery uses the context class loader of the initializing thread.
 * Provider constructors must not call back into this registry.</p>
 *
 * <p>This registry does not select providers, configure logging backends,
 * or establish bridges between facades and implementations.</p>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoggerRegistry {

    private static final LoggerRegistry INSTANCE = new LoggerRegistry();

    private Map<String, LoggerFacadeProvider> facadeProviders = Collections.emptyMap();
    private Map<String, LoggerImplProvider> implProviders = Collections.emptyMap();

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

    /**
     * Discovers facade and implementation providers independently.
     *
     * <p>An empty collection is valid. Discovery errors propagate to the
     * caller, and failed initialization may be retried.</p>
     *
     * @throws IllegalStateException if provider names are invalid or
     *                               duplicated, or initialization is recursive
     * @throws java.util.ServiceConfigurationError if a provider cannot be loaded
     */
    public synchronized void initialize() {
        if (initialized) {
            return;
        }
        if (initializing) {
            throw new IllegalStateException(
                "Recursive logger provider initialization is not allowed.");
        }

        initializing = true;
        try {
            Map<String, LoggerFacadeProvider> loadedFacades = new LinkedHashMap<>();
            Map<String, LoggerImplProvider> loadedImpls = new LinkedHashMap<>();

            ServiceLoader<LoggerFacadeProvider> facadeLoader =
                ServiceLoader.load(LoggerFacadeProvider.class);
            for (LoggerFacadeProvider provider : facadeLoader) {
                register(loadedFacades, provider.getName(), provider, "facade");
            }

            ServiceLoader<LoggerImplProvider> implLoader =
                ServiceLoader.load(LoggerImplProvider.class);
            for (LoggerImplProvider provider : implLoader) {
                register(loadedImpls, provider.getName(), provider, "implementation");
            }

            facadeProviders = Collections.unmodifiableMap(loadedFacades);
            implProviders = Collections.unmodifiableMap(loadedImpls);
            initialized = true;
        } finally {
            initializing = false;
        }
    }

    /**
     * Returns a facade provider by its exact, case-sensitive name.
     *
     * @param name the facade name
     * @return the provider, or null if not registered
     */
    public LoggerFacadeProvider getFacadeProvider(String name) {
        Objects.requireNonNull(name, "Facade name must not be null.");
        ensureInitialized();
        return facadeProviders.get(name);
    }

    /**
     * Returns an implementation provider by its exact, case-sensitive name.
     *
     * @param name the implementation name
     * @return the provider, or null if not registered
     */
    public LoggerImplProvider getImplProvider(String name) {
        Objects.requireNonNull(name, "Implementation name must not be null.");
        ensureInitialized();
        return implProviders.get(name);
    }

    /**
     * Returns a copy of all registered facade providers.
     *
     * @return the facade providers; iteration order is not a selection priority
     */
    public List<LoggerFacadeProvider> getFacadeProviders() {
        ensureInitialized();
        return new ArrayList<>(facadeProviders.values());
    }

    /**
     * Returns a copy of all registered implementation providers.
     *
     * @return the implementation providers; iteration order is not a selection priority
     */
    public List<LoggerImplProvider> getImplProviders() {
        ensureInitialized();
        return new ArrayList<>(implProviders.values());
    }

    private void ensureInitialized() {
        if (!initialized) {
            initialize();
        }
    }

    private static <T> void register(
        Map<String, T> providers, String name, T provider, String category) {

        if (name == null || name.trim().isEmpty() || !name.equals(name.trim())) {
            throw new IllegalStateException(
                "Invalid " + category + " provider name '" + name
                    + "' declared by " + provider.getClass().getName());
        }

        T existing = providers.putIfAbsent(name, provider);
        if (existing != null) {
            throw new IllegalStateException(
                "Duplicate " + category + " provider name '" + name + "': "
                    + existing.getClass().getName() + " and "
                    + provider.getClass().getName());
        }
    }
}
