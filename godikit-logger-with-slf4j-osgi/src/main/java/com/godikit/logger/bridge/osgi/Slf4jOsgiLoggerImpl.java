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
package com.godikit.logger.bridge.osgi;

import com.godikit.logger.Logger;
import com.godikit.logger.utils.LoggerThrowableUtils;
import org.osgi.service.log.LoggerFactory;

import java.util.Iterator;
import java.util.ServiceLoader;

import static com.godikit.logger.utils.LoggerUtils.mergeMsgAndThrowable;

/**
 * SLF4J OSGi implementation for GodiKit Logger.
 *
 * <p>This implementation uses OSGi Log Service which bridges to SLF4J
 * through osgi-over-slf4j.</p>
 *
 * <p>Usage in OSGi environment:</p>
 * <pre>{@code
 * // This module delegates to OSGi Log Service
 * // osgi-over-slf4j bridges OSGi logs to SLF4J
 * Logger logger = LoggerFactory.getLogger(MyClass.class);
 * logger.info("Message in OSGi environment");
 * }</pre>
 *
 * @author Len (len782768@gmail.com)
 * @since 2026-07-01
 */
public class Slf4jOsgiLoggerImpl implements Logger {

    public static final String FACADE = "slf4j";
    public static final String PROVIDER = "osgi";

    private static volatile LoggerFactory loggerFactory;

    private final org.osgi.service.log.Logger osgiLogger;

    public Slf4jOsgiLoggerImpl(final Class<?> clazz) {
        this.osgiLogger = getLoggerFactory().getLogger(clazz);
    }

    public Slf4jOsgiLoggerImpl(final String name) {
        this.osgiLogger = getLoggerFactory().getLogger(name);
    }

    public Slf4jOsgiLoggerImpl(final org.osgi.service.log.Logger facadeLogger) {
        osgiLogger = facadeLogger;
    }

    private static LoggerFactory getLoggerFactory() {
        if (loggerFactory == null) {
            synchronized (Slf4jOsgiLoggerImpl.class) {
                if (loggerFactory == null) {
                    ServiceLoader<LoggerFactory> loader = ServiceLoader.load(LoggerFactory.class);
                    Iterator<LoggerFactory> iterator = loader.iterator();
                    if (iterator.hasNext()) {
                        loggerFactory = iterator.next();
                    } else {
                        throw new IllegalStateException(
                                "No OSGi LoggerFactory implementation found. " +
                                        "Ensure 'osgi-over-slf4j' is on the classpath to enable OSGi logging."
                        );
                    }
                }
            }
        }
        return loggerFactory;
    }

    @Override
    public String getFacadeName() {
        return FACADE;
    }

    @Override
    public Object getFacadeLogger() {
        return osgiLogger;
    }

    @Override
    public String getProviderName() {
        return PROVIDER;
    }

    @Override
    public boolean isTraceEnabled() {
        return osgiLogger.isTraceEnabled();
    }

    @Override
    public boolean isDebugEnabled() {
        return osgiLogger.isDebugEnabled();
    }

    @Override
    public boolean isInfoEnabled() {
        return osgiLogger.isInfoEnabled();
    }

    @Override
    public boolean isWarnEnabled() {
        return osgiLogger.isWarnEnabled();
    }

    @Override
    public boolean isErrorEnabled() {
        return osgiLogger.isErrorEnabled();
    }

    @Override
    public void trace(Throwable cause, String msg, Object... args) {
        osgiLogger.trace(mergeMsgAndThrowable(msg, cause), args);
    }

    @Override
    public void trace(Throwable cause) {
        osgiLogger.trace(LoggerThrowableUtils.toString(cause));
    }

    @Override
    public void trace(String msg, Object... args) {
        osgiLogger.trace(msg, args);
    }

    @Override
    public void debug(Throwable cause, String msg, Object... args) {
        osgiLogger.debug(mergeMsgAndThrowable(msg, cause), args);
    }

    @Override
    public void debug(Throwable cause) {
        osgiLogger.debug(LoggerThrowableUtils.toString(cause));
    }

    @Override
    public void debug(String msg, Object... args) {
        osgiLogger.debug(msg, args);
    }

    @Override
    public void info(Throwable cause, String msg, Object... args) {
        osgiLogger.info(mergeMsgAndThrowable(msg, cause), args);
    }

    @Override
    public void info(Throwable cause) {
        osgiLogger.info(LoggerThrowableUtils.toString(cause));
    }

    @Override
    public void info(String msg, Object... args) {
        osgiLogger.info(msg, args);
    }

    @Override
    public void warn(Throwable cause, String msg, Object... args) {
        osgiLogger.warn(mergeMsgAndThrowable(msg, cause), args);
    }

    @Override
    public void warn(Throwable cause) {
        osgiLogger.warn(LoggerThrowableUtils.toString(cause));
    }

    @Override
    public void warn(String msg, Object... args) {
        osgiLogger.warn(msg, args);
    }

    @Override
    public void error(Throwable cause, String msg, Object... args) {
        osgiLogger.error(mergeMsgAndThrowable(msg, cause), args);
    }

    @Override
    public void error(Throwable cause) {
        osgiLogger.error(LoggerThrowableUtils.toString(cause));
    }

    @Override
    public void error(String msg, Object... args) {
        osgiLogger.error(msg, args);
    }
}