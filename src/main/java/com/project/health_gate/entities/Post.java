package com.project.health_gate.entities;

import lombok.Data;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Post {
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

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;


}
