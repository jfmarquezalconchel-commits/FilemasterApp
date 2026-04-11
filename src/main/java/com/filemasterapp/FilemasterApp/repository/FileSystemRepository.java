package com.filemasterapp.FilemasterApp.repository;

import com.filemasterapp.FilemasterApp.Model.FileSystem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileSystemRepository extends JpaRepository<FileSystem, Long> {
    List<FileSystem> getFileSystemById(Long id);
    List<FileSystem> findFileSystemsByAvailable(Boolean available);
}
