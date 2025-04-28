package com.springboot.letterbackend.data.repository;


import com.springboot.letterbackend.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserRepository extends JpaRepository<User,Long> {

    User getByUid(String uid);

    User getUserById(Long id);
    User getByEmail(String email);
}
