package com.godikit.logger.demo;

import com.godikit.logger.Logger;
import com.godikit.logger.LoggerFactory;
import org.slf4j.MDC;

import java.util.UUID;

public class LogWithTraceId {

    public static void main(String[] args) {
        MDC.put("traceId", UUID.randomUUID().toString());

        Logger logger = LoggerFactory.getLogger(LogWithTraceId.class);

        System.out.println(logger.getName());
        System.out.println(logger.getProvider());
        System.out.println(logger.getOriginalLogger() + " " + logger.getOriginalLogger().getClass());

        logger.trace("Hello world!");
        logger.debug("Hello world!");
        logger.info("Hello world!");
        logger.warn("Hello world!");
        logger.error("Hello world!");
    }
}
