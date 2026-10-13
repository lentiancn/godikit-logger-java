package com.godikit.logger.spi.facade;

import com.godikit.logger.Logger;
import com.godikit.logger.spi.provider.LoggerProvider;

/**
 * SPI contract for logging facade providers.
 *
 * <p>Implementations must provide a public no-argument constructor and
 * register in META-INF/services using this interface's fully qualified name.</p>
 */
public interface LoggerFacade {

    /**
     * Returns the unique facade name, such as "slf4j" or "log4j2".
     *
     * @return a non-blank, stable name without surrounding whitespace
     */
    String getName();

    void checkCompatibilityWith(LoggerProvider provider);

    /**
     * Creates a logger after verifying the active implementation.
     *
     * @param name     the logger name
     * @param provider the selected implementation provider
     * @return a non-null, thread-safe logger
     */
    Logger getLogger(String name, LoggerProvider provider);

    Logger getLogger(Class<?> clazz, LoggerProvider provider);
}
