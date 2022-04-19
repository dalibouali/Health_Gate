package com.project.health_gate.services;

import com.project.health_gate.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {
     User confirm_doctor(Long id);
     void deleteUser(Long id);
}
