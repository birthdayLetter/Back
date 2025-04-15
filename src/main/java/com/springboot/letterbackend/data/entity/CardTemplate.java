package com.springboot.letterbackend.data.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "card_template")
public class CardTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    String imgaeUrl;
}
