package com.godikit.logger.enums;

import lombok.Getter;

@Getter
public enum LoggerFacadeEnum {
    NO_OP("no-op"),
    SYSTEM("system"),
    SLF4J("slf4j"),
    LOG4J2("log4j2"),
    JUL("jul");

    private final String name;

    LoggerFacadeEnum(String name) {
        this.name = name;
    }
}
