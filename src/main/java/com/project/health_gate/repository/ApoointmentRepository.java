package com.project.health_gate.repository;

import com.project.health_gate.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApoointmentRepository extends JpaRepository<Appointment,Long> {

}
