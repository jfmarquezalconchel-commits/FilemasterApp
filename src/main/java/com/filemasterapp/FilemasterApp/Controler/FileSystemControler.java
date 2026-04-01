package com.filemasterapp.FilemasterApp.Controler;

import com.filemasterapp.FilemasterApp.Model.FileSystem;
import com.filemasterapp.FilemasterApp.Services.FileSystemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FileSystemControler {

    private final FileSystemService fileSystemService;

    public FileSystemControler(FileSystemService service) {
        this.fileSystemService = service;
    }

    @GetMapping("/fs")
    public List<FileSystem> getFileSystems() {
        return this.fileSystemService.getFileSystemList();
    }


}
