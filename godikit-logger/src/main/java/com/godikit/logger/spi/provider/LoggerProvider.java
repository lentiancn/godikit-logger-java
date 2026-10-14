package com.godikit.logger.spi.provider;

import com.godikit.logger.Logger;

/**
 * SPI contract for logging implementation providers.
 *
 * <p>Implementations must provide a public no-argument constructor and
 * register in META-INF/services using this interface's fully qualified name.</p>
 */
public interface LoggerProvider {

    /**
     * Returns the unique implementation name, such as "logback" or "log4j2".
     *
     * @return a non-blank, stable name without surrounding whitespace
     */
    String getName();

    /**
     * Creates a logger with the specified name.
     *
     * @param name the logger name
     * @return a non-null, thread-safe logger
     */
    Logger getLogger(String name);

    /**
     * Creates a logger based on the specified class.
     *
     * @param clazz the class for the logger
     * @return a non-null, thread-safe logger
     */
    Logger getLogger(Class<?> clazz);
}
