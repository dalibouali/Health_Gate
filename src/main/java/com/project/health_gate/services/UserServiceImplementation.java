package com.project.health_gate.services;

import com.project.health_gate.DTO.DtoUpdateUser;
import com.project.health_gate.entities.*;

import com.project.health_gate.repository.*;

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
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
    private final ApoointmentRepository apoointmentRepository;


    private final ApoointmentRepository appointmentRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;



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
        if(newUser.getProlfileImageUrl()!=null){
            currentUser.setProlfileImageUrl(newUser.getProlfileImageUrl());
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
    public boolean addDoctorToMyList(Long id,String username) {
        User doctor =userRepository.findOneById(id);

        User user=userRepository.findByUsername(username);



        return user.getMyDoctors().add(doctor);
    }

    @Override
    public List<User> getUsers() {
        List<User> allUsers = userRepository.findUsers();

        List<User> doctorstoconfirm = new ArrayList<>();
        for(User u: allUsers ){
            System.out.println(u.getSpecialities());

            if(u.getSpecialities()!=null){
                System.out.println(u);
                doctorstoconfirm.add(u);
            }
        }
        System.out.println(doctorstoconfirm);
        return doctorstoconfirm;
    }

    @Override
    public List<User> getDoctors() {
        List<User> allUsers = userRepository.findUsers();

        List<User> doctors = new ArrayList<>();
        for(User u: allUsers ){
            System.out.println(u.getRoles());
            if(u.getIsVerified()==true){
                System.out.println(u);
                doctors.add(u);
            }
        }
        System.out.println(doctors);
        return doctors;
    }

    @Override
    public void deletedoctorfromMyList(Long id,String username) {
        User doc=userRepository.findOneById(id);

        User user=userRepository.findByUsername(username);
        user.getMyDoctors().remove(doc);

    }

    @Override
    public List<User> getMyDoctors() {
        String username="";
        //get user loged in
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal). getUsername();
        } else {
            username = principal. toString();
        }
        User user=userRepository.findByUsername(username);
        return user.getMyDoctors();
    }

    //upload any file to a medical file
    public Document addFileToMedicalFile( MultipartFile multipartFile) throws IOException {
        String filename= StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        MedicalFile medicalfile = null;
        String username="";
        //get user loged in
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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



    @Override
    public void deleteFileFromMedicalFile(Long id) {
      documentRepository.deleteById(id);

    }

    @Override
    public void addDiscription(Long id, String disc) {
    Document doc =documentRepository.findByFileId(id);
    doc.setDiscription(disc);
    documentRepository.save(doc);
    }


    @Override
    public void SendAppointmentRequest(LocalDateTime date, Long id, String message) {
        String username="";
        Object principal = SecurityContextHolder. getContext(). getAuthentication(). getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal). getUsername();
        } else {
            username = principal. toString();
        }
        User user=userRepository.findByUsername(username);

        Appointment appointment=new Appointment();
        appointment.setPatient(user);
        appointment.setDoctor(userRepository.findOneById(id));

        appointment.setDate(date);
        appointment.setMessage(message);
        appointmentRepository.save(appointment);


    }

    @Override
    public void setAppointmentDate(Long id,LocalTime time) {
        Appointment app=appointmentRepository.findAppointmentById(id);
        app.setTime(time);


    }

    @Override
    public void makePost(String Content) {
        Post p =new Post();
        String username="";
        Object principal = SecurityContextHolder. getContext(). getAuthentication(). getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal). getUsername();
        } else {
            username = principal. toString();
        }
        User user=userRepository.findByUsername(username);
        p.setMaker(user);
        p.setContent(Content);
        p.setDate(new Date());
        p.setTime( LocalTime.now());

        postRepository.save(p);

    }

    @Override
    public void makeComment(String Content,Long id) {
        Comment c=new Comment();
        String username="";
        Object principal = SecurityContextHolder. getContext(). getAuthentication(). getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal). getUsername();
        } else {
            username = principal. toString();
        }
        User user=userRepository.findByUsername(username);
        c.setMaker(user);
        c.setContent(Content);
        c.setDate(new Date());
        c.setTime( LocalTime.now());
        c.setPost(postRepository.findOneById(id));

       commentRepository.save(c);

    }

    @Override
    public List<Post> showPosts() {
        return postRepository.findAll();
    }

    @Override
    public List<Comment> showCommentsPerPost(Long id) {
        Post post =postRepository.findOneById(id);


        return post.getComments();
    }

    @Override
    public void deleteMyPost(Long id) {
        Post post =postRepository.findOneById(id);

        postRepository.delete(post);

    }

    @Override
    public void deleteMyComment(Long id) {
        Comment comment =commentRepository.findOneById(id);

        commentRepository.delete(comment);

    }


    @Override
    public String addProfileImage(String imagePath) throws IOException {


        String username="";
        //get user loged in
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal). getUsername();
        } else {
            username = principal. toString();
        }
        User user=userRepository.findByUsername(username);
         user.setProlfileImageUrl("assets/img/doctors/"+imagePath);
        return "assets/img/doctors/"+imagePath;
    }

    @Override
    public String showrPofileImage() {
        String username="";
        //get user loged in
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal). getUsername();
        } else {
            username = principal. toString();
        }
        User user=userRepository.findByUsername(username);
        return user.getProlfileImageUrl();
    }

    @Override
    public List<Appointment> getAppointmentsAsUser() {
        String username="";
        //get user loged in
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal). getUsername();
        } else {
            username = principal. toString();
        }
        User user=userRepository.findByUsername(username);
        return  appointmentRepository.findAppointmentByPatient(user);



    }

    @Override
    public List<Appointment> getAppointmentAsDoctor() {
        String username="";
        //get user loged in
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal). getUsername();
        } else {
            username = principal. toString();
        }
        User user=userRepository.findByUsername(username);
        return  appointmentRepository.findAppointmentByDoctor(user);
    }

    @Override
    public void CancelApoointment(Long id) {
        appointmentRepository.delete(appointmentRepository.findAppointmentById(id));

    }

    @Override
    public void EditDate(Long id,LocalDateTime date) {
        Appointment app=appointmentRepository.findAppointmentById(id);

        app.setDate(date);
        app.setTime(null);
        app.setConfirmed(true);

    }

    @Override
    public String GetFileContent(Long id) throws FileNotFoundException,IOException {
        Document doc=documentRepository.findByFileId(id);
        File file= ResourceUtils.getFile("classpath:"+doc.getName());
        String content=new String((Files.readAllBytes(file.toPath())));
        System.out.println(content);

        return content;

    }

    @Override
    public void SetFileContent(Long id,String newContent) throws IOException {

        Document doc=documentRepository.findByFileId(id);


        Path fichier=Paths.get(doc.getName());
        Files.writeString(fichier,newContent,StandardOpenOption.APPEND);





    }

    @Override
    public List<User> getMyPatients() {
        List<User> myPatients=new ArrayList<>();
        String username="";
        //get user loged in
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal). getUsername();
        } else {
            username = principal. toString();
        }
        User user=userRepository.findByUsername(username);
        List<User> allusers=userRepository.findAllUsers();
        for(User u:allusers){
            if(u.getMyDoctors().contains(user))
                myPatients.add(u);

        }
        return myPatients;
    }
}
