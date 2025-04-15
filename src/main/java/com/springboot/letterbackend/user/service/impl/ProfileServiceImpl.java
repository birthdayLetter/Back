package com.springboot.letterbackend.user.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ProfileServiceImpl {
    private final String uploadDir="D:\\upload\\prifile\\";
    public String returnProfilePath(MultipartFile file) throws IOException {

        File directory=new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // 파일명 중복 방지를 위해 UUID 사용
        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String savedFileName = UUID.randomUUID().toString() + fileExtension;

        // 실제 저장 경로
        Path filePath = Paths.get(uploadDir + savedFileName);
        Files.write(filePath, file.getBytes());

        return uploadDir + savedFileName;


    }
}
