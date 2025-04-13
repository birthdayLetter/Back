package com.springboot.letterbackend.data.entity;


import jakarta.persistence.*;

@Table(name = "friend")
@Entity
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    String user_id;
    @Column(nullable = false)
    String friend_id;
    @Column(nullable = false)
    Status status;

}
