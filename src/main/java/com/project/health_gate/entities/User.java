package com.project.health_gate.entities;




import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.Transient;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
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


    /* can be deleted*/
    @Column(name = "enabled")
    private boolean enabled;


    @Column(name = "gender")
    private String gender;

    @ManyToMany(fetch= EAGER )
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
    /* can be deleted*/
    @Column(name = "lastseen")
    @Transient
    private String lastseen;

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
    @Column(name="Specilaitiess")
    private Specialities specialities;

    @Column(name="IsVerified")
    private Boolean IsVerified;



    private String diseases;

    private boolean  isActive;

    private boolean isNotLocked;

    @Column(name="civil_status")
    private String civilStatus;


    public User(Long id, String firstName, String lastName, String username, String password, String prolfileImageUrl, boolean enabled, String gender) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.prolfileImageUrl = prolfileImageUrl;
        this.enabled = enabled;
        this.gender = gender;



    }

    public User(Long id, String username, String firstName, String lastName, String password, String prolfileImageUrl, String gender) {
        this.id=id;
        this.firstName=firstName;
        this.username=username;
        this.lastName=lastName;

    }
}
