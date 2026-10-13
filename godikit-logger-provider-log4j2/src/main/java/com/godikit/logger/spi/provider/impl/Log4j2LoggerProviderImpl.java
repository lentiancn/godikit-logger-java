package com.godikit.logger.spi.provider.impl;

import com.godikit.logger.enums.LoggerProviderEnum;
import com.godikit.logger.spi.facade.LoggerFacade;
import com.godikit.logger.spi.provider.LoggerProvider;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Log4j2LoggerProviderImpl implements LoggerProvider {

    @Override
    public String getName() {
        return LoggerProviderEnum.LOG4J2.getName();
    }

    @Override
    public void checkCompatibilityWith(LoggerFacade facade) {
        // TODO 这里按需实现，当facade与当前provider不兼容时，抛出异常（告知用户需要引用哪个jar包）
    }
}
