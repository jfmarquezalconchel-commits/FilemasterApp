package com.filemasterapp.FilemasterApp.Services;

import com.filemasterapp.FilemasterApp.Model.File;
import com.filemasterapp.FilemasterApp.Model.FileSystem;
import com.filemasterapp.FilemasterApp.dto.FileOutDTO;
import com.filemasterapp.FilemasterApp.dto.FileSystemOutDTO;
import com.filemasterapp.FilemasterApp.repository.FileSystemRepository;
import com.filemasterapp.FilemasterApp.repository.FilesRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FileTree {

    @PersistenceContext
    private EntityManager entityManager;

    private final FilesRepository repository;

    private FileSystemService rootFs;
    private HashMap<Long,Nodo> tree = new HashMap<>();
    private HashMap<Long,FileSystem> fileSystems = new HashMap<>();


    public FileTree(FilesRepository repository) {
        this.repository = repository;
  }

    public String getTree(Long id) {

        return this.tree.get(id).toString();
    }


    public Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    @PostConstruct
    public void init() {
        try {
            if (this.fileSystems.isEmpty()) {
                com.filemasterapp.FilemasterApp.Utils.FileSystem fs = new com.filemasterapp.FilemasterApp.Utils.FileSystem(this.entityManager.unwrap(Session.class));
                for (FileSystem afs : fs.getFileSystemList()) {
                    if(afs.getId()!=3) continue;
                    this.fileSystems.put(afs.getId(), afs);
                    FileService fileServiceRepo = new FileService(this.repository);
                    Nodo node = null;
                    if(this.tree.containsKey(afs.getId())){
                        node= this.tree.get(afs.getId());
                    }else{
                        node = new Nodo("/",null,null);
                        this.tree.put(afs.getId(),node);
                    }
                    for(FileOutDTO fout:fileServiceRepo.getFilesByFileSystem(afs.getId())){
                       this.addFile(node,fout.getPath(),fout);
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private Nodo findNode(String path,Nodo nodeToFind){
        String[] partes = path.split("/");
        Nodo retorno=null;
        if(partes.length>0){
            retorno=nodeToFind;
            if(partes[0].equals(nodeToFind.getPath())){
                partes = Arrays.copyOfRange(partes, 1, partes.length);
                if(partes.length>1){
                    if(nodeToFind.getHijos().containsKey(partes[0])){
                        retorno=this.findNode(String.join("/",partes),nodeToFind.getHijos().get(partes[0]));
                    }
                }
            }
        }
        return retorno;
    }

    private Nodo addFile(Nodo raiz, String path,FileOutDTO file) {

        Nodo ref=raiz;
        Long bytes = raiz.getNodeSize();
        System.out.println(path);

        String[] partes = path.split("/");

        if(partes.length>0){
            if(raiz.getPath().equals(partes[0])) {
                raiz.setNodeSize(raiz.getNodeSize() + file.getSize());
                partes = Arrays.copyOfRange(partes, 1, partes.length);
                if(partes.length>1){
                    String nuevoPath = String.join("/",partes);
                    if(!raiz.getHijos().containsKey(partes[0])){
                        raiz.addHijo(partes[0],raiz,null);
                    }
                    ref=addFile(raiz.getHijos().get(partes[0]),nuevoPath,file);
                }
            }
        }else{
            raiz.setFileInfo(file);
        }
        return ref;
    }

}

class Nodo {

    private  FileOutDTO fileInfo;
    private  Map<String, Nodo> hijos = new HashMap<>();
    private  Nodo padre;
    private  String path;
    private  Long nodeSize=0L;

    public Nodo(String path, Nodo padre, FileOutDTO fileInfo) {
        this.path = path;
        this.padre = padre;
        this.fileInfo = fileInfo;
    }

    public Map<String, Nodo> getHijos() {
        return hijos;
    }

    public String getPath() {
        return path;
    }

    public Nodo getPadre() {
        return padre;
    }

    public FileOutDTO getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(FileOutDTO fileInfo) {
        this.fileInfo = fileInfo;
    }

    public void setHijos(Map<String, Nodo> hijos) {
        this.hijos = hijos;
    }

    public void addHijo(String path, Nodo parent,FileOutDTO fileInfo) {
        Nodo nuevo = new Nodo(path,parent,fileInfo);
        this.hijos.put(path,nuevo);
    }

    public void setPadre(Nodo padre) {
        this.padre = padre;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getNodeSize() {
        return nodeSize;
    }

    public void setNodeSize(Long nodeSize) {
        this.nodeSize = nodeSize;
    }
}