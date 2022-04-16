package com.project.health_gate.services;

import com.project.health_gate.entities.User;
import com.project.health_gate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j //for log
public class AdminServiceImplmentation implements AdminService{

    private final UserRepository userRepository;




    @Override
    public User confirm_doctor(Long id) {


        User doctorToConfirm=userRepository.findOneById(id);


        if(doctorToConfirm!=null){
            doctorToConfirm.setIsVerified(true);
            userRepository.save(doctorToConfirm);
        }else{
            System.out.println("User not found");

        }
        return  doctorToConfirm;




    }
}
