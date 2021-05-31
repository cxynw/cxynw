package com.cxynw.dao.impl;

import com.cxynw.dao.LogDao;
import com.cxynw.dao.base.BaseDaoImpl;
import com.cxynw.model.does.Log;
import com.cxynw.repository.LogRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Repository
public class LogDaoImpl extends BaseDaoImpl<Log, BigInteger> implements LogDao {
    public LogDaoImpl(@NotNull LogRepository baseRepository) {
        super(baseRepository);
    }

    @Override
    protected BigInteger getId(Log log) {
        return log.getLogId();
    }
}
