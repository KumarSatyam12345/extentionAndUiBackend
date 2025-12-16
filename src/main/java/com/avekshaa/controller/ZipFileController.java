package com.avekshaa.controller;

import com.avekshaa.dto.ZipFileDownloadDto;
import org.springframework.http.MediaType;
import com.avekshaa.service.ZipFileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/zip")
@AllArgsConstructor
public class ZipFileController {

    private final ZipFileService zipFileService;

    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<String> uploadZip(
            @RequestParam("file") MultipartFile file) {

        zipFileService.uploadZipFile(file);
        return ResponseEntity.ok("ZIP file uploaded successfully");
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<byte[]> downloadZip(@PathVariable String fileName) {

        ZipFileDownloadDto zipFile = zipFileService.downloadZipFile(fileName);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + zipFile.getFileName() + "\"")
                .contentType(MediaType.parseMediaType(zipFile.getContentType()))
                .body(zipFile.getData());
    }
}

