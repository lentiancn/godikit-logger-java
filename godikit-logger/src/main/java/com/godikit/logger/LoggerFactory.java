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

import com.godikit.error.utils.ThrowableUtils;
import com.godikit.logger.impl.NoOperationLoggerImpl;
import com.godikit.logger.utils.LoggerUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
 * @author Len (len782768@gmail.com)
 * @since 2025-11-07 22:53
 */
public final class LoggerFactory {

    private static final Map<String, Logger> LOGGER_CACHE = new ConcurrentHashMap<>();

    private static volatile String currentFacade = NoOperationLoggerImpl.FACADE;
    private static volatile String currentProvider = NoOperationLoggerImpl.PROVIDER;
    private static volatile Constructor<? extends Logger> currentConstructor;

    static {
        loadLoggerImplementation();
    }

    /**
     * Loads the first available Logger implementation via SPI.
     */
    private static void loadLoggerImplementation() {
        try {
            List<Class<? extends Logger>> loggerImplClasses = LoggerUtils.loadLoggerImplClasses(Logger.class);
            if (loggerImplClasses.isEmpty()) {
                System.err.println("[GodiKit Logger] No Logger provider found on classpath. Please add one of: " +
                        "godikit-logger-with-slf4j-log4j2, godikit-logger-with-slf4j-logback, etc.");
                return;
            }

            if (loggerImplClasses.size() > 1) {
                System.err.println("[GodiKit Logger] Multiple Logger providers detected. Using the first one: " +
                        loggerImplClasses.get(0).getName());
                return;
            }

            Class<? extends Logger> loggerImplClass = loggerImplClasses.get(0);
            currentFacade = (String) loggerImplClass.getDeclaredField("FACADE").get(null);
            currentProvider = (String) loggerImplClass.getDeclaredField("PROVIDER").get(null);

            Constructor<? extends Logger> constructor = loggerImplClass.getDeclaredConstructor(String.class);
            constructor.setAccessible(true);
            currentConstructor = constructor;
        } catch (Throwable e) {
            System.err.println("[GodiKit Logger] Failed to load Logger implementation: " + ThrowableUtils.toString(e));
        }
    }

    /**
     * Gets the current facade name.
     *
     * @return the current facade name
     */
    public static String getCurrentFacade() {
        return currentFacade;
    }

    /**
     * Gets the current provider in format "facade+provider".
     *
     * @return the current provider string (e.g., "slf4j+log4j2")
     */
    public static String getCurrentProvider() {
        return currentFacade + "+" + currentProvider;
    }

    /**
     * Gets a Logger instance by name.
     *
     * @param name the logger name
     * @return Logger instance (cached by name)
     */
    public static Logger getLogger(final String name) {
        Logger cached = LOGGER_CACHE.get(name);
        if (cached != null) {
            return cached;
        }

        Logger logger;
        if (currentConstructor == null) {
            logger = new NoOperationLoggerImpl(name);
        } else {
            try {
                logger = currentConstructor.newInstance(name);
            } catch (Throwable e) {
                System.err.println("[GodiKit Logger] Failed to create Logger: " + ThrowableUtils.toString(e));
                logger = new NoOperationLoggerImpl(name);
            }
        }

        LOGGER_CACHE.put(name, logger);
        return logger;
    }

    /**
     * Gets a Logger instance by class.
     *
     * @param clazz the class for the logger
     * @return Logger instance (cached by class name)
     */
    public static Logger getLogger(final Class<?> clazz) {
        return getLogger(clazz.getName());
    }

    /**
     * Creates a new Logger instance from an existing facade logger.
     *
     * <p>This method attempts to create a Logger implementation that wraps
     * the provided facade logger.</p>
     *
     * @param facadeLogger the facade logger instance to wrap
     * @return a new Logger instance wrapping the facade logger, or NoOperationLoggerImpl if creation fails
     */
    public static Logger getLogger(final Object facadeLogger) {
        if (facadeLogger == null) {
            return new NoOperationLoggerImpl((Object) null);
        }

        try {
            if (currentConstructor == null) {
                return new NoOperationLoggerImpl(facadeLogger);
            }

            Constructor<? extends Logger> constructor = currentConstructor.getDeclaringClass()
                    .getDeclaredConstructor(facadeLogger.getClass());
            constructor.setAccessible(true);
            return constructor.newInstance(facadeLogger);
        } catch (NoSuchMethodException e) {
            System.err.println("[GodiKit Logger] No matching constructor found for: " + facadeLogger.getClass().getName());
            return new NoOperationLoggerImpl(facadeLogger);
        } catch (InvocationTargetException | IllegalAccessException | InstantiationException e) {
            System.err.println("[GodiKit Logger] Failed to create Logger: " + ThrowableUtils.toString(e));
            return new NoOperationLoggerImpl(facadeLogger);
        }
    }
}
