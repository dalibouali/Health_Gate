package com.project.health_gate.DTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
@Data
public class RequestAppointment {
    private LocalDateTime date;
    private Long doctor;
    private  String message;
}
