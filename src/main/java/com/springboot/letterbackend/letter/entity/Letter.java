package com.springboot.letterbackend.letter.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Table
@Entity
@Getter
@Setter
public class Letter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    LetterTemplate letterTemplate;

    @Column(nullable = false)
    @Lob
    private String content;

    @Column(nullable = false)
    String fromUserId;

    @Column(nullable = false)
    String toUserId;

    @Column(nullable = false)
    int year; //생일편지보낸년도 구분

    @Column(nullable = false)
    LocalDateTime createdDate;
}
