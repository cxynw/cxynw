package com.cxynw.dao.impl;

import com.cxynw.dao.FileMarkDao;
import com.cxynw.dao.base.BaseDaoImpl;
import com.cxynw.model.does.FileMark;
import com.cxynw.repository.FileMarkRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Repository
public class FileMarkDaoImpl extends BaseDaoImpl<FileMark, BigInteger> implements FileMarkDao {
    public FileMarkDaoImpl(@NotNull FileMarkRepository baseRepository) {
        super(baseRepository);
    }

    @Override
    protected BigInteger getId(FileMark fileMark) {
        return fileMark.getFileMarkId();
    }
}
