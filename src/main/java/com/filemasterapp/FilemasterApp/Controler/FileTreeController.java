package com.filemasterapp.FilemasterApp.Controler;

import com.filemasterapp.FilemasterApp.Services.FileSystemService;
import com.filemasterapp.FilemasterApp.Services.FileTree;
import com.filemasterapp.FilemasterApp.Utils.FileSystem;
import com.filemasterapp.FilemasterApp.dto.FileOutDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FileTreeController {

    private final FileTree fileTreeService;

    public  FileTreeController(FileTree fileTreeService) {
        this.fileTreeService = fileTreeService;

    }

    @GetMapping("/tree/{id}")
    public String getFileTree(@PathVariable Long id) {
        return this.fileTreeService.getTree(id);
    }

}
