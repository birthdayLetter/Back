package com.springboot.letterbackend.data.repository;


import com.springboot.letterbackend.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User,Long> {

    User getByUid(String uid);

}
