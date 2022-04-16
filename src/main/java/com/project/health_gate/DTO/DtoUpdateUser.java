package com.project.health_gate.DTO;

import com.project.health_gate.entities.Document;
import com.project.health_gate.entities.Role;
import com.project.health_gate.entities.Specialities;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;



@Data
public class DtoUpdateUser {

    private String firstName;


    private String lastName;




    private String username;



    private String password;



    private String prolfileImageUrl;



    private boolean enabled;


    private String confirmationToken;


    private String gender;


    private Collection<Role> roles=new ArrayList<>();



    private String phone;


    private String address;

    private String city;


    private String height;

    private String lastseen;


    private Document diploma;




    private List<Document> attachements;





    private String bio;

    private Specialities specialities;


    private Boolean IsVerified;



    private String diseases;

    private boolean  isActive;

    private boolean isNotLocked;


    private String civilStatus;
}
