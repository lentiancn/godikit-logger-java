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
package com.godikit.logger.spi.provider.tinylog;

import com.godikit.logger.Logger;
import com.godikit.logger.LoggerFactory;
import org.slf4j.MDC;
import org.tinylog.core.LogEntry;
import org.tinylog.core.LogEntryValue;
import org.tinylog.writers.Writer;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Map;

/**
 * A TinyLog Writer that bridges log entries to SLF4J 2.x (typically backed by Logback).
 *
 * <p>This writer acts as a TinyLog sink: it receives structured log entries from
 * TinyLog and forwards them to the godikit-logger facade, which in turn delegates
 * to the configured SLF4J 2.x provider.</p>
 *
 * <p>Configuration:</p>
 * <pre>
 * writer = com.godikit.logger.spi.provider.tinylog.Slf4jWriter
 * </pre>
 */
public class Slf4jWriter implements Writer {

    /**
     * Constructs the writer. TinyLog invokes this constructor via reflection,
     * passing any writer.xxx properties from the configuration file.
     *
     * @param properties configuration properties (ignored in this implementation)
     */
    public Slf4jWriter(Map<String, String> properties) {
    }

    /**
     * Declares which LogEntry fields this writer requires.
     *
     * <p>TinyLog will only populate the requested values to minimize overhead.</p>
     *
     * @return the set of required log entry values
     */
    @Override
    public Collection<LogEntryValue> getRequiredLogEntryValues() {
        return EnumSet.of(
            LogEntryValue.LEVEL,
            LogEntryValue.MESSAGE,
            LogEntryValue.EXCEPTION,
            LogEntryValue.CLASS,
            LogEntryValue.CONTEXT
        );
    }

    /**
     * Writes a log entry to SLF4J 2.x, bridging from TinyLog's internal representation.
     *
     * <p>This method:</p>
     * <ol>
     *   <li>Obtains or creates a godikit logger for the caller's class</li>
     *   <li>Propagates TinyLog context entries to SLF4J MDC</li>
     *   <li>Dispatches the log message with optional exception</li>
     *   <li>Clears MDC entries to prevent cross-request contamination</li>
     * </ol>
     *
     * @param logEntry the log entry to forward
     */
    @Override
    public void write(LogEntry logEntry) {
        String className = logEntry.getClassName();
        Logger logger = LoggerFactory.getLogger(className != null ? className : "root");

        Map<String, String> context = logEntry.getContext();
        if (context != null && !context.isEmpty()) {
            context.forEach(MDC::put);
        }

        String message = logEntry.getMessage();
        Throwable throwable = logEntry.getException();

        switch (logEntry.getLevel()) {
            case TRACE:
                logger.trace(message, throwable);
                break;
            case DEBUG:
                logger.debug(message, throwable);
                break;
            case INFO:
                logger.info(message, throwable);
                break;
            case WARN:
                logger.warn(message, throwable);
                break;
            case ERROR:
                logger.error(message, throwable);
                break;
        }

        if (context != null) {
            context.keySet().forEach(MDC::remove);
        }
    }

    @Override
    public void flush() {
    }

    @Override
    public void close() {
    }
}
