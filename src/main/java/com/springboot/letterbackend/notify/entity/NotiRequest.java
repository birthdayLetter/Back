package com.springboot.letterbackend.notify.entity;

import com.springboot.letterbackend.data.entity.User;
import com.springboot.letterbackend.notify.aop.proxy.NotifyInfo;
import com.springboot.letterbackend.notify.common.NotificationType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.Auditable;

import java.time.temporal.TemporalAccessor;
import java.util.Optional;
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NotiRequest implements NotifyInfo, Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notiRequestId;

    @Column(length = 100,nullable = false)
    private String titile;

    @Override
    public User getReciver() {
        return null;
    }

    @Override
    public Long getGoUrId() {
        return 0L;
    }

    @Override
    public NotificationType getNotifyType() {
        return NotificationType.FRIEND;
    }

    @Override
    public Optional getCreatedBy() {
        return Optional.empty();
    }

    @Override
    public void setCreatedBy(Object createdBy) {

    }

    @Override
    public Optional getCreatedDate() {
        return Optional.empty();
    }

    @Override
    public void setCreatedDate(TemporalAccessor creationDate) {

    }

    @Override
    public Optional getLastModifiedBy() {
        return Optional.empty();
    }

    @Override
    public void setLastModifiedBy(Object lastModifiedBy) {

    }

    @Override
    public Optional getLastModifiedDate() {
        return Optional.empty();
    }

    @Override
    public void setLastModifiedDate(TemporalAccessor lastModifiedDate) {

    }

    @Override
    public Object getId() {
        return null;
    }

    @Override
    public boolean isNew() {
        return false;
    }

    public void setNotiRequestId(Long notiRequestId) {
        this.notiRequestId = notiRequestId;
    }

    public Long getNotiRequestId() {
        return notiRequestId;
    }
}
