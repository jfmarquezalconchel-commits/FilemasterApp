package com.filemasterapp.FilemasterApp.Controler;

import com.filemasterapp.FilemasterApp.Services.FileService;
import com.filemasterapp.FilemasterApp.dto.FileOutDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/fsfiles/{id}")
    public List<FileOutDTO> getFileSystems(@PathVariable Long id) {
        return this.fileService.getFilesByFileSystem(id);
    }
}
