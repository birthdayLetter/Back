package com.springboot.letterbackend.data.repository;

import com.springboot.letterbackend.data.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, Long> {
}
