package com.cxynw.model.query;

import java.math.BigInteger;

public interface FileInfo {

    String getFileExtension();

    String getFileBasename();

    long getFileSize();

    String getSha256();

    Long getDownloadTimes();

    BigInteger getFileId();

}
