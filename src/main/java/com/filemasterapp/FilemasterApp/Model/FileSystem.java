package com.filemasterapp.FilemasterApp.Model;

import jakarta.persistence.*;
import com.filemasterapp.FilemasterApp.dto.FileSystemInputDTO;
import com.filemasterapp.FilemasterApp.dto.FileSystemOutDTO;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "filesystems")
public class FileSystem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 256)
    private String target;

    @Column(name = "source", length = 256)
    private String source;

    @Column(name = "fstype", length = 60)
    private String fstype;

    @Column(name = "options", length = 1024)
    private String options;

    @Column(name = "enabled")
    private Boolean enabled = true;

    @Column(name = "capacity", nullable = false)
    private Long capacity = 0L;

    @Column(name = "available", nullable = false)
    private Long available = 0L;

    @Column(name = "inuse", nullable = false)
    private Long inuse = 0L;

    @Column(name = "last_update", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime lastUpdate; // fecha + hora

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "partuuid")
    private String partuuid;

    @Column(name = "partlabel")
    String partlabel;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


    public String getPartUuid() {
        return partuuid;
    }

    public void setPartUuid(String partUuid) {
        this.partuuid = partUuid;
    }

    @PrePersist
    protected void onCreate() {
        lastUpdate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdate = LocalDateTime.now();
    }

    public Set<FileSystem> getChildren() {
        return children;
    }

    public void setChildren(Set<FileSystem> children) {
        this.children = children;
    }

    public void addChild(FileSystem child) {
        if (children != null) {
            children.add(child);
        }
    }

    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = true)
    private FileSystem parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<FileSystem> children = new HashSet<FileSystem>();

    public void setBlkInfo(String uuid, String partuuid, String partlabel) {
        this.uuid = uuid;
        this.partuuid = partuuid;
        this.partlabel = partlabel;
    }

    public FileSystem(FileSystemInputDTO input) {
        this.fstype = input.getFstype();
        this.target = input.getTarget();
        this.source = input.getSource();
        this.options = input.getOptions();
        this.capacity = input.getCapacity();
        this.available = input.getAvailable();
        this.inuse = input.getInuse();
        this.lastUpdate = input.getLastUpdate();
        this.uuid = input.getUuid();
        this.partuuid = input.getPartuuid();
        this.partlabel = input.getPartlabel();
        this.parent = input.getParent();
    }

    //FileSystem(Long id, String target, String source, String fstype, String options,Long capacity,Long available,Long inuse, FileSystem parent) {
    FileSystem(FileSystemOutDTO out) {
        this.id = out.getId();
        this.fstype = out.getFstype();
        this.target = out.getTarget();
        this.source = out.getSource();
        this.options = out.getOptions();
        this.capacity = out.getCapacity();
        this.available = out.getAvailable();
        this.inuse = out.getInuse();
        this.lastUpdate = out.getLastUpdate();
        this.uuid = out.getUuid();
        this.partuuid = out.getPartuuid();
        this.partlabel = out.getPartlabel();
        this.parent = out.getParent();
    }


    public FileSystem() {

    }


    public FileSystem getParent() {
        return parent;
    }

    public void setParent(FileSystem parent) {
        this.parent = parent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getFstype() {
        return fstype;
    }

    public void setFstype(String fstype) {
        this.fstype = fstype;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Long getCapacity() {
        return capacity;
    }

    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }

    public Long getAvailable() {
        return available;
    }

    public void setAvailable(Long available) {
        this.available = available;
    }

    public Long getInuse() {
        return inuse;
    }

    public void setInuse(Long inuse) {
        this.inuse = inuse;
    }

    public String getPartuuid() {
        return partuuid;
    }

    public void setPartuuid(String partuuid) {
        this.partuuid = partuuid;
    }

    public String getPartlabel() {
        return partlabel;
    }

    public void setPartlabel(String partlabel) {
        this.partlabel = partlabel;
    }

    @Override
    public String toString() {
        return this.getFstype() + " " + this.getTarget() + " " + this.getSource();
    }

    @Override
    public boolean equals(Object o) {
        FileSystem fs = (FileSystem) o;
        if ( (this.getUuid()!=null && fs.getUuid()!=null && this.getUuid().equals(fs.getUuid() ) )||
                (this.getPartUuid()!=null && fs.getPartUuid()!=null && this.getPartUuid().equals(fs.getPartUuid())) ||
                (this.getPartlabel()!=null && fs.getPartlabel()!=null && this.getPartlabel().equals(fs.getPartlabel())) ||
                this.getSource()!=null && fs.getSource()!=null && this.getSource().equals(fs.getSource())
        ) {
            return true;
        }
        return false;
    }

    public void updateFields(FileSystem fs) {
        this.setAvailable(fs.getAvailable());
        this.setInuse(fs.getInuse());
        this.setLastUpdate(fs.getLastUpdate());
    }
}
