package com.avekshaa.repository;

import com.avekshaa.entity.ZipFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ZipFileRepository extends JpaRepository<ZipFileEntity, Long> {

    Optional<ZipFileEntity> findByFileName(String fileName);
}
