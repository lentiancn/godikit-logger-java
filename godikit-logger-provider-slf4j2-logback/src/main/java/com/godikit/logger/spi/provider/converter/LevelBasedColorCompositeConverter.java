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
package com.godikit.logger.spi.provider.converter;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase;

import java.util.ResourceBundle;

import static ch.qos.logback.core.pattern.color.ANSIConstants.DEFAULT_FG;

/**
 * Logback pattern converter that applies a level-specific ANSI foreground color.
 *
 * <p>Colors are configurable through the classpath resource
 * {@code gt-level-based-color.properties}. Each level supports a hex color
 * (e.g. {@code #008080}) and a bold flag. If the resource is absent or invalid,
 * built-in defaults are used.</p>
 *
 * <p>Registered in logback configuration via:
 * <pre>{@code <conversionRule conversionWord="gtLevelBasedColor"
 *   converterClass="...LevelBasedColorCompositeConverter"/>}</pre>
 */
public class LevelBasedColorCompositeConverter extends ForegroundCompositeConverterBase<ILoggingEvent> {

    /**
     * ANSI escape prefix for bold text.
     */
    private static final String BOLD = "1;";

    /**
     * ANSI escape prefix for 24-bit (truecolor) foreground: {@code 38;2;R;G;B}.
     */
    private static final String FE_PREFIX = "38;2;";

    /**
     * Default ANSI color code for TRACE level (teal).
     */
    private static String TRACE_COLOR_CODE = FE_PREFIX + "0;128;128";
    /**
     * Default ANSI color code for DEBUG level (blue).
     */
    private static String DEBUG_COLOR_CODE = FE_PREFIX + "0;112;245";
    /**
     * Default ANSI color code for INFO level (green).
     */
    private static String INFO_COLOR_CODE = FE_PREFIX + "85;178;70";
    /**
     * Default ANSI color code for WARN level (bold orange).
     */
    private static String WARN_COLOR_CODE = BOLD + FE_PREFIX + "255;142;0";
    /**
     * Default ANSI color code for ERROR level (bold red).
     */
    private static String ERROR_COLOR_CODE = BOLD + FE_PREFIX + "178;34;52";

    /** Loads optional color overrides from {@code gt-level-based-color.properties}. */
    static {
        init();
    }

    /**
     * Reads per-level color overrides from the classpath resource bundle.
     * Failures (missing bundle or malformed values) are silently ignored,
     * falling back to the built-in defaults.
     */
    private static void init() {
        ResourceBundle bundle;
        try {
            bundle = ResourceBundle.getBundle("gt-level-based-color");
        } catch (Throwable e) {
            bundle = null;
        }
        if (bundle == null) {
            return;
        }
        try {
            if (bundle.containsKey("trace.color.bold") && bundle.containsKey("trace.color")) {
                TRACE_COLOR_CODE = hexToAnsiColor(Boolean.parseBoolean(bundle.getString("trace.color.bold")), bundle.getString("trace.color"));
            }
        } catch (Throwable ignored) {
        }
        try {
            if (bundle.containsKey("debug.color.bold") && bundle.containsKey("debug.color")) {
                DEBUG_COLOR_CODE = hexToAnsiColor(Boolean.parseBoolean(bundle.getString("debug.color.bold")), bundle.getString("debug.color"));
            }
        } catch (Throwable ignored) {
        }
        try {
            if (bundle.containsKey("info.color.bold") && bundle.containsKey("info.color")) {
                INFO_COLOR_CODE = hexToAnsiColor(Boolean.parseBoolean(bundle.getString("info.color.bold")), bundle.getString("info.color"));
            }
        } catch (Throwable ignored) {
        }
        try {
            if (bundle.containsKey("warn.color.bold") && bundle.containsKey("warn.color")) {
                WARN_COLOR_CODE = hexToAnsiColor(Boolean.parseBoolean(bundle.getString("warn.color.bold")), bundle.getString("warn.color"));
            }
        } catch (Throwable ignored) {
        }
        try {
            if (bundle.containsKey("error.color.bold") && bundle.containsKey("error.color")) {
                ERROR_COLOR_CODE = hexToAnsiColor(Boolean.parseBoolean(bundle.getString("error.color.bold")), bundle.getString("error.color"));
            }
        } catch (Throwable ignored) {
        }
    }

    /**
     * Converts a hex color string to an ANSI 24-bit foreground escape sequence.
     *
     * <p>Supports both 3-digit ({@code #RGB}) and 6-digit ({@code #RRGGBB}) hex
     * notation. The leading {@code #} is optional.</p>
     *
     * @param bold whether to enable bold rendering
     * @param hex  the hex color string (e.g. {@code #008080} or {@code 080})
     * @return the ANSI escape code (without the surrounding ESC and trailing 'm')
     */
    static String hexToAnsiColor(final boolean bold, final String hex) {
        String hex01 = hex.startsWith("#") ? hex.substring(1) : hex;

        if (hex01.length() == 3) {
            hex01 = String.valueOf(hex01.charAt(0)) + hex01.charAt(0) +
                hex01.charAt(1) + hex01.charAt(1) +
                hex01.charAt(2) + hex01.charAt(2);
        }

        if (hex01.length() != 6) {
            return (bold ? BOLD : "") + DEFAULT_FG;
        }

        int r, g, b;
        try {
            r = Integer.parseInt(hex01.substring(0, 2), 16);
            g = Integer.parseInt(hex01.substring(2, 4), 16);
            b = Integer.parseInt(hex01.substring(4, 6), 16);
        } catch (NumberFormatException e) {
            return (bold ? BOLD : "") + DEFAULT_FG;
        }

        return (bold ? BOLD : "") + FE_PREFIX + r + ";" + g + ";" + b;
    }

    /**
     * Returns the ANSI color code for the given logging event based on its level.
     *
     * @param event the logging event
     * @return the ANSI escape sequence for the event's level
     */
    @Override
    protected String getForegroundColorCode(ILoggingEvent event) {
        switch (event.getLevel().toInt()) {
            case ch.qos.logback.classic.Level.TRACE_INT:
                return TRACE_COLOR_CODE;
            case ch.qos.logback.classic.Level.DEBUG_INT:
                return DEBUG_COLOR_CODE;
            case ch.qos.logback.classic.Level.INFO_INT:
                return INFO_COLOR_CODE;
            case ch.qos.logback.classic.Level.WARN_INT:
                return WARN_COLOR_CODE;
            case ch.qos.logback.classic.Level.ERROR_INT:
                return ERROR_COLOR_CODE;
            default:
                return DEFAULT_FG;
        }
    }
}
