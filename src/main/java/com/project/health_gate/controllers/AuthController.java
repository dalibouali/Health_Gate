package com.project.health_gate.controllers;

import com.project.health_gate.entities.AuthenticationRequest;
import com.project.health_gate.entities.AuthenticationResponse;
import com.project.health_gate.entities.User;
import com.project.health_gate.security.JwtUtil;
import com.project.health_gate.services.UserService;
import com.project.health_gate.services.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private final UserService userService;
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final UserServiceImplementation userServiceImplementation;
    @Autowired
    private JwtUtil jwtUtil;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, UserServiceImplementation userServiceImplementation) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.userServiceImplementation = userServiceImplementation;
    }


    @CrossOrigin(origins = "*")
    @RequestMapping(value ="/signin", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthentificationToken(@RequestBody AuthenticationRequest request) throws Exception {


        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            System.out.println("User authenticated successfully");
        }catch (BadCredentialsException e) {

            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Username or password invalid");

        }


        final UserDetails userDetails = userServiceImplementation.loadUserByUsername(request.getUsername());
        String access_token= jwtUtil.generateToken(userDetails);



        return ResponseEntity.ok(new AuthenticationResponse(access_token));
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/register")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/register").toUriString());

        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }
}
