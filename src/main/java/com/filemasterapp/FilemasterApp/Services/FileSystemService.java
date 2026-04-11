package com.filemasterapp.FilemasterApp.Services;

import com.filemasterapp.FilemasterApp.Model.FileSystem;
import com.filemasterapp.FilemasterApp.dto.FileSystemOutDTO;
import com.filemasterapp.FilemasterApp.repository.FileSystemRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileSystemService {

    @PersistenceContext
    private EntityManager entityManager;


    private final FileSystemRepository fileSystemRepository;
    public FileSystemService(FileSystemRepository fileSystemRepository) {
        this.fileSystemRepository = fileSystemRepository;
    }

    public List<FileSystemOutDTO> getAll() {
        return this.fileSystemRepository.findAll().stream().map(this::toDto).toList();
    }

    private  FileSystemOutDTO toDto(FileSystem fileSystem) {
        return new FileSystemOutDTO(fileSystem.getId(),fileSystem.getTarget(),fileSystem.getSource(),fileSystem.getFstype(),fileSystem.getOptions(),fileSystem.getCapacity(),fileSystem.getAvailable(),fileSystem.getInuse(),fileSystem.getParent());
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
