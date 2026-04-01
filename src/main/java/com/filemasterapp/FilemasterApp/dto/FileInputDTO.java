package com.filemasterapp.FilemasterApp.dto;

import com.filemasterapp.FilemasterApp.Model.FileSystem;
import com.filemasterapp.FilemasterApp.Utils.Utils;

import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) para representar un archivo o directorio en el sistema.
 * <p>
 * Contiene la información necesaria para transferir datos entre capas del sistema,
 * incluyendo ruta, nombre, tamaño, tipo y metadatos temporales.
 * </p>
 */
public class FileInputDTO {

    /**
     * Ruta del archivo o directorio.
     */
    private String path;

    /**
     * Nombre del archivo o directorio.
     */
    private String filename;

    /**
     * Tamaño del archivo en bytes.
     */
    private Long size;

    /**
     * Indica si es un directorio (true) o archivo (false).
     */
    private Boolean is_dir;

    /**
     * Sistema de archivos al que pertenece.
     */
    private FileSystem fileSystem;

    /**
     * Fecha y hora de la última modificación.
     */
    private LocalDateTime lastUpdate;

    /**
     * Fecha y hora de eliminación (null si no ha sido eliminado).
     */
    private LocalDateTime deletedAt;

    /**
     * Hash MD5 del archivo (solo para archivos, null para directorios).
     */
    private String md5;

    /**
     * Obtiene el hash MD5 del archivo.
     *
     * @return el hash MD5, o null si no está disponible
     */
    public String getMd5() {
        return md5;
    }

    /**
     * Establece el hash MD5 del archivo.
     *
     * @param uniqueKey la clave única utilizada para generar el hash
     */
    public void setMd5(String uniqueKey) {
        this.md5 = null;
    }

    /**
     * Obtiene la fecha y hora de la última modificación.
     *
     * @return la fecha y hora de última modificación
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Establece la fecha y hora de la última modificación.
     *
     * @param lastUpdate la fecha y hora a establecer
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Obtiene la fecha y hora de eliminación.
     *
     * @return la fecha y hora de eliminación, o null si no ha sido eliminado
     */
    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    /**
     * Establece la fecha y hora de eliminación.
     *
     * @param deletedAt la fecha y hora a establecer
     */
    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    /**
     * Constructor que inicializa el DTO con todos los campos.
     * <p>
     * Calcula automáticamente el hash MD5 basado en la combinación
     * del ID del sistema de archivos, ruta y nombre del archivo.
     * </p>
     *
     * @param fileSystem el sistema de archivos al que pertenece
     * @param path la ruta del archivo o directorio
     * @param filename el nombre del archivo o directorio
     * @param size el tamaño en bytes
     * @param is_dir indica si es un directorio
     * @param lastUpdate la fecha y hora de última modificación
     * @param deletedAt la fecha y hora de eliminación
     */
    public FileInputDTO(FileSystem fileSystem, String path, String filename, Long size, Boolean is_dir, LocalDateTime lastUpdate, LocalDateTime deletedAt) {
        this.fileSystem = fileSystem;
        this.path = path;
        this.filename = filename;
        this.size = size;
        this.is_dir = is_dir;
        this.lastUpdate = lastUpdate;
        this.deletedAt = deletedAt;
        this.md5 = Utils.md5(fileSystem.getId() + "|" + path + "|" + filename);
    }

    public FileInputDTO() {}

    /**
     * Obtiene el sistema de archivos al que pertenece.
     *
     * @return el sistema de archivos
     */
    public FileSystem getFileSystem() {
        return fileSystem;
    }

    /**
     * Establece el sistema de archivos al que pertenece.
     *
     * @param fileSystem el sistema de archivos a establecer
     */
    public void setFileSystem(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    /**
     * Obtiene la ruta del archivo o directorio.
     *
     * @return la ruta
     */
    public String getPath() {
        return path;
    }

    /**
     * Establece la ruta del archivo o directorio.
     *
     * @param path la ruta a establecer
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Obtiene el nombre del archivo o directorio.
     *
     * @return el nombre
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Establece el nombre del archivo o directorio.
     *
     * @param filename el nombre a establecer
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * Obtiene el tamaño del archivo en bytes.
     *
     * @return el tamaño en bytes
     */
    public Long getSize() {
        return size;
    }

    /**
     * Establece el tamaño del archivo en bytes.
     *
     * @param size el tamaño a establecer
     */
    public void setSize(Long size) {
        this.size = size;
    }

    /**
     * Indica si es un directorio.
     *
     * @return true si es un directorio, false si es un archivo
     */
    public Boolean getIs_dir() {
        return is_dir;
    }

    /**
     * Establece si es un directorio.
     *
     * @param is_dir true si es un directorio, false si es un archivo
     */
    public void setIs_dir(Boolean is_dir) {
        this.is_dir = is_dir;
    }

    /**
     * Devuelve una representación en cadena del objeto.
     * Formato: path|filename|size|is_dir|lastUpdate|deletedAt
     *
     * @return cadena con la información del archivo/directorio
     */
    @Override
    public String toString() {
        return this.path + "|" + this.filename + "|" + this.size + "|" + this.is_dir + "|" + this.lastUpdate + "|" + this.deletedAt;
    }
}
