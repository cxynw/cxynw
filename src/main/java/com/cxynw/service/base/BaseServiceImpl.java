package com.cxynw.service.base;

import com.cxynw.repository.base.BaseRepository;

public class BaseServiceImpl<DOMAIN,ID,Repository extends BaseRepository>
        implements BaseService<DOMAIN,ID> {

    protected final Repository repository;

    public BaseServiceImpl(Repository repository) {
        this.repository = repository;
    }

}
