package com.cxynw.service.impl;

import com.cxynw.dao.HttpRequestDao;
import com.cxynw.model.does.HttpRequest;
import com.cxynw.model.enums.LogTypeEnum;
import com.cxynw.service.HttpRequestService;
import com.cxynw.utils.LogUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
public class HttpRequestServiceImpl implements HttpRequestService {

    private final HttpRequestDao dao;
    private final LogUtils logUtils;

    public HttpRequestServiceImpl(HttpRequestDao dao,
                                  LogUtils logUtils) {
        this.dao = dao;
        this.logUtils = logUtils;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void record(HttpRequest httpRequest) {
        dao.insert(httpRequest);
    }

    @Override
    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Throwable.class)
    public void asyncRecord(HttpRequest httpRequest) {
        try {
            dao.insert(httpRequest);
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logUtils.publishEvent(this, LogTypeEnum.HTTP_REQUEST_RECORD_ERROR,e.getMessage());
        }
    }

}
