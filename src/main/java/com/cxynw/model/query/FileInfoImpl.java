package com.cxynw.model.query;

import com.cxynw.model.does.FileMark;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigInteger;


@Getter
@AllArgsConstructor
public class FileInfoImpl implements FileInfo {

    private final FileMark fileMark;

    @Override
    public String getFileExtension() {
        return fileMark.getExtension();
    }

    @Override
    public String getFileBasename() {
        return fileMark.getFileBasename();
    }

    @Override
    public long getFileSize() {
        return fileMark.getFileSize();
    }

    @Override
    public String getSha256() {
        return fileMark.getSha256();
    }

    @Override
    public Long getDownloadTimes() {
        return fileMark.getDownloadTimes();
    }

    @Override
    public BigInteger getFileId() {
        return fileMark.getFileMarkId();
    }
}
