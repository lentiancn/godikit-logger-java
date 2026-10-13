package com.godikit.logger.spi.provider;

import com.godikit.logger.Logger;

/**
 * SPI contract for logging facade providers.
 *
 * <p>Implementations must provide a public no-argument constructor and
 * register in META-INF/services using this interface's fully qualified name.</p>
 */
public interface LoggerFacadeProvider {

    /**
     * Returns the unique facade name, such as "slf4j" or "log4j2".
     *
     * @return a non-blank, stable name without surrounding whitespace
     */
    String getName();

    /**
     * Returns the native factory used by this facade.
     * Called after service discovery, never from a provider constructor.
     *
     * @return the active native factory
     */
    Object getFacadeFactory();

    /**
     * Creates a logger after verifying the active implementation.
     *
     * @param name the logger name
     * @param implementation the selected implementation provider
     * @return a non-null, thread-safe logger
     */
    Logger createLogger(String name, LoggerImplProvider implementation);

    /**
     * Wraps a native logger after verifying its implementation.
     *
     * @param delegate the native logger
     * @param implementation the selected implementation provider
     * @return a non-null logger
     * @throws IllegalArgumentException if the native logger is incompatible
     */
    Logger wrapLogger(Object delegate, LoggerImplProvider implementation);
}
