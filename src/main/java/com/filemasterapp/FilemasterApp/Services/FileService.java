package com.filemasterapp.FilemasterApp.Services;

import com.filemasterapp.FilemasterApp.Model.File;
import com.filemasterapp.FilemasterApp.Model.FileSystem;
import com.filemasterapp.FilemasterApp.dto.FileOutDTO;
import com.filemasterapp.FilemasterApp.repository.FilesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {
    private FilesRepository filesRepository;
    public FileService(FilesRepository filesRepository) {
        this.filesRepository = filesRepository;
    }

    public List<FileOutDTO> getFilesByFileSystem(Long id) {
        //return this.filesRepository.findByFileSystem_IdAndDeletedAtIsNull(id).stream().map(this::toDto).toList();
        return this.filesRepository.findByFileSystemId(id).stream().map(this::toDto).toList();
    }

    private  FileOutDTO toDto(File file) {
        return new FileOutDTO(file.getId(),file.getFileSystem(),file.getPath(),file.getFilename(),file.getSize(),file.getIs_dir(),file.getLastUpdate(),file.getDeletedAt());
    }

   /* public String getFileSystemTree(Long id){
        return this.filesRepository.
    }*/

}
