package com.avekshaa.dto;

import lombok.Data;

@Data
public class ZipFileDownloadDto {

    private String fileName;
    private String contentType;
    private byte[] data;

    public ZipFileDownloadDto(String fileName, String contentType, byte[] data) {
        this.fileName = fileName;
        this.contentType = contentType;
        this.data = data;
    }
}

