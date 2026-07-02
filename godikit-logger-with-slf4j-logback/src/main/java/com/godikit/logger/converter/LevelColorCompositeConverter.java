package com.godikit.logger.converter;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase;

import java.util.ResourceBundle;

import static ch.qos.logback.core.pattern.color.ANSIConstants.DEFAULT_FG;

public class LevelColorCompositeConverter extends ForegroundCompositeConverterBase<ILoggingEvent> {

    private static final String BOLD = "1;";
    private static final String FE_PREFIX = "38;2;";

    private static String TRACE_COLOR_CODE = FE_PREFIX + "0;128;128";
    private static String DEBUG_COLOR_CODE = FE_PREFIX + "0;112;245";
    private static String INFO_COLOR_CODE = FE_PREFIX + "85;178;70";
    private static String WARN_COLOR_CODE = BOLD + FE_PREFIX + "255;142;0";
    private static String ERROR_COLOR_CODE = BOLD + FE_PREFIX + "178;34;52";

    static {
        ResourceBundle bundle = null;
        try {
            bundle = ResourceBundle.getBundle("godikit-logger-level-color");
        } catch (Throwable ignored) {
        }
        try {
            if (bundle.containsKey("level.color.TRACE.bold") && bundle.containsKey("level.color.TRACE")) {
                TRACE_COLOR_CODE = hexToAnsiColor(Boolean.parseBoolean(bundle.getString("level.color.TRACE.bold")), bundle.getString("level.color.TRACE"));
            }
        } catch (Throwable ignored) {
        }
        try {
            if (bundle.containsKey("level.color.DEBUG.bold") && bundle.containsKey("level.color.DEBUG")) {
                DEBUG_COLOR_CODE = hexToAnsiColor(Boolean.parseBoolean(bundle.getString("level.color.DEBUG.bold")), bundle.getString("level.color.DEBUG"));
            }
        } catch (Throwable ignored) {
        }
        try {
            if (bundle.containsKey("level.color.INFO.bold") && bundle.containsKey("level.color.INFO")) {
                INFO_COLOR_CODE = hexToAnsiColor(Boolean.parseBoolean(bundle.getString("level.color.INFO.bold")), bundle.getString("level.color.INFO"));
            }
        } catch (Throwable ignored) {
        }
        try {
            if (bundle.containsKey("level.color.WARN.bold") && bundle.containsKey("level.color.WARN")) {
                WARN_COLOR_CODE = hexToAnsiColor(Boolean.parseBoolean(bundle.getString("level.color.WARN.bold")), bundle.getString("level.color.WARN"));
            }
        } catch (Throwable ignored) {
        }
        try {
            if (bundle.containsKey("level.color.ERROR.bold") && bundle.containsKey("level.color.ERROR")) {
                ERROR_COLOR_CODE = hexToAnsiColor(Boolean.parseBoolean(bundle.getString("level.color.ERROR.bold")), bundle.getString("level.color.ERROR"));
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
