package com.project.health_gate.repository;

import com.project.health_gate.entities.Appointment;
import com.project.health_gate.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApoointmentRepository extends JpaRepository<Appointment,Long> {

    List<Appointment> findAppointmentByPatient(User patient);

    Appointment findAppointmentById(Long id);

}
