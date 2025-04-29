package com.springboot.letterbackend.data.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @ToString.Exclude // 순환참조오류 방지
    @JoinColumn(name = "from_user_id",referencedColumnName = "id")
    private User fromUser; // 신청자 Id

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @ToString.Exclude // 순환참조오류 방지
    @JoinColumn(name = "to_user_id",referencedColumnName = "id")
    private User toUser; //신청받은자 ID

    @Column(nullable = false)
    private Status status;

}
