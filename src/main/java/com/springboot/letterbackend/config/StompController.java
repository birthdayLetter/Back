package com.springboot.letterbackend.config;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StompController {
    private final SimpMessagingTemplate template;

    public StompController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/messages")  // 클라이언트에서 /pub/messages 로 보냄
    public void send(ChatMessageDto chatMessageDto) throws Exception {
        // /sub/message 를 구독한 클라이언트에게 전송
        template.convertAndSend("/sub/message/"+chatMessageDto.getSender(), chatMessageDto);
    }
}
