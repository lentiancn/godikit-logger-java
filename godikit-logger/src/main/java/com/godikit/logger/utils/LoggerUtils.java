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
package com.godikit.logger.utils;

import com.godikit.error.utils.ThrowableUtils;
import com.godikit.logger.Logger;
import com.godikit.logger.LoggerConstants;
import com.godikit.logger.LoggerFactory;
import com.godikit.logger.impl.NoOperationLoggerImpl;
import com.godikit.logger.impl.SystemLoggerImpl;
import lombok.NoArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Utility class for Logger operations.
 *
 * <p>Provides convenient static methods for logging and SPI service loading.</p>
 *
 * @author Len (len782768@gmail.com)
 * @since 2025-11-08 16:36
 */
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class LoggerUtils {

    /**
     * Gets a Logger instance by name.
     *
     * @param name the logger name
     * @return Logger instance
     */
    public static Logger getLogger(final String name) {
        return LoggerFactory.getLogger(name);
    }

    /**
     * Gets a Logger instance by class.
     *
     * @param clazz the class for the logger
     * @return Logger instance
     */
    public static Logger getLogger(final Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    /**
     * Merges a message template and a Throwable into a single formatted string.
     *
     * <p>This utility combines a log message with its associated exception stack trace,
     * useful when the underlying logging framework does not natively support
     * both message formatting and exception logging in a single call.</p>
     *
     * @param msg   the message template, may be null
     * @param cause the Throwable to include, may be null
     * @return the merged string, or empty string if both inputs are null
     */
    public static String mergeMsgAndThrowable(final String msg, final Throwable cause) {
        return (msg != null ? msg : "") + (cause != null ? ThrowableUtils.toString(cause) : "");
    }

    /**
     * Gets a Logger instance by name, falling back to SystemLogger if no provider is available.
     *
     * @param name the logger name
     * @return Logger instance or SystemLogger as fallback
     */
    public static Logger getLoggerWithSystemIfNoProvider(final String name) {
        Logger logger = LoggerFactory.getLogger(name);

        if (logger instanceof NoOperationLoggerImpl) {
            return new SystemLoggerImpl(name);
        }

        return logger;
    }

    /**
     * Gets a Logger instance by class, falling back to SystemLogger if no provider is available.
     *
     * @param clazz the class for the logger
     * @return Logger instance or SystemLogger as fallback
     */
    public static Logger getLoggerWithSystemIfNoProvider(final Class<?> clazz) {
        Logger logger = LoggerFactory.getLogger(clazz);

        if (logger instanceof NoOperationLoggerImpl) {
            return new SystemLoggerImpl(clazz);
        }

        return logger;
    }

    /**
     * Logs a warning message with cause and arguments.
     *
     * @param clazz the class for the logger
     * @param cause the Throwable cause
     * @param msg   the message pattern
     * @param args  the arguments
     */
    public static void warn(final Class<?> clazz, final Throwable cause, final String msg, final Object... args) {
        getLogger(clazz).warn(cause, msg, args);
    }

    /**
     * Logs a warning with just the cause.
     *
     * @param clazz the class for the logger
     * @param cause the Throwable cause
     */
    public static void warn(final Class<?> clazz, final Throwable cause) {
        getLogger(clazz).warn(cause);
    }

    /**
     * Logs a warning message with arguments.
     *
     * @param clazz the class for the logger
     * @param msg   the message pattern
     * @param args  the arguments
     */
    public static void warn(final Class<?> clazz, final String msg, final Object... args) {
        getLogger(clazz).warn(msg, args);
    }

    /**
     * Loads Logger implementation classes via SPI.
     *
     * @param serviceClass the service interface
     * @return list of implementation classes
     * @throws ClassNotFoundException if class not found
     * @throws IOException            if I/O error occurs
     */
    @SuppressWarnings("unchecked")
    public static List<Class<? extends Logger>> loadLoggerImplClasses(final Class<Logger> serviceClass) throws ClassNotFoundException, IOException {
        List<Class<? extends Logger>> serviceImplClasses = new ArrayList<>();
        ClassLoader classLoader = serviceClass.getClassLoader();
        String serviceFileName = LoggerConstants.DIRECTORY_SERVICES + serviceClass.getName();

        Enumeration<URL> resources = classLoader.getResources(serviceFileName);
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            try (InputStream is = resource.openStream();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                String serviceImplClassName;

                while ((serviceImplClassName = reader.readLine()) != null) {
                    serviceImplClassName = serviceImplClassName.trim();

                    if (serviceImplClassName.isEmpty() || serviceImplClassName.startsWith("#")) {
                        continue;
                    }

                    Class<?> implClass = classLoader.loadClass(serviceImplClassName);
                    if (serviceClass.isAssignableFrom(implClass)) {
                        serviceImplClasses.add((Class<? extends Logger>) implClass);
                    }
                }
            }
        }
        return serviceImplClasses;
    }
}
