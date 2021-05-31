package com.cxynw.utils;

import com.cxynw.event.logger.LogEvent;
import com.cxynw.model.enums.LogTypeEnum;
import com.cxynw.model.param.LogParam;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class LogUtils {

    private final ApplicationEventPublisher applicationEventPublisher;

    public LogUtils(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishEvent(Object source,LogParam logParam){
        LogEvent logEvent = new LogEvent(source,logParam);
        this.applicationEventPublisher.publishEvent(logEvent);
    }

    public void publishEvent(Object source, LogTypeEnum type,String format,Object... args){
        LogParam logParam = new LogParam(type,String.format(format,args));
        publishEvent(source,logParam);
    }

}
