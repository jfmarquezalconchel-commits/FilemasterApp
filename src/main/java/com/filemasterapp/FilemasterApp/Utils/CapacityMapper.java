package com.filemasterapp.FilemasterApp.Utils;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Clase que mapea la capacidad de un sistema de archivos.
 * Se utiliza para almacenar información obtenida del comando df,
 * incluyendo tamaño, usado, disponible y porcentaje de uso.
 * <p>
 * Esta clase implementa Serializable para permitir la persistencia de objetos
 * y utiliza anotaciones Jackson para serialización JSON.
 * </p>
 */
public class CapacityMapper implements Serializable {

    /**
     * Sistema de archivos (ej: /dev/sda1, tmpfs).
     */
    @JsonProperty("filesystem")
    private String filesystem;

    /**
     * Tamaño total del sistema de archivos en bytes.
     */
    private Long size = 0L;

    /**
     * Espacio usado en el sistema de archivos en bytes.
     */
    private Long used = 0L;

    /**
     * Espacio disponible en el sistema de archivos en bytes.
     */
    private Long avail = 0L;

    /**
     * Porcentaje de uso del sistema de archivos.
     */
    private String use_percent;

    /**
     * Punto de montaje del sistema de archivos.
     */
    private String mounted_on;

    /**
     * Obtiene el sistema de archivos.
     *
     * @return el sistema de archivos
     */
    public String getFilesystem() {
        return filesystem;
    }

    /**
     * Establece el sistema de archivos.
     *
     * @param filesystem el sistema de archivos a establecer
     */
    public void setFilesystem(String filesystem) {
        this.filesystem = filesystem;
    }

    /**
     * Obtiene el tamaño total del sistema de archivos.
     *
     * @return el tamaño en bytes
     */
    public Long getSize() {
        return size;
    }

    /**
     * Establece el tamaño del sistema de archivos.
     *
     * @param size el tamaño en bytes
     */
    public void setSize(String size) {
        this.size = Long.valueOf(size);
    }

    /**
     * Obtiene el espacio usado en el sistema de archivos.
     *
     * @return el espacio usado en bytes
     */
    public Long getUsed() {
        return used;
    }

    /**
     * Establece el espacio usado en el sistema de archivos.
     *
     * @param used el espacio usado en bytes
     */
    public void setUsed(String used) {
        this.used = Long.valueOf(used);
    }

    /**
     * Obtiene el espacio disponible en el sistema de archivos.
     *
     * @return el espacio disponible en bytes
     */
    public Long getAvail() {
        return avail;
    }

    /**
     * Establece el espacio disponible en el sistema de archivos.
     *
     * @param avail el espacio disponible en bytes
     */
    public void setAvail(String avail) {
        this.avail = Long.valueOf(avail);
    }

    /**
     * Obtiene el porcentaje de uso del sistema de archivos.
     *
     * @return el porcentaje de uso
     */
    public String getUse_percent() {
        return use_percent;
    }

    /**
     * Establece el porcentaje de uso del sistema de archivos.
     *
     * @param use_percent el porcentaje de uso
     */
    public void setUse_percent(String use_percent) {
        this.use_percent = use_percent;
    }

    /**
     * Obtiene el punto de montaje del sistema de archivos.
     *
     * @return el punto de montaje
     */
    public String getMounted_on() {
        return mounted_on;
    }

    /**
     * Establece el punto de montaje del sistema de archivos.
     *
     * @param mounted_on el punto de montaje
     */
    public void setMounted_on(String mounted_on) {
        this.mounted_on = mounted_on;
    }

    /**
     * Devuelve una representación en cadena del objeto.
     * Formato: filesystem|mounted_on|avail|used|use_percent
     *
     * @return cadena con la información del sistema de archivos
     */
    @Override
    public String toString() {
        return this.getFilesystem() + "|"
                + this.getMounted_on() + "|"
                + this.getAvail() + "|"
                + this.getUsed() + "|"
                + this.getUse_percent();
    }
}
