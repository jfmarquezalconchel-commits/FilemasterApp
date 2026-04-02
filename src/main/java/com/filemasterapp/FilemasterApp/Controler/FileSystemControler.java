package com.filemasterapp.FilemasterApp.Controler;

import com.filemasterapp.FilemasterApp.Services.FileSystemService;
import com.filemasterapp.FilemasterApp.Utils.FileSystem;
import com.filemasterapp.FilemasterApp.dto.FileOutDTO;
import com.filemasterapp.FilemasterApp.dto.FileSystemOutDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FileSystemControler {

    private final FileSystemService fileSystemService;

    public FileSystemControler(FileSystemService fileSystemService) {
        this.fileSystemService = fileSystemService;
    }

    @GetMapping("/fs")
    public List<FileSystemOutDTO> getFileSystems() {
        return this.fileSystemService.getAll();
    }




}
