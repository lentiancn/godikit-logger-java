package com.godikit.logger.enums;

import lombok.Getter;

@Getter
public enum LoggerImplEnum {
    NO_OP("no-op"),
    SYSTEM("system"),
    LOGBACK("logback"),
    LOG4J2("log4j2"),
    JUL("jul");

    private final String name;

    LoggerImplEnum(String name) {
        this.name = name;
    }
}
