package com.filemasterapp.FilemasterApp.dto;

import com.filemasterapp.FilemasterApp.Model.FileSystem;

import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) para representar un sistema de archivos en salida.
 * <p>
 * Contiene la información de un sistema de archivos para transferir datos
 * entre capas del sistema, incluyendo identificador, destino, origen,
 * tipo de sistema de archivos, opciones y capacidad.
 * </p>
 */
public class FileSystemOutDTO {

    /**
     * Identificador único del sistema de archivos.
     */
    private Long id;

    /**
     * Ruta de destino del sistema de archivos.
     */
    private String target;

    /**
     * Ruta de origen del sistema de archivos.
     */
    private String source;

    /**
     * Tipo de sistema de archivos (ej: ext4, xfs, btrfs).
     */
    private String fstype;

    /**
     * Opciones de montaje del sistema de archivos.
     */
    private String options;

    /**
     * Indica si el sistema de archivos está habilitado.
     * <p>
     * Valor por defecto: true
     * </p>
     */
    private Boolean enabled = true;

    /**
     * Capacidad total del sistema de archivos en bytes.
     * <p>
     * Valor por defecto: 0
     * </p>
     */
    private Long capacity = 0L;

    /**
     * Espacio disponible en el sistema de archivos en bytes.
     * <p>
     * Valor por defecto: 0
     * </p>
     */
    private Long available = 0L;

    /**
     * Espacio utilizado en el sistema de archivos en bytes.
     * <p>
     * Valor por defecto: 0
     * </p>
     */
    private Long inuse = 0L;

    /**
     * Fecha y hora de la última actualización.
     */
    private LocalDateTime lastUpdate;

    /**
     * UUID único del sistema de archivos.
     */
    private String uuid;

    /**
     * PartUUID del sistema de archivos.
     */
    private String partuuid;

    /**
     * Etiqueta de la partición del sistema de archivos.
     */
    private String partlabel;

    /**
     * Sistema de archivos padre (para sistemas de archivos montados dentro de otros).
     */
    private FileSystem parent;

    /**
     * Constructor que inicializa el DTO con los parámetros básicos.
     * <p>
     * Establece los valores por defecto para enabled, capacity, available e inuse.
     * </p>
     *
     * @param id identificador único del sistema de archivos
     * @param target ruta de destino del sistema de archivos
     * @param source ruta de origen del sistema de archivos
     * @param fstype tipo de sistema de archivos
     * @param options opciones de montaje
     * @param capacity capacidad total en bytes
     * @param available espacio disponible en bytes
     * @param inuse espacio utilizado en bytes
     * @param parent sistema de archivos padre (opcional)
     */
    FileSystemOutDTO(Long id, String target, String source, String fstype, String options, Long capacity, Long available, Long inuse, FileSystem parent) {
        this.id = id;
        this.fstype = fstype;
        this.target = target;
        this.source = source;
        this.options = options;
        this.capacity = capacity;
        this.available = available;
        this.inuse = inuse;
        this.parent = parent;
    }

    /**
     * Obtiene el sistema de archivos padre.
     *
     * @return el sistema de archivos padre, o null si no hay padre
     */
    public FileSystem getParent() {
        return parent;
    }

    /**
     * Establece el sistema de archivos padre.
     *
     * @param parent el sistema de archivos padre a establecer
     */
    public void setParent(FileSystem parent) {
        this.parent = parent;
    }

    /**
     * Obtiene el identificador único del sistema de archivos.
     *
     * @return el identificador
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador único del sistema de archivos.
     *
     * @param id el identificador a establecer
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene la ruta de destino del sistema de archivos.
     *
     * @return la ruta de destino
     */
    public String getTarget() {
        return target;
    }

    /**
     * Establece la ruta de destino del sistema de archivos.
     *
     * @param target la ruta de destino a establecer
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * Obtiene la ruta de origen del sistema de archivos.
     *
     * @return la ruta de origen
     */
    public String getSource() {
        return source;
    }

    /**
     * Establece la ruta de origen del sistema de archivos.
     *
     * @param source la ruta de origen a establecer
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * Obtiene el tipo de sistema de archivos.
     *
     * @return el tipo de sistema de archivos
     */
    public String getFstype() {
        return fstype;
    }

    /**
     * Establece el tipo de sistema de archivos.
     *
     * @param fstype el tipo de sistema de archivos a establecer
     */
    public void setFstype(String fstype) {
        this.fstype = fstype;
    }

    /**
     * Obtiene las opciones de montaje del sistema de archivos.
     *
     * @return las opciones de montaje
     */
    public String getOptions() {
        return options;
    }

    /**
     * Establece las opciones de montaje del sistema de archivos.
     *
     * @param options las opciones de montaje a establecer
     */
    public void setOptions(String options) {
        this.options = options;
    }

    /**
     * Indica si el sistema de archivos está habilitado.
     *
     * @return true si está habilitado, false si no
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * Establece si el sistema de archivos está habilitado.
     *
     * @param enabled true si está habilitado, false si no
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Obtiene la capacidad total del sistema de archivos en bytes.
     *
     * @return la capacidad total
     */
    public Long getCapacity() {
        return capacity;
    }

    /**
     * Establece la capacidad total del sistema de archivos en bytes.
     *
     * @param capacity la capacidad total a establecer
     */
    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }

    /**
     * Obtiene el espacio disponible en el sistema de archivos en bytes.
     *
     * @return el espacio disponible
     */
    public Long getAvailable() {
        return available;
    }

    /**
     * Establece el espacio disponible en el sistema de archivos en bytes.
     *
     * @param available el espacio disponible a establecer
     */
    public void setAvailable(Long available) {
        this.available = available;
    }

    /**
     * Obtiene el espacio utilizado en el sistema de archivos en bytes.
     *
     * @return el espacio utilizado
     */
    public Long getInuse() {
        return inuse;
    }

    /**
     * Establece el espacio utilizado en el sistema de archivos en bytes.
     *
     * @param inuse el espacio utilizado a establecer
     */
    public void setInuse(Long inuse) {
        this.inuse = inuse;
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
     * Obtiene el UUID único del sistema de archivos.
     *
     * @return el UUID
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Establece el UUID único del sistema de archivos.
     *
     * @param uuid el UUID a establecer
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * Obtiene el PartUUID del sistema de archivos.
     *
     * @return el PartUUID
     */
    public String getPartuuid() {
        return partuuid;
    }

    /**
     * Establece el PartUUID del sistema de archivos.
     *
     * @param partuuid el PartUUID a establecer
     */
    public void setPartuuid(String partuuid) {
        this.partuuid = partuuid;
    }

    /**
     * Obtiene la etiqueta de la partición del sistema de archivos.
     *
     * @return la etiqueta de la partición
     */
    public String getPartlabel() {
        return partlabel;
    }

    /**
     * Establece la etiqueta de la partición del sistema de archivos.
     *
     * @param partlabel la etiqueta de la partición a establecer
     */
    public void setPartlabel(String partlabel) {
        this.partlabel = partlabel;
    }

    /**
     * Devuelve una representación en cadena del objeto.
     * Formato: id|target|source|fstype|options|enabled|capacity|available|inuse|lastUpdate|uuid|partuuid|partlabel
     *
     * @return cadena con la información del sistema de archivos
     */
    @Override
    public String toString() {
        return this.id + "|" + this.target + "|" + this.source + "|" + this.fstype + "|" + this.options + "|" + this.enabled + "|" + this.capacity + "|" + this.available + "|" + this.inuse + "|" + this.lastUpdate + "|" + this.uuid + "|" + this.partuuid + "|" + this.partlabel;
    }
}