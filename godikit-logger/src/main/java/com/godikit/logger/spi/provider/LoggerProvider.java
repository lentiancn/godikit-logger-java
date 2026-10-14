package com.godikit.logger.spi.provider;

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
}
