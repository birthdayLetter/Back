package com.springboot.letterbackend.data.repository;

import com.springboot.letterbackend.data.entity.Friend;
import com.springboot.letterbackend.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, Long> {
   //Friend findFriendByUserId(Long userId);
   Friend findFriendByFromUserIdAndToUser(Long userId, User user);

}
