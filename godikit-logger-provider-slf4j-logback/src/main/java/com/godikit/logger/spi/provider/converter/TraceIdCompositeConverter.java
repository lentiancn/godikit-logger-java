//package com.godikit.logger.spi.provider.converter;
//
//import ch.qos.logback.classic.spi.ILoggingEvent;
//import ch.qos.logback.core.pattern.CompositeConverter;
//
//import java.util.Map;
//
//public class TraceIdCompositeConverter extends CompositeConverter<ILoggingEvent> {
//
//    @Override
//    protected String transform(ILoggingEvent event, String in) {
//        Map<String, String> mdcPropertyMap;
//        try {
//            mdcPropertyMap = event.getMDCPropertyMap();
//        } catch (Throwable e) {
//            mdcPropertyMap = null;
//        }
//
//        String traceId = (mdcPropertyMap != null) ? mdcPropertyMap.get("traceId") : null;
//        return "[" + StringUtils.defaultIfBlank(traceId, IDUtils.generate()) + "]";
//    }
//}
