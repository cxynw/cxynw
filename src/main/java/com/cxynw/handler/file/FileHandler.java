package com.cxynw.handler.file;

import com.cxynw.model.does.User;
import com.cxynw.model.does.FileMark;
import com.cxynw.model.enums.FileTypeEnum;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

public interface FileHandler {

    UploadFile upload(@NonNull MultipartFile multipartFile, User uploader, FileTypeEnum fileTypeEnum)
            throws IOException, NoSuchAlgorithmException;

    List<UploadFile> upload(@NonNull MultipartFile[] multipartFiles, User uploader,FileTypeEnum fileTypeEnum)
            throws IOException, NoSuchAlgorithmException;

    class UploadFile{
        private final java.io.File tempLocation;
        private final String sha256;
        private final FileMark file;

        public UploadFile(java.io.File tempLocation, String sha256, FileMark fileMark) {
            this.tempLocation = tempLocation;
            this.sha256 = sha256;
            this.file = fileMark;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            UploadFile that = (UploadFile) o;
            return tempLocation.equals(that.tempLocation) && sha256.equals(that.sha256) && file.equals(that.file);
        }

        @Override
        public int hashCode() {
            return Objects.hash(tempLocation, sha256, file);
        }


        public java.io.File getTempLocation() {
            return tempLocation;
        }

        public String getSha256() {
            return sha256;
        }

        public FileMark getUserFile() {
            return file;
        }
    }

}
