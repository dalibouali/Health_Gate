package com.project.health_gate.repository;

import com.project.health_gate.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
    User deleteUserById(Long id);


@Query("select  u from User u where u.id=:id ")
    User findOneById(@Param("id")Long  id);

    @Query(" select  u from User u")
    List<User> findAllUsers();

    @Query(" select  new User(u.id, u.firstName, u.lastName, u.username, u.phone,u.password, u.prolfileImageUrl,u.specialities,  u.gender,u.IsVerified,u.bio) from User u")
    List<User> findUsers();




}
