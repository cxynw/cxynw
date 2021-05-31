package com.cxynw.service.impl;

import com.cxynw.dao.LogDao;
import com.cxynw.model.does.Log;
import com.cxynw.repository.LogRepository;
import com.cxynw.service.LogService;
import com.cxynw.service.base.AbstractCrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;

@Slf4j
@Service
public class LogServiceImpl extends AbstractCrudService<Log, BigInteger> implements LogService {

    private final LogRepository repository;
    private final LogDao logDao;

    protected LogServiceImpl(LogRepository repository,
                             LogDao logDao) {
        super(repository);
        this.repository = repository;
        this.logDao = logDao;
    }

    @Override
    @Transactional
    public void record(Log log) {
        logDao.insert(log);
    }
}
