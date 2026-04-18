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

    public Nodo getTree(Long id) {

        if(tree.containsKey(id)){
            return tree.get(id);
        }
        return null;
    }

    public Nodo getTreePath(Long id,String path){
        if(tree.containsKey(id)){
            return tree.get(id).findPath(path);
        }
        return null;
    }

    private String viewTree(Nodo raiz){
        StringBuilder sb = new StringBuilder();
        sb.append("|-"+raiz.getPath());

        for(FileOutDTO f:raiz.getFilesInfo()){
            if(f!=null && !f.getIs_dir()){
                sb.append("|->"+f.getFilename());
            }

        }
        for(Nodo child:raiz.getHijos().values()){
            sb.append("|-"+viewTree(child));
        }

        return sb.toString();
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
                    //REMOVER AL FINAL
                    if(afs.getId()!=19) continue;
                    this.fileSystems.put(afs.getId(), afs);
                    FileService fileServiceRepo = new FileService(this.repository);
                    Nodo node = new Nodo("/",null);
                    //REMOVER CONTADOR AL FINAL.
                    Integer counter=10000;
                    for(FileOutDTO fout:fileServiceRepo.getFilesByFileSystem(afs.getId())){
                       this.addFile(node,fout.getPath(),fout);
                       counter--;
                       if(counter==0){
                           break;
                       }
                    }

                    this.tree.put(afs.getId(),node);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private Nodo findNode(String path,Nodo nodeToFind){
        String[] partes = path.split("/");
        Nodo retorno=null;
        if(partes.length>1){
            System.out.println("partes length");
            retorno=nodeToFind;
            if(partes[1].equals(nodeToFind.getPath())){
                System.out.println("partes igualnodo");
                partes = Arrays.copyOfRange(partes, 1, partes.length);
                if(partes.length>2){
                    System.out.println("partes >2");
                    if(nodeToFind.getHijos().containsKey(partes[0])){
                        System.out.println("contiene key");
                        retorno=this.findNode(String.join("/",partes),nodeToFind.getHijos().get(partes[0]));
                    }
                }
            }
        }
        return retorno;
    }

    private Nodo addFile(Nodo raiz, String path,FileOutDTO file) {

        String[] partes = path.split("/");
        System.out.println(path+"/"+file.getFilename());

        for(int i=1;i<partes.length;i++){

             if(raiz.getHijos().containsKey(partes[i])){
                 raiz=raiz.getHijos().get(partes[i]);
                 raiz.setNodeSize(raiz.getNodeSize() + file.getSize());
             }else {
                 raiz = raiz.addHijo(partes[i], null);
                 raiz.setNodeSize(raiz.getNodeSize() + file.getSize());
             }
        }
        file.setFileSystem(null);
        raiz.addFileInfo(file);
        return raiz;
    }

}

