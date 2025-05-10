package com.springboot.letterbackend.letter.repository;

import com.springboot.letterbackend.letter.entity.LetterTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LetterTemplateRepository extends JpaRepository<LetterTemplate,Long> {

    // 모든 카드 템플릿을 조회합니다.
    @Override
    List<LetterTemplate> findAll();

    //특정 템플릿 아이디를 조회합니다.
    LetterTemplate findLetterById(long id);

}
