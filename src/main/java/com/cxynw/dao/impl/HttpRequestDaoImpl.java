package com.cxynw.dao.impl;

import com.cxynw.dao.HttpRequestDao;
import com.cxynw.dao.base.BaseDaoImpl;
import com.cxynw.model.does.HttpRequest;
import com.cxynw.repository.HttpRequestRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Repository
public class HttpRequestDaoImpl extends BaseDaoImpl<HttpRequest, BigInteger> implements HttpRequestDao {
    public HttpRequestDaoImpl(@NotNull HttpRequestRepository baseRepository) {
        super(baseRepository);
    }

    @Override
    protected BigInteger getId(HttpRequest httpRequest) {
        return httpRequest.getHttpRequestId();
    }
}
