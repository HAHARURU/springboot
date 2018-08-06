package com.haru.mybatis.util;

import com.haru.mybatis.exception.CustomException;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * @author HARU
 * @since 2018/8/2
 *
 * 基于字节数组的MultipartFile,无临时文件，全部暂存于内存中用来解决跨服务器传输MultipartFile的问题
 */
public class BytesMultipartFile implements MultipartFile, Serializable {
    private final byte[] fileContent;
    private final Long size;
    private final String fileName;
    private final String contentType;
    private final String originalFileName;

    public BytesMultipartFile(byte[] fileContent) {
        this.fileContent = fileContent;
        this.size = (long) fileContent.length;
        this.fileName = "";
        this.contentType = "";
        this.originalFileName = "";
    }

    public BytesMultipartFile(MultipartFile file) {
        try {
            this.fileContent = file.getBytes();
        } catch (IOException e) {
            throw new CustomException("文件读取错误");
        }
        this.size = file.getSize();
        this.fileName = file.getName();
        this.contentType = file.getContentType();
        this.originalFileName = file.getOriginalFilename();
    }

    @Override
    public String getName() {
        return fileName;
    }

    @Override
    public String getOriginalFilename() {
        return originalFileName;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public boolean isEmpty() {
        return fileContent != null && fileContent.length > 0;
    }

    @Override
    public long getSize() {
        return fileContent.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return fileContent;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(fileContent);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        new FileOutputStream(dest).write(fileContent);
    }
}
