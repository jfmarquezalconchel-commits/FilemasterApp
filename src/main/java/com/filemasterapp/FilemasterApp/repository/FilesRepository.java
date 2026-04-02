package com.filemasterapp.FilemasterApp.repository;

import com.filemasterapp.FilemasterApp.Model.File;
import com.filemasterapp.FilemasterApp.dto.FileOutDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilesRepository extends JpaRepository<File, Long> {

    List<File> findByFileSystemId(Long id);
    List<File> findByFileSystem_IdAndDeletedAtIsNull(Long id);
}
