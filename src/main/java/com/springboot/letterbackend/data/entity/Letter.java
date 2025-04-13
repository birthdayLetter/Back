package com.springboot.letterbackend.data.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Table(name = "letter")
@Entity
public class Letter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    String imageUrl;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    String reciverId;

    @Column(nullable = false)
    String senderId;

    @Column(nullable = false)
    int year; //생일편지보낸년도 구분

    @Column(nullable = false)
    LocalDateTime createdDate;
}
