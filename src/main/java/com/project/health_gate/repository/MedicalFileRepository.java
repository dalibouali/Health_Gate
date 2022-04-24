package com.project.health_gate.repository;

import com.project.health_gate.entities.MedicalFile;
import com.project.health_gate.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalFileRepository extends JpaRepository<MedicalFile,Long> {

    MedicalFile getMedicalFileByUser(User user);
}
