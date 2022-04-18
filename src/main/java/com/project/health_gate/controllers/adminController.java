package com.project.health_gate.controllers;

import com.project.health_gate.entities.User;
import com.project.health_gate.services.AdminService;
import com.project.health_gate.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/admin")
@RequiredArgsConstructor
public class adminController {
    private final UserService userService;

    private final AdminService adminService;

    @PutMapping("/confirmDoctor/{id}")
    public ResponseEntity<User> confirm_doctor(@PathVariable Long id){

        return ResponseEntity.ok().body(adminService.confirm_doctor(id));

    }
    @CrossOrigin(origins = "*")
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {


        return ResponseEntity.ok().body(userService.getUsers());
    }


}
