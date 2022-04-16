package com.project.health_gate.repository;

import com.project.health_gate.entities.Erole;
import com.project.health_gate.entities.Role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(Erole name);
}

