package com.godikit.logger;

import com.godikit.logger.enums.LoggerFacadeEnum;
import com.godikit.logger.enums.LoggerImplEnum;

public class LoggerProviderConfig {

    public static LoggerFacadeEnum getFacade() {
        String name = System.getProperty("godikit.logger.facade");
        return name == null ? LoggerFacadeEnum.SLF4J : LoggerFacadeEnum.valueOf(name);
    }

    public static LoggerImplEnum getImpl() {
        String name = System.getProperty("godikit.logger.impl");
        return name == null ? LoggerImplEnum.LOGBACK : LoggerImplEnum.valueOf(name);
    }
}
