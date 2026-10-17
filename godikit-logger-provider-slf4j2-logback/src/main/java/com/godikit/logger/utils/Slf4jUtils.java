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

import lombok.NoArgsConstructor;
import org.slf4j.bridge.SLF4JBridgeHandler;

/**
 * Utility class for SLF4J-level operations.
 *
 * <p>This class provides helpers that operate at the SLF4J API layer,
 * independent of any specific backend (Logback, Log4j, etc.).</p>
 *
 * @author Len (len@godikit.com)
 * @since 2025-11-09 17:51
 */
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class Slf4jUtils {

    /**
     * Redirects all java.util.logging (JUL) calls to SLF4J.
     *
     * <p>This is idempotent — calling it when the bridge is already installed is a no-op.
     * The bridge works by installing {@link SLF4JBridgeHandler} as a JUL handler on the
     * root logger, intercepting all subsequent {@code java.util.logging.Logger} calls
     * and forwarding them to SLF4J.</p>
     *
     * <p>This method is automatically called by {@link com.godikit.logger.spi.provider.impl.Slf4j2LogbackLoggerProviderImpl}
     * when the SLF4J 2.x/Logback provider is loaded via ServiceLoader. It can also be
     * invoked manually in applications that do not use {@code LoggerFactory} at all
     * but still need JUL output routed to the configured SLF4J backend.</p>
     *
     * <p>Errors are swallowed silently because JUL may not be available in all
     * environments (e.g. some containers restrict {@code java.util.logging} access).
     * In such cases, the absence of the bridge is harmless — it simply means JUL
     * output goes to the default JUL handler instead of SLF4J.</p>
     */
    public static void redirectJulToSlf4j() {
        try {
            if (SLF4JBridgeHandler.isInstalled()) {
                return;
            }

            SLF4JBridgeHandler.removeHandlersForRootLogger();
            SLF4JBridgeHandler.install();
        } catch (Throwable ignored) {
            // no-op
        }
    }
}
