package com.cxynw.repository;

import com.cxynw.model.does.FileMark;
import com.cxynw.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;

public interface FileMarkRepository extends BaseRepository<FileMark, BigInteger> {

    boolean existsBySha256(String sha256);

    @Query("update FileMark set downloadTimes = :newValue where fileMarkId = :id")
    @Modifying
    Integer updateDownloadTimesById(@Param("newValue") Long newDownloadTimes, @Param("id") BigInteger id);

    @Query("update FileMark set downloadPassword = :newValue where fileMarkId = :id")
    @Modifying
    Integer updateDownloadPasswordById(@Param("newValue") String newValue,@Param("id") BigInteger id);


}
