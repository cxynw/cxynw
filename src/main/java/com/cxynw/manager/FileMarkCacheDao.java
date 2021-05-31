package com.cxynw.manager;

import com.cxynw.model.does.FileMark;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.math.BigInteger;
import java.util.Optional;

public interface FileMarkCacheDao {

    @Cacheable(cacheNames = "FileMark::Id",key = "#root.args[0]",unless = "#result == null")
    Optional<FileMark> findById(BigInteger id);

    @CachePut(cacheNames = "FileMark::Id",key = "#result.fileMarkId",unless = "#result == null")
    Optional<FileMark> insert(FileMark fileMark);
}
