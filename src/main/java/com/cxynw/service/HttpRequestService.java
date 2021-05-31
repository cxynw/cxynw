package com.cxynw.service;

import com.cxynw.model.does.HttpRequest;

import java.sql.SQLException;

public interface HttpRequestService {

    /**
     *
     * @param httpRequest
     * @throws SQLException 极有可能发生数据过长异常
     */
    void record(HttpRequest httpRequest) throws SQLException;

    void asyncRecord(HttpRequest httpRequest);

}
