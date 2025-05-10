package com.springboot.letterbackend.letter.repository;

import com.springboot.letterbackend.letter.entity.Letter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LetterRepository extends JpaRepository<Letter, Long> {


    List<Letter> getLetterByYearAndToUserId(int year,String toUserId);
    List<Letter> getLetterByToUserId(String toUserId);
}
