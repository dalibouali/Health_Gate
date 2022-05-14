package com.project.health_gate.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.health_gate.DTO.DtoUpdateUser;

import com.project.health_gate.DTO.RequestAppointment;

import com.project.health_gate.entities.*;
import com.project.health_gate.repository.UserRepository;
import com.project.health_gate.security.JwtUtil;
import com.project.health_gate.services.UserService;
import com.project.health_gate.services.UserServiceImplementation;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
@Slf4j
@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class userController {

    private final UserService userService;
    private final UserRepository userRepository;




    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }


    @GetMapping("/doctors")
    public ResponseEntity<List<User>> getDoctors() {
        return ResponseEntity.ok().body(userService.getDoctors());
    }
    @GetMapping("/Mydoctors")
    public ResponseEntity<List<User>> getMyDoctors() {
        return ResponseEntity.ok().body(userService.getMyDoctors());
    }
    @GetMapping("/Mypatients")
    public ResponseEntity<List<User>> getMyPatients() {
        return ResponseEntity.ok().body(userService.getMyPatients());
    }


    @PutMapping("deleteDoctorFromMyList/{id}")
    public ResponseEntity<?> deleteDoctorFromMyList(@PathVariable Long id,@RequestBody String username){
        userService.deletedoctorfromMyList(id,username);
        return ResponseEntity.ok().build();

    }




    @PostMapping (value="/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addFileToMedicalFile(@RequestParam("document") MultipartFile multipartFile) throws IOException {
        userService.addFileToMedicalFile(multipartFile);
        return ResponseEntity.ok().build();
    }

    @PostMapping (value="/uploadImage" )
    public ResponseEntity<?> uploadImage(@RequestBody String imagePath) throws IOException {
        userService.addProfileImage(imagePath);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/files")
    public ResponseEntity<List<Document>> GetFiles()  {
        return ResponseEntity.ok().body(userService.GetFiles());
    }


    @PutMapping("/setProfile")
    public User setProfile(@RequestBody User user){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();


        userService.updateProfile(userDetails,user);

        return user;

    }



    @DeleteMapping("/deleteFile/{id}")
    public ResponseEntity<?> deleteFile(@PathVariable Long id) {
        userService.deleteFileFromMedicalFile(id);

        return ResponseEntity.ok().build();
    }



    @PutMapping("/addDiscription/{id}")
    public ResponseEntity<?> addDiscription(@PathVariable Long id,@RequestBody String disc) {
        userService.addDiscription(id,disc);

        return ResponseEntity.ok().build();
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<?> showUserDetails(@PathVariable Long id){

        User user =userRepository.findOneById(id);
        if(user!=null)
            return  ResponseEntity.ok().body(user);
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");



    }
    @PutMapping("/Editappointment/{id}")
    public void Editappointment(@PathVariable Long id,@RequestBody LocalDateTime date){
        userService.EditDate(id,date);
    }

    @PutMapping("/addDoctorToMyList/{id}")
    public ResponseEntity<?> addDoctorToMyList(@PathVariable Long id,@RequestBody String username) {

        userService.addDoctorToMyList(id,username);

        return ResponseEntity.ok().build();
    }


    @PostMapping("/sendAppointment")
    public ResponseEntity<?> sendAppointment(@RequestBody RequestAppointment request) {

        userService.SendAppointmentRequest(request.getDate(), request.getDoctor(), request.getMessage());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/showMyProfileImg")
    public String ShowProfileImg()  {

        System.out.println(userService.showrPofileImage());

        return userService.showrPofileImage();
    }


    @PostMapping("/makePost")
    public ResponseEntity<?> makepost(@RequestBody String content) {

        userService.makePost(content);

        return ResponseEntity.ok().build();
    }


    @PostMapping("/makeComment/{id}")
    public ResponseEntity<?> makeComment(@RequestBody String content,@PathVariable Long id) {

        userService.makeComment(content,id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/showPosts/")
    public ResponseEntity<?> showPosts() {
        return ResponseEntity.ok().body(userService.showPosts());
    }

    @GetMapping("/showCommentsPerPost/{id}")
    public ResponseEntity<?> showCommentsPerPost(@PathVariable Long id) {
        return ResponseEntity.ok().body(userService.showCommentsPerPost(id));
    }


    @PostMapping("/deleteMyComment/{id}")
    public ResponseEntity<?> deleteMyComment(@PathVariable Long id) {

        userService.deleteMyComment(id);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteMyPost/{id}")
    public ResponseEntity<?> deleteMyPost(@PathVariable Long id) {

        userService.deleteMyPost(id);

        return ResponseEntity.ok().build();
    }
    @GetMapping("/findApoointmentsAsUser")
    public ResponseEntity<List<Appointment>> findAppointmentsAsUser(){
        return ResponseEntity.ok().body(userService.getAppointmentsAsUser());
    }
    @GetMapping("/findApoointmentsAsDoctor")
    public ResponseEntity<List<Appointment>> findApoointmentsAsDoctor(){
        return ResponseEntity.ok().body(userService.getAppointmentAsDoctor());
    }

    @PutMapping("/EditAppointments/{id}")
    public ResponseEntity<?> EditAppointments(@PathVariable Long id,@RequestParam("date") String date) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm");
        LocalDateTime  d1 = LocalDateTime.parse(date, df);


        userService.EditDate(id,d1);

        return ResponseEntity.ok().build();
    }
    @PutMapping("/setAppointmentDate/{id}")
    public ResponseEntity<?> setAppointmentDate(@PathVariable Long id,@RequestBody LocalTime time){
        userService.setAppointmentDate(id,time);

        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/cancelAppointment/{id}")
    public ResponseEntity<?> cancelAppointment(@PathVariable Long id) {

        userService.CancelApoointment(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("getFileContent/{id}")
    public ResponseEntity<String> getFileContent(@PathVariable Long id) throws IOException {
        try{
            return ResponseEntity.ok().body(userService.GetFileContent(id));

        }catch (IOException e){
            return ResponseEntity.status(400).body("No File Exist "+e);
        }

    }
    @PutMapping("setFileContent/{id}")
    public ResponseEntity<?> setFileContent(@PathVariable Long id,@RequestBody String newContent) throws IOException {
        try{
            userService.SetFileContent(id,newContent);
            return ResponseEntity.ok().build();

        }catch (IOException e){
            return ResponseEntity.status(400).body("No such File Exist "+e);
        }

    }









    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

    @PostMapping("/role/addToUser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form) {
        userService.addRoleToUser(form.getUsername(), Erole.valueOf(form.getRolename()));

        return ResponseEntity.ok().build();
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes(StandardCharsets.UTF_8));
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                User user = userService.getUser(username);


                String access_token = JWT.create()
                        .withSubject(user.getUsername()).
                        withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);

            } catch (Exception exception) {

                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());

                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());

                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);


            }

        } else {
            throw new RuntimeException("Refresh token is missing");

        }


    }

    @Data
    class RoleToUserForm {
        private String username;
        private String rolename;
    }
}