package com.springboot.letterbackend.notify.aop.proxy;

import com.springboot.letterbackend.data.entity.User;
import com.springboot.letterbackend.notify.common.NotificationType;

public interface NotifyInfo {
    User getReciver();
    Long getGoUrId();
    NotificationType getNotifyType();
}
