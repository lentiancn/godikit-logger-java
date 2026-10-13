package com.godikit.logger.spi.provider.impl;

/**
 * SPI contract for logging implementation providers.
 *
 * <p>Implementations must provide a public no-argument constructor and
 * register in META-INF/services using this interface's fully qualified name.</p>
 */
public interface LoggerImplProvider {

    /**
     * Returns the unique implementation name, such as "logback" or "log4j2".
     *
     * @return a non-blank, stable name without surrounding whitespace
     */
    String getName();

    /**
     * Checks whether the native facade factory uses this implementation.
     * Implementations must not reconfigure the facade or its global binding.
     *
     * @param facadeName the facade name
     * @param factory    the active native factory
     * @return true if the factory belongs to this implementation
     */
    boolean supportsFactory(String facadeName, Object factory);

    /**
     * Checks whether a native logger belongs to this implementation.
     *
     * @param facadeName the facade name
     * @param logger     the native logger
     * @return true if the logger is supported
     */
    boolean supportsLogger(String facadeName, Object logger);
}
