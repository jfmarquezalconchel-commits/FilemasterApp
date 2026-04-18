package com.filemasterapp.FilemasterApp.Utils;

import com.filemasterapp.FilemasterApp.Model.File;
import com.filemasterapp.FilemasterApp.Model.FileSystem;
import com.filemasterapp.FilemasterApp.Utils.BlkIdMapper;
import com.filemasterapp.FilemasterApp.Utils.CapacityMapper;
import com.filemasterapp.FilemasterApp.Utils.FileSystemHandler;
import com.filemasterapp.FilemasterApp.Utils.FileSystemMapper;
import com.filemasterapp.FilemasterApp.dao.FileDAO;
import com.filemasterapp.FilemasterApp.dao.FileSystemDAO;
import com.filemasterapp.FilemasterApp.dto.FileInputDTO;
import com.filemasterapp.FilemasterApp.dto.FileSystemInputDTO;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;




import com.filemasterapp.FilemasterApp.Utils.FileSystemHandler.*;
import org.springframework.stereotype.Service;

@Service
public class FileSystemControler {
    @PersistenceContext
    private EntityManager entityManager;

    FileSystemDAO fileSystemDAO;
    FileDAO fileDAO;
    Session session;
    private static String[] validFs = {"ext4", "vfat", "cifs"};

    private static String[] donotVisit={"/proc","/sys","/dev","/run"};

    public List<FileSystem> fileSystemtoFoundCache;

    public FileSystemControler(Session session) {
        this.fileSystemDAO =new FileSystemDAO(session);
        this.fileDAO =new FileDAO(session);
        this.session = session;
    }

    public FileSystemControler() {}

    public FileSystemControler(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.session = entityManager.unwrap(Session.class);
        this.fileSystemDAO =new FileSystemDAO(this.session);
        this.fileDAO =new FileDAO(this.session);

    }

    private FileSystem getFromCurrentFilesystems(FileSystem toFind) {
        FileSystem fileSystemFound=null;
        if(this.fileSystemtoFoundCache==null || this.fileSystemtoFoundCache.size()==0) {
            this.fileSystemtoFoundCache = this.fileSystemDAO.getFileSystemListAll();
        }
        for(FileSystem fileSystem:this.fileSystemtoFoundCache) {
            if(fileSystem.equals(toFind)){
                fileSystemFound=fileSystem;
                break;
            }
        }
        return fileSystemFound;
    }

    public ArrayList<FileSystem> getFileSystemList() {
        return new ArrayList<>(this.fileSystemtoFoundCache != null ? this.fileSystemtoFoundCache : this.fileSystemDAO.getFileSystemListAll());
    }


    public void FileSystemLoader() {
        HashMap<String, CapacityMapper> capaitity = com.filemasterapp.FilemasterApp.Utils.FileSystemHandler.FileSystemCapacity();
        List<FileSystemMapper> lista = FileSystemHandler.FileSystemReader();

        Transaction transaction = session.beginTransaction();
        for (FileSystemMapper fs : lista) {
            if (!Arrays.asList(validFs).contains(fs.fstype)) continue;
            CapacityMapper capacidad = capaitity.get(fs.target);
            FileSystemInputDTO input=new FileSystemInputDTO(fs.target,fs.source,fs.fstype,fs.options,capacidad != null ? capacidad.getSize() : 0, capacidad != null ? capacidad.getAvail() : 0, capacidad != null ? capacidad.getUsed() : 0,null);
            FileSystem main = new FileSystem(input);
            System.out.println(fs.toString());
            if(fs.source.indexOf("dev") != -1) {
                BlkIdMapper blkinfo = FileSystemHandler.FileSystemBlkInfo(fs.source);
                main.setBlkInfo(blkinfo.getUuid(), blkinfo.getPartuuid(), blkinfo.getPartlabel());
            }

            FileSystem alreadyFs=getFromCurrentFilesystems(main);
            if(alreadyFs!=null) {
                alreadyFs.updateFields(main);
                main =  alreadyFs;
            }

            for (FileSystemMapper fs2 : fs.children) {
                CapacityMapper capacidadChild = capaitity.get(fs2.target);
                if (Arrays.asList(validFs).contains(fs2.fstype)) {
                    FileSystemInputDTO input2=new FileSystemInputDTO(fs2.target,fs2.source,fs2.fstype,fs2.options,capacidadChild != null ? capacidadChild.getSize() : 0, capacidadChild != null ? capacidadChild.getAvail() : 0, capacidadChild != null ? capacidadChild.getUsed() : 0,main);
                    FileSystem child =new FileSystem(input2);
                    if(fs2.source.indexOf("dev") != -1) {
                        BlkIdMapper blkinfoChild = FileSystemHandler.FileSystemBlkInfo(fs2.source);
                        child.setBlkInfo(blkinfoChild.getUuid(), blkinfoChild.getPartuuid(), blkinfoChild.getPartlabel());
                    }

                    FileSystem alreadyFsChild=getFromCurrentFilesystems(child);
                    if(alreadyFsChild!=null) {
                        alreadyFsChild.updateFields(child);
                        child =  alreadyFsChild;
                    }
                    main.addChild(child);
                }
            }
            this.session.persist(main);
        }
        transaction.commit();
    }

