package com.springboot.letterbackend.data.repository;

import com.springboot.letterbackend.data.entity.Letter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LetterRepository extends JpaRepository<Letter, Long> {
}
