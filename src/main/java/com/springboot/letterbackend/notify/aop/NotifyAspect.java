package com.springboot.letterbackend.notify.aop;

import com.springboot.letterbackend.notify.aop.proxy.NotifyInfo;
import com.springboot.letterbackend.notify.common.NotifyMessage;
import com.springboot.letterbackend.notify.service.NotifyService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
@EnableAsync
public class NotifyAspect {
    private final NotifyService notifyService;

    public NotifyAspect(NotifyService notifyService) {
        this.notifyService = notifyService;
    }

    @Pointcut("@annotation(com.springboot.letterbackend.notify.NeedNotify)")
    public void annotationPointcut() {
    }
    @Async
    @AfterReturning(pointcut = "annotationPointcut()",returning = "result")
    public void checkValue(JoinPoint joinPoint, Object result) {
        NotifyInfo notifyProxy = (NotifyInfo) result;
        notifyService.send(
                notifyProxy.getReciver(),
                notifyProxy.getNotifyType(),
                NotifyMessage.NEW_FRIEND_REQUEST.getMessage(),
                "/api/v1/yata/" + (notifyProxy.getGoUrId())
        );
        log.info("result = {}", result);
    }
}
