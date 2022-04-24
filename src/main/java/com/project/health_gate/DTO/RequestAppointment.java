package com.project.health_gate.DTO;

import lombok.Data;

import java.util.Date;
@Data
public class RequestAppointment {
    private Date date;
    private Long doctor;
    private  String message;
}
