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

import com.godikit.logger.impl.NoOperationLoggerImpl;
import com.godikit.logger.utils.LoggerThrowableUtils;
import com.godikit.logger.utils.LoggerUtils;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Factory for creating and managing Logger instances.
 *
 * <p>This factory uses SPI (Service Provider Interface) to discover and load
 * Logger implementations from the classpath. It also supports caching of
 * Logger instances for better performance.</p>
 *
 * <h2>Usage:</h2>
 * <pre>{@code
 * Logger log = LoggerFactory.getLogger(MyClass.class);
 * LoggerFactory.setProvider("godikit", "logback");
 * }</pre>
 *
 * @author Len (len782768@gmail.com)
 * @since 2025-11-07 22:53
 */
public final class LoggerFactory {

    private static final String PROVIDER_KEY_FORMAT = "%s+%s";
    private static final Map<String, Class<? extends Logger>> LOGGER_IMPL_MAP = new ConcurrentHashMap<>();
    private static volatile String currentFacade = NoOperationLoggerImpl.FACADE;
    private static volatile String currentProvider = NoOperationLoggerImpl.PROVIDER;

    static {
        loadLoggerImplementations();
    }

    private static void loadLoggerImplementations() {
        try {
            int providerCount = 0;
            List<Class<? extends Logger>> loggerImplClasses = LoggerUtils.loadLoggerImplClasses(Logger.class);
            for (Class<? extends Logger> loggerImplClass : loggerImplClasses) {
                String facade = (String) loggerImplClass.getDeclaredField("FACADE").get(null);
                String provider = (String) loggerImplClass.getDeclaredField("PROVIDER").get(null);
                String key = String.format(PROVIDER_KEY_FORMAT, facade, provider);
                LOGGER_IMPL_MAP.put(key, loggerImplClass);
                if (currentFacade.equals(NoOperationLoggerImpl.FACADE)) {
                    currentFacade = facade;
                    currentProvider = provider;
                }
                providerCount++;
            }
            if (providerCount == 0) {
                System.err.println("你没有安装任何日志提供器。请引入其中1个");
            } else if (providerCount > 1) {
                System.err.println("你导入了多个日志提供器。只能使用其中1个，避免日志框架冲突");
            }
        } catch (Throwable e) {
            System.err.println("[GodiKit Logger] Failed to load Logger implementations: " + LoggerThrowableUtils.toString(e));
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
     * @return the current provider string
     */
    public static String getCurrentProvider() {
        return String.format(PROVIDER_KEY_FORMAT, currentFacade, currentProvider);
    }

    public static Logger getLogger(final String name) {
        Class<? extends Logger> loggerImplClass = LOGGER_IMPL_MAP.get(getCurrentProvider());
        if (loggerImplClass == null) {
            return new NoOperationLoggerImpl(name);
        }
        try {
            Constructor<? extends Logger> constructor = loggerImplClass.getDeclaredConstructor(String.class);
            constructor.setAccessible(true);
            return constructor.newInstance(name);
        } catch (Throwable e) {
            System.err.println("[GodiKit Logger] Failed to create Logger: " + LoggerThrowableUtils.toString(e));
            return new NoOperationLoggerImpl(name);
        }
    }

    public static Logger getLogger(final Class clazz) {
        Class<? extends Logger> loggerImplClass = LOGGER_IMPL_MAP.get(getCurrentProvider());
        if (loggerImplClass == null) {
            return new NoOperationLoggerImpl(clazz);
        }
        try {
            Constructor<? extends Logger> constructor = loggerImplClass.getDeclaredConstructor(Class.class);
            constructor.setAccessible(true);
            return constructor.newInstance(clazz);
        } catch (Throwable e) {
            System.err.println("[GodiKit Logger] Failed to create Logger: " + LoggerThrowableUtils.toString(e));
            return new NoOperationLoggerImpl(clazz);
        }
    }

    public static Logger getLogger(final Object facadeLogger) {
        Class<? extends Logger> loggerImplClass = LOGGER_IMPL_MAP.get(getCurrentProvider());
        if (loggerImplClass == null) {
            return new NoOperationLoggerImpl(facadeLogger);
        }
        try {
            Constructor<? extends Logger> constructor = loggerImplClass.getDeclaredConstructor(facadeLogger.getClass());
            constructor.setAccessible(true);
            return constructor.newInstance(facadeLogger);
        } catch (Throwable e) {
            System.err.println("[GodiKit Logger] Failed to create Logger: " + LoggerThrowableUtils.toString(e));
            return new NoOperationLoggerImpl(facadeLogger);
        }
    }
}
