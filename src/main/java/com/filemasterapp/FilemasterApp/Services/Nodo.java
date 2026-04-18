package com.filemasterapp.FilemasterApp.Services;

import com.filemasterapp.FilemasterApp.dto.FileOutDTO;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

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
        List<String> partes = Arrays.asList(path.split("/"));
        if(partes.get(0).equals("")){
            partes.set(0,"/");
        }
        if(partes.size()>0 && this.getPath().equals(partes.get(0))){
            for(Nodo child : this.hijos.values()){
                if(partes.size()>1 && partes.get(1).equals(child.getPath())){
                    return child.findPath(partes.subList(1,partes.size()).stream().map(String::valueOf).collect(Collectors.joining("/")));
                }
            }
        }
        return this;
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