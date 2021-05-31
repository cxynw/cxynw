package com.cxynw.model.param;

import com.cxynw.model.dto.base.InputConverter;
import com.cxynw.model.does.Log;
import com.cxynw.model.enums.LogTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LogParam implements InputConverter<Log> {

    private LogTypeEnum type;
    private String content;

    @Override
    public Log convertTo() {
        Log log = new Log();
        log.setLogType(type.getValue());
        log.setLogContent(content);
        return log;
    }
}
