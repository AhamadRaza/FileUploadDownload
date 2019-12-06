package com.fileUploadDownload.FileUploadDownload.model;

import javax.persistence.*;
import java.io.Serializable;


//url : https://www.youtube.com/watch?v=XZdfUqZ0CG4
@Entity
@Table(name = "user_files")
public class UserFiles implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "modified_file_name")
    private String modifiedFileName;
    @Column(name = "file_extension")
    private String fileExtension;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getModifiedFileName() {
        return modifiedFileName;
    }

    public void setModifiedFileName(String modifiedFileName) {
        this.modifiedFileName = modifiedFileName;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserFiles{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", modifiedFileName='" + modifiedFileName + '\'' +
                ", fileExtension='" + fileExtension + '\'' +
                ", user=" + user +
                '}';
    }
}