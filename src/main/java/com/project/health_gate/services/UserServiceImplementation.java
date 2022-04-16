package com.project.health_gate.services;

import com.project.health_gate.DTO.DtoUpdateUser;
import com.project.health_gate.entities.*;
import com.project.health_gate.repository.DocumentRepository;
import com.project.health_gate.repository.MedicalFileRepository;
import com.project.health_gate.repository.RoleRepository;
import com.project.health_gate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j //for log
public class UserServiceImplementation implements UserService, UserDetailsService {
    private final ModelMapper mapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final MedicalFileRepository medicalFileRepository;
    private final DocumentRepository documentRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findByUsername(username);
        if(user==null)
        {
            log.error("User not found in DB");
            throw new UsernameNotFoundException("USEr not found in the database");
        }else{
            log.info("User found in DB :{}",username);
        }
        Collection<SimpleGrantedAuthority> authorities=new ArrayList<>();
        user.getRoles().forEach(role->{
            authorities.add(new SimpleGrantedAuthority(role.getName().toString()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities) ;
    }
    @Override
    public User updateProfile(UserDetails userDetails, User newUser) {
        User currentUser =userRepository.findByUsername(userDetails.getUsername());
        if(newUser.getUsername()!=null){
            currentUser.setUsername(newUser.getUsername());
        }
        if(newUser.getPassword()!=null){
            currentUser.setPassword(newUser.getPassword());
        }
        if(newUser.getAddress()!=null){
            currentUser.setAddress(newUser.getAddress());
        }
        if(newUser.getRoles()!=null){
            currentUser.setRoles(newUser.getRoles());
        }
        if(newUser.getBio()!=null){
            currentUser.setBio(newUser.getBio());
        }
        if(newUser.getCity()!=null){
            currentUser.setCity(newUser.getCity());
        }
        if(newUser.getCivilStatus()!=null){
            currentUser.setCivilStatus(newUser.getCivilStatus());
        }
        if(newUser.getAttachements()!=null){
            currentUser.setAttachements(newUser.getAttachements());
        }
        if(newUser.getDiseases()!=null){
            currentUser.setDiseases(newUser.getDiseases());
        }
        if(newUser.getDiploma()!=null){
            currentUser.setDiploma(newUser.getDiploma());
        }
        if(newUser.getFirstName()!=null){
            currentUser.setFirstName(newUser.getFirstName());
        }
        if(newUser.getFirstName()!=null){
            currentUser.setFirstName(newUser.getFirstName());
        }
        if(newUser.getHeight()!=null){
            currentUser.setHeight(newUser.getHeight());
        }

        if(newUser.getGender()!=null){
            currentUser.setGender(newUser.getGender());
        }
        if(newUser.getProlfileImageUrl()!=null){
            currentUser.setProlfileImageUrl(newUser.getProlfileImageUrl());
        }
        if(newUser.getPhone()!=null){
            currentUser.setPhone(newUser.getPhone());
        }
        if(newUser.getSpecialities()!=null){
            currentUser.setSpecialities(newUser.getSpecialities());
        }



        userRepository.save(currentUser);
        return currentUser;
    }
    @Override
    public User saveUser(User user) {
        log.info("saving new user {} to the database",user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        ArrayList<Role> defaultrole=new ArrayList<>();
        defaultrole.add(roleRepository.findByName(Erole.ROLE_USER));
        user.setRoles(defaultrole);
        //Create a medical File
        MedicalFile med =new MedicalFile();
        med.setUser(user);
        medicalFileRepository.save(med);
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("saving new role {} to the database",role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, Erole roleName) {
        log.info("Adding role {} to user {} ",roleName,username);
        User user =userRepository.findByUsername(username);
        Role role=roleRepository.findByName(roleName);
        user.getRoles().add(role);

    }

    @Override
    public User getUser(String username) {
        log.info("Fetching  user {} ",username);
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getUsers() {
        log.info("Fetching  users");

        return userRepository.findAllUsers();
    }

    //upload any file to a medical file
    public Document addFileToMedicalFile( MultipartFile multipartFile) throws IOException {
        String filename= StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        MedicalFile medicalfile = null;
        String username="";
        //get user loged in
        Object principal = SecurityContextHolder. getContext(). getAuthentication(). getPrincipal();
        if (principal instanceof UserDetails) {
             username = ((UserDetails)principal). getUsername();
        } else {
            username = principal. toString();
        }
        User user=userRepository.findByUsername(username);
        medicalfile=medicalFileRepository.getMedicalFileByUser(user);
        System.out.println(medicalfile.getId());

        Document doc =new Document();
        doc.setName(filename);
        doc.setContent(multipartFile.getBytes());
        doc.setSize(multipartFile.getSize());
        doc.setUploadTime(new Date());
        doc.setMedicalfile(medicalfile);

        documentRepository.save(doc);

        return doc;
    }
    //get all User files

    public List<Document> GetFiles(){
        String username="";
        MedicalFile medicalfile = null;
        Object principal = SecurityContextHolder. getContext(). getAuthentication(). getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal). getUsername();
        } else {
            username = principal. toString();
        }
        User user=userRepository.findByUsername(username);
        medicalfile=medicalFileRepository.getMedicalFileByUser(user);

        List<Document> docs= documentRepository.findDocumentsByMedicalfile(medicalfile);
        return docs;

    }


}
