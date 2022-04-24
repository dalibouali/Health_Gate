package com.project.health_gate.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long  FileId;

    private String  name;

    private long size;

    private Date uploadTime;

    private byte[] content;

    @Column(nullable = true)
    private String discription="No discription until now";

    public Document(Long fileId, String name, long size,Date uploadTime,byte[] content,String disc) {
        FileId = fileId;
        this.name = name;
        this.size = size;
        this.uploadTime=uploadTime;
        this.content=content;
        this.discription=disc;
    }
    public Document(Long fileId, String name, long size,Date uploadTime,byte[] content,MedicalFile medicalfile,String disc) {
        FileId = fileId;
        this.name = name;
        this.size = size;
        this.uploadTime=uploadTime;
        this.content=content;
        this.discription=disc;
        this.medicalfile=medicalfile;
    }

    @ManyToOne
    private MedicalFile medicalfile;



}
