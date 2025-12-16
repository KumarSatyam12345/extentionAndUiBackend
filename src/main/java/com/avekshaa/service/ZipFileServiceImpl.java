package com.avekshaa.service;

import com.avekshaa.dto.ZipFileDownloadDto;
import com.avekshaa.entity.ZipFileEntity;
import com.avekshaa.exceptions.BadRequestException;
import com.avekshaa.exceptions.ResourceNotFoundException;
import com.avekshaa.repository.ZipFileRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Set;


@Service
public class ZipFileServiceImpl implements ZipFileService {

    private static final Set<String> ALLOWED_ZIP_FILES = Set.of(
            "chrome-extension.zip",
            "edge-extension.zip",
            "firefox-extension.zip"
    );

    private final ZipFileRepository repository;

    public ZipFileServiceImpl(ZipFileRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    @Override
    public ZipFileDownloadDto downloadZipFile(String fileName) {

        if (!fileName.toLowerCase().endsWith(".zip")) {
            fileName = fileName + ".zip";
        }

        String finalFileName = fileName;
        ZipFileEntity entity = repository.findByFileName(fileName)
                .orElseThrow(() ->
                        new ResourceNotFoundException("ZIP file not found: " + finalFileName));

        // 🔥 LOB accessed INSIDE transaction
        return new ZipFileDownloadDto(
                entity.getFileName(),
                entity.getContentType(),
                entity.getData()
        );
    }


    @Transactional
    @Override
    public void uploadZipFile(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new BadRequestException("Uploaded file is empty");
        }

        String originalFileName = file.getOriginalFilename();

        if (originalFileName == null || !originalFileName.toLowerCase().endsWith(".zip")) {
            throw new BadRequestException("Only ZIP files are allowed");
        }
        String normalizedFileName = originalFileName.toLowerCase();

        if (!ALLOWED_ZIP_FILES.contains(normalizedFileName)) {
            throw new BadRequestException(
                    "Invalid file name. Allowed files are: chrome-extension.zip," +
                            " edge-extension.zip, firefox-extension.zip"
            );
        }

        try {
            ZipFileEntity entity = repository.findByFileName(originalFileName)
                    .orElseGet(ZipFileEntity::new);

            entity.setFileName(originalFileName);
            entity.setContentType(file.getContentType());
            entity.setData(file.getBytes());

            repository.saveAndFlush(entity);

        } catch (IOException ex) {
            throw new UncheckedIOException(
                    "Failed to read ZIP file data for file: " + originalFileName,
                    ex
            );
        }

    }

}
