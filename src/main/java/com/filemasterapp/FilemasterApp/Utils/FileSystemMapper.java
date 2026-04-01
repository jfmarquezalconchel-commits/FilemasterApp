package com.filemasterapp.FilemasterApp.Utils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Clase que representa un sistema de archivos montado.
 * Se utiliza para mapear los resultados de la salida del comando findmnt.
 * <p>
 * Cada sistema de archivo puede tener hijos (submontajes) representados en el campo children.
 * </p>
 */
public class FileSystemMapper implements Serializable {

    /**
     * Identificador único del sistema de archivos.
     */
    public Long id;

    /**
     * Punto de montaje del sistema de archivos (target).
     */
    public String target;

    /**
     * Origen del sistema de archivos (source).
     */
    public String source;

    /**
     * Tipo de sistema de archivos (ej: ext4, xfs, tmpfs).
     */
    public String fstype;

    /**
     * Opciones de montaje.
     */
    public String options;

    /**
     * Lista de sistemas de archivos hijos (submontajes).
     */
    public ArrayList<FileSystemMapper> children = new ArrayList<FileSystemMapper>();
}
