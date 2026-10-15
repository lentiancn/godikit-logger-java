package com.godikit.logger.spi.provider.converter;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.CompositeConverter;
import com.godikit.logger.utils.StringUtils;

import java.util.Map;

public class TraceIdCompositeConverter extends CompositeConverter<ILoggingEvent> {

    @Override
    protected String transform(ILoggingEvent event, String in) {
        Map<String, String> mdcPropertyMap;
        try {
            mdcPropertyMap = event.getMDCPropertyMap();
        } catch (Throwable e) {
            mdcPropertyMap = null;
        }

        if (mdcPropertyMap == null) {
            return in;
        }

        String traceId = mdcPropertyMap.get("traceId");
        if (StringUtils.isBlank(traceId)) {
            return in;
        }

        return "[" + traceId + "] " + in;
    }
}
