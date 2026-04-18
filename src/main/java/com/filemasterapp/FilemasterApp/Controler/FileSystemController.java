package com.filemasterapp.FilemasterApp.Controler;

import com.filemasterapp.FilemasterApp.Services.FileSystemService;
import com.filemasterapp.FilemasterApp.dto.FileSystemOutDTO;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FileSystemController {

    private final FileSystemService fileSystemService;

    public FileSystemController(FileSystemService fileSystemService) {
        this.fileSystemService = fileSystemService;
    }

    @GetMapping("/fs")
    public List<FileSystemOutDTO> getFileSystems() {
        return this.fileSystemService.getAll();
    }

    @GetMapping("/fs/regen")
    public void getFileSystemRegen() {
        this.fileSystemService.cleanTables();
        //this.fileSystemService.regenerateFiles();
    }

    @GetMapping("/fs/loader")
    @Async
    public void getFileSystemFileLoader() {
        this.fileSystemService.cleanFiles();
        this.fileSystemService.regenerateFiles();
    }




}
