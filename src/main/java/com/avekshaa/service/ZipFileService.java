package com.avekshaa.service;

import com.avekshaa.dto.ZipFileDownloadDto;
import org.springframework.web.multipart.MultipartFile;

public interface ZipFileService {

    void uploadZipFile(MultipartFile file);

    ZipFileDownloadDto downloadZipFile(String fileName);
}
