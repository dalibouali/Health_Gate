package com.project.health_gate.controllers;

import com.project.health_gate.entities.User;
import com.project.health_gate.repository.UserRepository;
import com.project.health_gate.services.AdminService;
import com.project.health_gate.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/admin")
@RequiredArgsConstructor
public class adminController {
    private final UserService userService;
    private final UserRepository userRepository;

    private final AdminService adminService;

    @PutMapping("/confirmDoctor/{id}")
    public ResponseEntity<User> confirm_doctor(@PathVariable Long id){

        return ResponseEntity.ok().body(adminService.confirm_doctor(id));

    }
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        try{
            adminService.deleteUser(id);
            return ResponseEntity.noContent().build();
        }
        catch (EmptyResultDataAccessException e){
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<?> showUserDetails(@PathVariable Long id){

            User user =userRepository.findOneById(id);
            if(user!=null)
                return  ResponseEntity.ok().body(user);
         else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");



    }

    @CrossOrigin(origins = "*")
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {


        return ResponseEntity.ok().body(userService.getUsers());
    }
    @GetMapping("/doctors")
    public ResponseEntity<List<User>> getDoctors() {
        List<User> allusers=userService.getUsers();
        List<User> Doctors=new ArrayList<>() {
        } ;
        for(User u:allusers){
            if(u.getRoles().contains("ROLE_DOCTOR"))
            Doctors.add(u);
        }

        return ResponseEntity.ok().body(Doctors);
    }
    @CrossOrigin("*")
    @PostMapping("/deleteComment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {

        userService.deleteMyComment(id);

        return ResponseEntity.ok().build();
    }
    @CrossOrigin("*")
    @PostMapping("/deletePost/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {

        userService.deleteMyPost(id);

        return ResponseEntity.ok().build();
    }


}
