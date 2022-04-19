package com.project.health_gate.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class MedicalFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @OneToMany
    private List<Document> Content;

    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    private User user;



}
