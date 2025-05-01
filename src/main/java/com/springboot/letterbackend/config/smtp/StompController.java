package com.springboot.letterbackend.config.smtp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class StompController {
    private final SimpMessagingTemplate template;
    Logger logger = LoggerFactory.getLogger(StompController.class);

    public StompController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping(value = "/messages2/{userId}")  // 클라이언트에서 /pub/messages 로 보냄
    public void send(@Payload ChatMessageDto chatMessageDto, @DestinationVariable String userId) throws Exception {
        logger.info("stomp 컨트롤러가 작동합니다. chatMessageDto:{} ",chatMessageDto);
        // /sub/message 를 구독한 클라이언트에게 전송
        template.convertAndSend("/sub/message/"+userId, chatMessageDto);
    }
}
