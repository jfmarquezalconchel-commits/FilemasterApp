package com.filemasterapp.FilemasterApp.Controler;

import com.filemasterapp.FilemasterApp.Services.FileSystemService;
import com.filemasterapp.FilemasterApp.Services.FileTree;
import com.filemasterapp.FilemasterApp.Services.Nodo;
import com.filemasterapp.FilemasterApp.Utils.FileSystem;
import com.filemasterapp.FilemasterApp.dto.FileOutDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class FileTreeController {

    private final FileTree fileTreeService;

    public  FileTreeController(FileTree fileTreeService) {
        this.fileTreeService = fileTreeService;

    }

    @GetMapping("/tree/{id}")
    public Nodo getFileTree(@PathVariable Long id) {
        return this.fileTreeService.getTree(id);
    }

    @PostMapping("/tree/{id}")
    public Nodo getFileTree(@RequestBody Map<String, Object> datos, @PathVariable Long id) {
         return this.fileTreeService.getTreePath(id,datos.get("path").toString());
    }

}
