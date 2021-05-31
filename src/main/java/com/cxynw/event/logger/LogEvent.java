package com.cxynw.event.logger;

import com.cxynw.model.param.LogParam;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class LogEvent extends ApplicationEvent {

    private final LogParam logParam;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public LogEvent(Object source,LogParam logParam) {
        super(source);
        this.logParam = logParam;
    }
}
