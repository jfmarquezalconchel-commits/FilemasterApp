package com.filemasterapp.FilemasterApp.Services;

import com.filemasterapp.FilemasterApp.dto.FileOutDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Nodo {

    private ArrayList<FileOutDTO> fileInfo = new ArrayList<>();
    private Map<String, Nodo> hijos = new HashMap<>();
    private  String path;
    private  Long nodeSize=0L;

    public Nodo(String path,FileOutDTO fileInfo) {
        this.path = path;
        this.fileInfo.add(fileInfo);
    }

    public Nodo findPath(String path){
        String[] partes = path.split("/");
        Nodo  node = this;
        System.out.println(path);

        if(partes.length>1 && node.getPath().equals(partes[1])){
            for(Nodo child : node.hijos.values()){
                String[] partes2 = Arrays.copyOfRange(partes, 1, partes.length);
                Nodo nf = child.findPath(partes2[1]);
                if(nf!=null){
                    return nf;
                }
            }
        }

        return node;
    }

    public Map<String, Nodo> getHijos() {
        return hijos;
    }

    public String getPath() {
        return path;
    }


    public ArrayList<FileOutDTO> getFilesInfo() {
        return fileInfo;
    }

    public void addFileInfo(FileOutDTO fileInfo) {
        this.fileInfo.add(fileInfo);
    }

    public void setHijos(Map<String, Nodo> hijos) {
        this.hijos = hijos;
    }

    public Nodo addHijo(String path,FileOutDTO fileInfo) {
        Nodo nuevo = new Nodo(path,fileInfo);
        this.hijos.put(path,nuevo);
        return nuevo;
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