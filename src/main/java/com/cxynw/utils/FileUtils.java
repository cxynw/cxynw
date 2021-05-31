package com.cxynw.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public class FileUtils {

    public static String getFilename(@NonNull String filename){
        return StringUtils.getFilename(filename).trim();
    }

    public static String getBasename(@NonNull String filename){
        int index = filename.lastIndexOf(".");
        if(index == -1){
            index = filename.length();
        }
        int folderIndex = filename.lastIndexOf("/");
        if (folderIndex > index) {
            return "";
        }
        return filename.substring(folderIndex+1,index).trim();
    }

    public static Optional<String> getExtension(@NonNull String filename){
        String extension = StringUtils.getFilenameExtension(filename);
        return Optional.ofNullable(extension.trim());
    }

    public static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static UploadFile sha256(InputStream inputStream) throws NoSuchAlgorithmException, IOException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        File tempFile = File.createTempFile(
                String.valueOf(System.currentTimeMillis())
                ,"zhengdianboke");

        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(tempFile));
        byte[] data = new byte[1024*5];
        int n;
        while((n=inputStream.read(data))!=-1){
            digest.update(data,0,n);
            outputStream.write(data,0,n);
        }
        outputStream.flush();
        outputStream.close();

        return new UploadFile(tempFile,bytesToHex(digest.digest()));
    }

    @Slf4j
    @AllArgsConstructor
    @Getter
    public static class UploadFile{
        private File tempLocation;
        private String sha512;
    }

}
