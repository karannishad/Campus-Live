package com.FaceReko.model;



import javax.persistence.*;

@Entity
public class AttendanceRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String className;

    private String fileName;

    private String fileType;

    @Lob
    private byte[] image;

    public AttendanceRecord() {

    }


    public Long getId() {
        return id;
    }

    public AttendanceRecord(String className, String fileName, String fileType, byte[] image) {
        this.className = className;
        this.fileName = fileName;
        this.fileType = fileType;
        this.image = image;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
