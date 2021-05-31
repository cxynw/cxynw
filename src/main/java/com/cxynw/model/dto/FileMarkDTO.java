package com.cxynw.model.dto;


import com.cxynw.model.does.FileMark;

public class FileMarkDTO {

    protected final FileMark fileMark;

    public FileMarkDTO(FileMark fileMark) {
        this.fileMark = fileMark;
    }

    public boolean hasPassword(){
        return null != fileMark.getDownloadPassword();
    }

}
