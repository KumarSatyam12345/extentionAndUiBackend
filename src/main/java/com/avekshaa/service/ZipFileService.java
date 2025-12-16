package com.avekshaa.service;

import com.avekshaa.dto.ZipFileDownloadDto;
import com.avekshaa.entity.ZipFileEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

public interface ZipFileService {

    void uploadZipFile(MultipartFile file);

    ZipFileDownloadDto downloadZipFile(String fileName);
}
