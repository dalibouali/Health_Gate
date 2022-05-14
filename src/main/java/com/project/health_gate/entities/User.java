package com.project.health_gate.entities;




import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.Transient;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static javax.persistence.FetchType.EAGER;


@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    @NotEmpty(message = "Please provide your first name")
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty(message = "Please provide your last name")
    private String lastName;


    @Column(name = "username", nullable = false, unique = true)
    @Email(message = "Please provide a valid e-mail")
    @NotEmpty(message = "Please provide an e-mail")
    private String username;

    @Column(name = "password")
    @Transient
    private String password;


    @Column(name = "Image_Url")
    private String prolfileImageUrl;







    @Column(name = "gender")
    private String gender;

    @ManyToMany(fetch= EAGER )
    @JoinTable(name = "user_roles",joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles=new ArrayList<>();

    @Column(name = "phone")
    @NotEmpty(message = "Please provide your phone number")
    private String phone;

    @Column(name = "address")
    @NotEmpty(message = "Please provide your address")
    private String address;
    @Column(name = "city")
    @NotEmpty(message = "Please provide your city")
    private String city;

    @Column(name = "height")
    @NotEmpty(message = "Please provide your first name")
    private String height;



    /* Diploma*/
    @OneToOne
    private Document diploma;

    /* justification Files And certifications */

    @OneToMany
    private List<Document> attachements;



    /*Biography */
    @Column(name="Bio")
    private String bio;
    /* Speciality*/
    @Column(name="specilaities")
    private String specialities;

    @Column(name="IsVerified")
    private Boolean IsVerified=false;


    @ManyToMany
    private List<User> MyDoctors;





    private String diseases;

    private boolean  isActive;


    private boolean isLocked;


    @Column(name="civil_status")
    private String civilStatus;



    public User(Long id, String firstName, String lastName, String username, String Phone,String password, String prolfileImageUrl,String specialities,  String gender,Boolean IsVerified,String bio,List<User> MyDoctors) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.prolfileImageUrl = prolfileImageUrl;
        this.bio=bio;
        this.MyDoctors=MyDoctors;


        this.gender = gender;
        this.specialities=specialities;
        this.phone=Phone;
        this.IsVerified=IsVerified;





    }
    public User(Long id, String firstName, String lastName, String username, String Phone,String password, String prolfileImageUrl,String specialities,  String gender,Boolean IsVerified,String bio) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.prolfileImageUrl = prolfileImageUrl;
        this.bio=bio;



        this.gender = gender;
        this.specialities=specialities;
        this.phone=Phone;
        this.IsVerified=IsVerified;





    }

    public User(Long id, String username, String firstName, String lastName, String password, String prolfileImageUrl, String gender) {
        this.id=id;
        this.firstName=firstName;
        this.username=username;
        this.lastName=lastName;

    }
}
