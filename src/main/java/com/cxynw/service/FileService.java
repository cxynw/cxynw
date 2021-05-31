package com.cxynw.service;

import com.cxynw.model.does.FileMark;
import com.cxynw.model.enums.FileTypeEnum;
import com.cxynw.model.query.FileInfo;
import com.cxynw.service.base.CrudService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.util.List;

public interface FileService extends CrudService<FileMark, BigInteger> {

    List<FileMark> uploadFiles(MultipartFile[] files, FileTypeEnum fileTypeEnum);
    FileMark uploadFiles(MultipartFile files, FileTypeEnum fileTypeEnum);

    void autoResponseFile(FileInfo fileInfo, HttpServletResponse response);

    void setDownloadHeader(FileInfo fileInfo,HttpServletResponse response);


}
