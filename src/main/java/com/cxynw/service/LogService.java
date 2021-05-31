package com.cxynw.service;

import com.cxynw.model.does.Log;
import com.cxynw.service.base.CrudService;

import java.math.BigInteger;

public interface LogService extends CrudService<Log, BigInteger> {

    void record(Log log);

}
