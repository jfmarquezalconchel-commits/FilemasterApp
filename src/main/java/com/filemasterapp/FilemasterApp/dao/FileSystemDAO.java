package com.filemasterapp.FilemasterApp.dao;

import com.filemasterapp.FilemasterApp.Model.FileSystem;
import org.hibernate.Session;


import java.util.List;

/**
 * Clase DAO (Data Access Object) para gestionar la persistencia de datos
 * relacionados con el sistema de archivos.
 * <p>
 * Utiliza Hibernate para realizar consultas HQL y acceder a la base de datos.
 * </p>
 */
public class FileSystemDAO {

    /**
     * Sesión de Hibernate para realizar operaciones de base de datos.
     */
    private Session session;

    /**
     * Constructor que inicializa el DAO con una sesión de Hibernate.
     *
     * @param session la sesión de Hibernate a utilizar
     */
    public FileSystemDAO(Session session) {
        this.session = session;
    }

    /**
     * Obtiene una lista de sistemas de archivos hijos de un sistema de archivos padre.
     * <p>
     * Consulta todos los archivos que tienen como sistema de archivos padre
     * el especificado.
     * </p>
     *
     * @param f el sistema de archivos padre
     * @return una lista de FileSystem con los hijos del sistema de archivos padre
     */
    public List<FileSystem> getFileSystemListChilds(FileSystem f) {
        return this.session.createQuery("from File where fileSystem = :p ", FileSystem.class)
                .setParameter("p", f.getParent())
                .list();
    }

    /**
     * Obtiene una lista de todos los sistemas de archivos.
     * <p>
     * Consulta todos los registros de la entidad File en la base de datos.
     * </p>
     *
     * @return una lista de FileSystem con todos los sistemas de archivos
     */
    public List<FileSystem> getFileSystemListAll() {
        return this.session.createQuery("from FileSystem where enabled is true", FileSystem.class).getResultList();
    }

}
