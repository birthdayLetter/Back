package com.springboot.letterbackend.notify.repository;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

public interface EmitterRepository {
    SseEmitter save(String emitterId, SseEmitter sseEmitter); //sse이벤트 전송객체 저장
    void saveEventCache(String emitterId, Object event);//이벤트 캐시아이디와 이벤트 객체를 받아 저장
    Map<String, SseEmitter> findAllEmitterStartWithByMemberId(String memberId);//주어진 memberId로 시작하는 모든 Emitter를 가져옴
    Map<String, Object> findAllEventCacheStartWithByMemberId(String memberId);
    void deleteById(String id);//tkrwp
    void deleteAllEmitterStartWithId(String memberId);//해당회원과 관련된 모든 Emitter를 지운
    void deleteAllEventCacheStartWithId(String memberId);//해당 회원과 관련된 모든 이벤트를 지움.
}
