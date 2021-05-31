package com.cxynw.dao.base;

public interface BaseDao<T,ID> extends
        BaseQuery<T,ID>,
        BaseInsertDao<T>,
        BaseDelete<T,ID>,
        BaseUpdate<T,ID>{



}