    public void scanFileSystem(FileSystem fs) throws IOException {
        ArrayList<String> paths = new ArrayList<>();
        List<File> localCache = this.fileDAO.getFileList(fs);
        String target = fs.getTarget();
        if (fs.getEnabled()) {
            Path root = Paths.get(target);
            ArrayList<File> fileList = new ArrayList<>();

            fs.getChildren().forEach(child -> {
                paths.add(child.getTarget());
            });

            Files.walkFileTree(root, new SimpleFileVisitor<>() {

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    if (Files.isReadable(file)) {
                       boolean pathEncontrado = false;
                        if (fs.getParent() == null) {
                            for (String str : paths) {
                                if (file.startsWith(str)) {
                                    pathEncontrado = true;
                                    break;
                                }
                            }
                        }
                        if (!pathEncontrado) {
                            System.out.println(file);
                            java.io.File f = new java.io.File(String.valueOf(file));
                            FileInputDTO fileObject = new FileInputDTO(fs,
                                    f.getPath().replace(f.getName(), "").replace(fs.getTarget(), ""),
                                    f.getName(),
                                    f.length(),
                                    f.isDirectory(), null, null);


                            /*File currentFile=localCache.stream().filter(flocal-> flocal.getMd5() == fileObject.getMd5() ).findFirst().orElse(null);
                            if(currentFile==null) {*/
                                fileList.add(new File(fileObject));
                            /*}else{
                                currentFile.setSize(fileObject.getSize());
                                currentFile.setLastUpdate(fileObject.getLastUpdate());
                                fileList.add(currentFile);
                            }*/

                        }
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {
                    // 🔥 Aquí ignoras archivos/directorios sin permisos
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                    for(String toSkip:donotVisit){
                        if(dir.startsWith(toSkip)){
                            return FileVisitResult.SKIP_SUBTREE;
                        }
                    }

                    for(String toSkip:paths){
                        if(dir.startsWith(toSkip)){
                            return FileVisitResult.SKIP_SUBTREE;
                        }
                    }

                    if (Files.isReadable(dir)) {
                        return FileVisitResult.CONTINUE;
                    }
                    // ❌ Si no puedes leer el directorio, lo saltas entero
                    return FileVisitResult.SKIP_SUBTREE;
                }

            });

            if(!fileList.isEmpty()){
                Transaction transaction = session.beginTransaction();
                this.fileDAO.deleteAllRecords(fs);
                    for (File file : fileList) {
                        try {
                            session.persist(file);
                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }
                    }
                transaction.commit();
            }
        }

    }


    public Integer removeFiles(FileSystem fs)  {
        return session.createQuery("delete from File where fileSystem = :fsId", File.class).setParameter("fsId", fs.getId()).executeUpdate();
    }

    public Integer removeAllFiles()  {
        Integer result = 0;
        Transaction transaction = session.beginTransaction();
        result = session.createQuery("delete from File ").executeUpdate();
        transaction.commit();
        return result;
    }


    public void removeAllFileSystems() {
        Session session = this.entityManager.unwrap(Session.class);

        List<FileSystem> filesystems =  session.createQuery("from FileSystem  f order by f.parent desc").list();
        try {
            for(FileSystem fs: filesystems){
                Transaction transaction = session.beginTransaction();
                int filas=session.createQuery("delete from File f where f.fileSystem = :fsId").setParameter("fsId", fs).executeUpdate();
                transaction.commit();
                transaction = session.beginTransaction();
                session.createQuery("delete from FileSystem f where f.id = :fsId").setParameter("fsId", fs.getId()).executeUpdate();
                transaction.commit();
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            
        }
    }

}
