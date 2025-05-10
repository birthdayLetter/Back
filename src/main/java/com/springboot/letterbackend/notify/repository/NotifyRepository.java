package com.springboot.letterbackend.notify.repository;

import com.springboot.letterbackend.notify.entity.Notify;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotifyRepository extends JpaRepository<Notify, Long> {
}
