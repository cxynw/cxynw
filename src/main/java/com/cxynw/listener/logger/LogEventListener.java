package com.cxynw.listener.logger;

import com.cxynw.event.logger.LogEvent;
import com.cxynw.model.does.Log;
import com.cxynw.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class LogEventListener {

    private final LogService logService;

    public LogEventListener(LogService logService){
        this.logService = logService;
    }

    @EventListener
    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void onApplicationEvent(LogEvent logEvent){
        Log logToCreate = logEvent.getLogParam().convertTo();

        if(log.isDebugEnabled()){
            log.debug(logEvent.getLogParam().toString());
        }

        logService.record(logToCreate);
    }

}
