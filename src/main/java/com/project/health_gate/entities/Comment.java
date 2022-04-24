package com.project.health_gate.entities;

import lombok.Data;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;

@Data
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long  id;

    @OneToOne
    private  User maker;

    private Date date;

    private LocalTime time;

    private String content;

    private boolean disabled;

    @ManyToOne
    private Post post;

}
