package com.godikit.logger.enums;

import lombok.Getter;

@Getter
public enum LoggerProviderEnum {
    NO_OP("no-op"),
    SYSTEM("system"),
    LOGBACK("logback"),
    LOG4J2("log4j2"),
    JUL("jul");

    private final String name;

    LoggerProviderEnum(String name) {
        this.name = name;
    }
}
