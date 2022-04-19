package com.project.health_gate.services;

import com.project.health_gate.DTO.DtoUpdateUser;
import com.project.health_gate.entities.Document;
import com.project.health_gate.entities.Erole;
import com.project.health_gate.entities.Role;
import com.project.health_gate.entities.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, Erole roleName);
    User getUser(String username);
    List<User> getUsers();
    Document addFileToMedicalFile(MultipartFile multipartFile) throws IOException;
    List<Document> GetFiles();
    User updateProfile(UserDetails userDetails, User newUser);




}
