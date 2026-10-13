package com.godikit.logger.spi.provider.facade;

import com.godikit.logger.Logger;
import com.godikit.logger.spi.provider.impl.LoggerImplProvider;

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
     * @param name           the logger name
     * @param implProvider the selected implementation provider
     * @return a non-null, thread-safe logger
     */
    Logger getLogger(String name, LoggerImplProvider implProvider);

    Logger getLogger(Class<?> clazz, LoggerImplProvider implProvider);

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
