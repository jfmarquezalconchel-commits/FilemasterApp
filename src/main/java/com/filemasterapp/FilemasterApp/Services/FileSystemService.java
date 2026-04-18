package com.filemasterapp.FilemasterApp.Services;

import com.filemasterapp.FilemasterApp.Model.FileSystem;
import com.filemasterapp.FilemasterApp.Utils.HibernateSessionHandler;
import com.filemasterapp.FilemasterApp.dto.FileSystemOutDTO;
import com.filemasterapp.FilemasterApp.repository.FileSystemRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import com.filemasterapp.FilemasterApp.Utils.FileSystemControler;


import java.util.ArrayList;
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

    public void regenerateFiles(){
        FileSystemControler fsc = new FileSystemControler(this.entityManager);
        fsc.FileSystemLoader();
        /*
         * Ejemplo de uso para escanear un sistema de archivos específico.
         * Descomentar para probar el escaneo.
         */
        ArrayList<com.filemasterapp.FilemasterApp.Model.FileSystem> fs = fsc.getFileSystemList();
        for (com.filemasterapp.FilemasterApp.Model.FileSystem f: fs) {
            // Buscar el sistema de archivos con ID 1
            //if (f.getId() == 1) {
            System.out.println(f.toString());
            try {
                //f.getChildren();
                fsc.scanFileSystem(f);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //}
        }

    }


    public void cleanTables(){
        FileSystemControler fileSystemControler = new FileSystemControler(this.entityManager);
        fileSystemControler.removeAllFileSystems();
    }

    public void cleanFiles(){
        FileSystemControler fileSystemControler = new FileSystemControler(this.entityManager);
        fileSystemControler.removeAllFiles();
    }


}
