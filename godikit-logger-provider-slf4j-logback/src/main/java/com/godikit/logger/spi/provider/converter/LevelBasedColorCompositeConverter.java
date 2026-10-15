package com.godikit.logger.spi.provider.converter;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase;

import java.util.ResourceBundle;

import static ch.qos.logback.core.pattern.color.ANSIConstants.DEFAULT_FG;

public class LevelBasedColorCompositeConverter extends ForegroundCompositeConverterBase<ILoggingEvent> {

    private static final String BOLD = "1;";
    private static final String FE_PREFIX = "38;2;";

    private static String TRACE_COLOR_CODE = FE_PREFIX + "0;128;128";
    private static String DEBUG_COLOR_CODE = FE_PREFIX + "0;112;245";
    private static String INFO_COLOR_CODE = FE_PREFIX + "85;178;70";
    private static String WARN_COLOR_CODE = BOLD + FE_PREFIX + "255;142;0";
    private static String ERROR_COLOR_CODE = BOLD + FE_PREFIX + "178;34;52";

    static {
        init();
    }

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

    static String hexToAnsiColor(final boolean bold, final String hex) {
        // 1. remove #
        String hex01 = hex.startsWith("#") ? hex.substring(1) : hex;

        // 2. simple format
        if (hex01.length() == 3) {
            hex01 = String.valueOf(hex01.charAt(0)) + hex01.charAt(0) +
                hex01.charAt(1) + hex01.charAt(1) +
                hex01.charAt(2) + hex01.charAt(2);
        }

        return (bold ? BOLD : "") + FE_PREFIX + Integer.parseInt(hex01.substring(0, 2), 16) + ";" + Integer.parseInt(hex01.substring(2, 4), 16) + ";" + Integer.parseInt(hex01.substring(4, 6), 16);
    }

    @Override
    protected String getForegroundColorCode(ILoggingEvent event) {
        Level level = event.getLevel();
        switch (level.toInt()) {
            case Level.ERROR_INT:
                return ERROR_COLOR_CODE;
            case Level.WARN_INT:
                return WARN_COLOR_CODE;
            case Level.INFO_INT:
                return INFO_COLOR_CODE;
            case Level.DEBUG_INT:
                return DEBUG_COLOR_CODE;
            case Level.TRACE_INT:
                return TRACE_COLOR_CODE;
            default:
                return DEFAULT_FG;
        }
    }
}
