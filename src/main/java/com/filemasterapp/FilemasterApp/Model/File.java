package com.filemasterapp.FilemasterApp.Model;

import jakarta.persistence.*;
import com.filemasterapp.FilemasterApp.dto.FileInputDTO;
import com.filemasterapp.FilemasterApp.dto.FileOutDTO;

import java.time.LocalDateTime;

/**
 * Entidad que representa un archivo o directorio dentro de un sistema de archivos.
 * <p>
 * Esta clase mapea la tabla 'file' de la base de datos y contiene información
 * sobre archivos y directorios, incluyendo ruta, nombre, tamaño, tipo,
 * fecha de actualización y estado de eliminación.
 * </p>
 */
@Entity
@Table(name = "file" /*, uniqueConstraints = @UniqueConstraint(columnNames = {"md5"})*/)
public class File {

    /**
     * Identificador único del archivo/directorio.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    /**
     * Sistema de archivos al que pertenece este archivo/directorio.
     */
    @ManyToOne
    @JoinColumn(name = "filesystem_id", nullable = false, insertable = true, updatable = true)
    private FileSystem fileSystem;

    /**
     * Ruta completa del archivo/directorio.
     */
    @Column(name = "path", length = 1024)
    private String path;

    /**
     * Nombre del archivo/directorio.
     */
    @Column(name = "filename", length = 256)
    private String filename;

    /**
     * Tamaño del archivo en bytes.
     */
    @Column(name = "size")
    private Long size;

    /**
     * Indica si es un directorio (true) o un archivo (false).
     */
    @Column(name = "is_dir")
    private Boolean is_dir;

    /**
     * Fecha y hora de la última actualización.
     */
    @Column(name = "last_update", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime lastUpdate;

    /**
     * Fecha y hora de eliminación (soft delete).
     */
    @Column(name = "deleted_at", nullable = true, columnDefinition = "TIMESTAMP DEFAULT null")
    private LocalDateTime deletedAt;

    /**
     * Hash MD5 del archivo para verificación de integridad.
     */
    @Column(name = "md5", length = 64)
    private String md5;

    /**
     * Se ejecuta antes de insertar un nuevo registro.
     * <p>
     * Actualiza la fecha de última modificación con la hora actual.
     * </p>
     */
    @PrePersist
    protected void onCreate() {
        lastUpdate = LocalDateTime.now();
    }

    /**
     * Se ejecuta antes de actualizar un registro existente.
     * <p>
     * Actualiza la fecha de última modificación con la hora actual.
     * </p>
     */
    @PreUpdate
    protected void onUpdate() {
        lastUpdate = LocalDateTime.now();
    }

    /**
     * Constructor que inicializa el objeto a partir de un FileOutDTO.
     * <p>
     * Copia todos los campos del DTO al objeto entidad.
     * </p>
     *
     * @param out el FileOutDTO con los datos del archivo/directorio
     */
    File(FileOutDTO out) {
        this.id = out.getId();
        this.path = out.getPath();
        this.filename = out.getFilename();
        this.size = out.getSize();
        this.is_dir = out.getIs_dir();
        this.lastUpdate = out.getLastUpdate();
        this.deletedAt = out.getDeletedAt();
        this.fileSystem = out.getFileSystem();
        this.md5 = out.getMd5();
    }

    /**
     * Constructor que inicializa el objeto a partir de un FileInputDTO.
     * <p>
     * Copia los campos del DTO al objeto entidad.
     * </p>
     *
     * @param input el FileInputDTO con los datos del archivo/directorio
     */
    public File(FileInputDTO input) {
        this.path = input.getPath();
        this.filename = input.getFilename();
        this.size = input.getSize();
        this.is_dir = input.getIs_dir();
        this.lastUpdate = input.getLastUpdate();
        this.deletedAt = input.getDeletedAt();
        this.fileSystem = input.getFileSystem();
        this.md5 = input.getMd5();
    }

    /**
     * Constructor por defecto (vacío).
     * <p>
     * Necesario para Hibernate.
     * </p>
     */
    File() {

    }

    /**
     * Obtiene el nombre del archivo/directorio.
     *
     * @return el nombre del archivo/directorio
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Establece el nombre del archivo/directorio.
     *
     * @param filename el nombre a establecer
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * Obtiene el identificador único del archivo/directorio.
     *
     * @return el identificador
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador único del archivo/directorio.
     *
     * @param id el identificador a establecer
     */
    public void setId(Long id) {
        this.id = id;
    }

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
     * Obtiene la ruta completa del archivo/directorio.
     *
     * @return la ruta completa
     */
    public String getPath() {
        return path;
    }

    /**
     * Establece la ruta completa del archivo/directorio.
     *
     * @param path la ruta a establecer
     */
    public void setPath(String path) {
        this.path = path;
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
     * @return true si es directorio, false si es archivo
     */
    public Boolean getIs_dir() {
        return is_dir;
    }

    /**
     * Establece si es un directorio.
     *
     * @param is_dir true si es directorio, false si es archivo
     */
    public void setIs_dir(Boolean is_dir) {
        this.is_dir = is_dir;
    }

    /**
     * Obtiene la fecha y hora de la última actualización.
     *
     * @return la fecha y hora de última actualización
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Establece la fecha y hora de la última actualización.
     *
     * @param lastUpdate la fecha y hora a establecer
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Obtiene la fecha y hora de eliminación (soft delete).
     *
     * @return la fecha y hora de eliminación, o null si no está eliminado
     */
    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    /**
     * Establece la fecha y hora de eliminación (soft delete).
     *
     * @param deletedAt la fecha y hora de eliminación a establecer
     */
    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

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
     * @param md5 el hash MD5 a establecer
     */
    public void setMd5(String md5) {
        this.md5 = md5;
    }
}