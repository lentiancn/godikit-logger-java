package com.godikit.logger.enums;

import lombok.Getter;

@Getter
public enum LoggerProviderNameEnum {
    NO_OP("No-OP", 0),
    SYSTEM("System", 1),
    SLF4J_LOGBACK("SLF4J+Logback", 100);

    private final String name;
    private final int priority;

    LoggerProviderNameEnum(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }
}
