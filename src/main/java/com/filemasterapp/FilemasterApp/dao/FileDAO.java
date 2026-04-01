package com.filemasterapp.FilemasterApp.dao;

import com.filemasterapp.FilemasterApp.Model.File;
import com.filemasterapp.FilemasterApp.Model.FileSystem;
import org.hibernate.Session;

import java.util.List;

public class FileDAO {
    private Session session;

    public FileDAO(Session session) {
        this.session = session;
    }

    public List<File>  getFileList(FileSystem fileSystemId) {
        return this.session.createQuery("from File where fileSystem =:fsId and deletedAt is null ",File.class).setParameter("fsId", fileSystemId).getResultList();
    }

    public int deleteAllRecords(FileSystem fs) {
        return this.session.createQuery("delete from File where fileSystem=:filesystem").setParameter("filesystem",fs).executeUpdate();
    }
}
