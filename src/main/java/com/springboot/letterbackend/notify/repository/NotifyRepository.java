package com.springboot.letterbackend.notify.repository;

import com.springboot.letterbackend.notify.entity.Notify;
import org.springframework.data.jpa.repository.JpaRepository;
// 알림객체를 저장하고 관리
public interface NotifyRepository extends JpaRepository<Notify, Long> {
}
