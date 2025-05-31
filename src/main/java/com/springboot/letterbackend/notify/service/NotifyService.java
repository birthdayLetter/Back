package com.springboot.letterbackend.notify.service;

import com.springboot.letterbackend.data.entity.User;
import com.springboot.letterbackend.notify.common.NotificationType;
import com.springboot.letterbackend.notify.dto.NotifyDto;
import com.springboot.letterbackend.notify.entity.Notify;
import com.springboot.letterbackend.notify.repository.EmitterRepository;
import com.springboot.letterbackend.notify.repository.NotifyRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;

@Service
public class NotifyService {
    private static final Long DEFAULT_TIMEOUT=600L*1000*60;
    private final EmitterRepository emitterRepository;
    private final NotifyRepository notifyRepository;


    public NotifyService(EmitterRepository emitterRepository, NotifyRepository notifyRepository) {
        this.emitterRepository = emitterRepository;
        this.notifyRepository = notifyRepository;
    }

    public SseEmitter subscribe(String email,String lastEventId){
        String emitterId=makeTimeIncludeId(email);
        SseEmitter emitter =emitterRepository.save(emitterId,new SseEmitter(DEFAULT_TIMEOUT));
        emitter.onCompletion(()-> emitterRepository.deleteById(emitterId));
        emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));

        //503 에러를 방지하기 위한 더미 이벤트 전송
        String eventId=makeTimeIncludeId(email);
        sendNotification(emitter,eventId,emitterId,"EventStream Created. [userEmail="+email+"]");

        //클라이언트가 미수신한 Event 목록이 존재할 경우 전송하여 Event 유실을 예방
        if(hasLostData(lastEventId)){
            sendLostData(lastEventId,email,emitterId,emitter);
        }
        return emitter;


    }
    private String makeTimeIncludeId(String email) {
        return email+"_"+System.currentTimeMillis();

    }
    private void sendNotification(SseEmitter emitter,String eventId, String emitterId,Object data){
        try{
            emitter.send(SseEmitter.event()
                    .id(eventId)
                    .name("sse")
                    .data(data)
            );
        }catch (IOException e){
            emitterRepository.deleteById(emitterId);
        }
    }
    private boolean hasLostData(String lastEventId) {
        return !lastEventId.isEmpty();
    }
    private void sendLostData(String lastEventId,String userEmail,String emitterId,SseEmitter emitter){
        Map<String,Object> eventCaches=emitterRepository.findAllEventCacheStartWithByMemberId(String.valueOf(userEmail));
        eventCaches.entrySet().stream()
                .filter(entry-> lastEventId.compareTo(entry.getKey())<0)
                .forEach((entry)->sendNotification(emitter,entry.getKey(),emitterId,entry.getValue()));
    }

    public void send(User receiver, NotificationType notificationType,String content,String url){
        Notify notification = notifyRepository.save(createNotification(receiver, notificationType, content, url));

        String receiverEmail = receiver.getEmail();
        String eventId = receiverEmail + "_" + System.currentTimeMillis();
        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByMemberId(receiverEmail);
        emitters.forEach(
                (key, emitter) -> {
                    emitterRepository.saveEventCache(key, notification);
                    sendNotification(emitter, eventId, key, NotifyDto.Response.createResponse(notification));
                }
        );
    }
    private Notify createNotification(User receiver, NotificationType notificationType, String content, String url) {
        return Notify.builder()
                .receiver(receiver)
                .notificationType(notificationType)
                .content(content)
                .url(url)
                .isRead(false)
                .build();
    }

}