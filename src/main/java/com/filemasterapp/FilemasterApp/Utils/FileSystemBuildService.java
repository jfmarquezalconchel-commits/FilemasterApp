package com.filemasterapp.FilemasterApp.Utils;

import com.filemasterapp.FilemasterApp.Model.File;
import com.filemasterapp.FilemasterApp.Model.FileSystem;
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

public class FileSystemBuildService {

    FileSystemDAO fileSystemDAO;
    FileDAO fileDAO;
    Session session;
    private static String[] validFs = {"ext4", "vfat", "cifs"};

    private static String[] donotVisit={"/proc","/sys","/dev","/run"};

    public List<FileSystem> fileSystemtoFoundCache;

    public FileSystemBuildService(Session session) {
        this.fileSystemDAO =new FileSystemDAO(session);
        this.fileDAO =new FileDAO(session);
        this.session = session;


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

    public List<FileSystem> getFileSystemList() {
        return new ArrayList<>(/*this.fileSystemtoFoundCache != null ? this.fileSystemtoFoundCache :*/ this.fileSystemDAO.getFileSystemListAll());
    }


    public void FileSystemLoader() {
        HashMap<String, CapacityMapper> capaitity = FileSystemHandler.FileSystemCapacity();
        List<FileSystemMapper> lista = FileSystemHandler.FileSystemReader();

        Transaction transaction = session.beginTransaction();
        for (FileSystemMapper fs : lista) {
            if (!Arrays.asList(validFs).contains(fs.fstype)) continue;
            CapacityMapper capacidad = capaitity.get(fs.target);
            FileSystemInputDTO input=new FileSystemInputDTO(fs.target,fs.source,fs.fstype,fs.options,capacidad != null ? capacidad.getSize() : 0, capacidad != null ? capacidad.getAvail() : 0, capacidad != null ? capacidad.getUsed() : 0,null);
            FileSystem main = new FileSystem(input);

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
                    //main.addChild(child);
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

            /*fs.getChildren().forEach(child -> {
                paths.add(child.getTarget());
            });*/

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

                                fileList.add(new File(fileObject));
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


}
