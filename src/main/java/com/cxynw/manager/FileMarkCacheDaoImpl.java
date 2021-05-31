package com.cxynw.manager;

import com.cxynw.dao.FileMarkDao;
import com.cxynw.model.does.FileMark;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public class FileMarkCacheDaoImpl implements FileMarkCacheDao {

    private final FileMarkDao fileMarkDao;

    public FileMarkCacheDaoImpl(FileMarkDao fileMarkDao) {
        this.fileMarkDao = fileMarkDao;
    }

    @Override
    public Optional<FileMark> findById(BigInteger id) {
        return fileMarkDao.findById(id);
    }

    @Override
    public Optional<FileMark> insert(FileMark fileMark) {
        return fileMarkDao.insert(fileMark);
    }

}
