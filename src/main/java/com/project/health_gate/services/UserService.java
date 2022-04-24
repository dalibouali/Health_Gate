package com.project.health_gate.services;

import com.project.health_gate.DTO.DtoUpdateUser;
import com.project.health_gate.entities.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
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
    void deleteFileFromMedicalFile(Long id);
    void addDiscription(Long id,String disc);
    void addDoctorToMyList(Long id);
    void SendAppointmentRequest(Date date, Long id, String message);
    void makePost(String Content);
    void makeComment(String Content,Long id);
    List<Post> showPosts();
    List<Comment> showCommentsPerPost(Long id);
    void deleteMyPost(Long id);
    void deleteMyComment(Long id);
    List<User> getDoctors();





}
