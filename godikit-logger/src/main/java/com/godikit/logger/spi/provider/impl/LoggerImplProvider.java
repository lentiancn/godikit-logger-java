package com.godikit.logger.spi.provider.impl;

import com.godikit.logger.spi.provider.facade.LoggerFacadeProvider;

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
     * @param facadeProvider
     * @return true if the factory belongs to this implementation
     */
    boolean supportsFactory(LoggerFacadeProvider facadeProvider);

    /**
     * Checks whether a native logger belongs to this implementation.
     *
     * @param facadeName the facade name
     * @param logger     the native logger
     * @return true if the logger is supported
     */
    boolean supportsLogger(String facadeName, Object logger);
}
