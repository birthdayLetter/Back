package com.springboot.letterbackend.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.concurrent.*;

@Component
public class WebSocketTestUtils {


    private int port=8080;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public StompSession connect() throws InterruptedException, ExecutionException, TimeoutException {
        WebSocketStompClient stompClient = new WebSocketStompClient(new StandardWebSocketClient());
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        return stompClient
                .connectAsync("ws://localhost:" + port + "/ws", new WebSocketHttpHeaders(), new StompSessionHandlerAdapter() {})
                .get(5, TimeUnit.SECONDS);
    }

    public String toJson(Object obj) throws Exception {
        return objectMapper.writeValueAsString(obj);
    }

    // 메시지 핸들러
    public static class DefaultStompFrameHandler implements org.springframework.messaging.simp.stomp.StompFrameHandler {
        private final BlockingQueue<String> queue;

        public DefaultStompFrameHandler(BlockingQueue<String> queue) {
            this.queue = queue;
        }

        @Override
        public Type getPayloadType(org.springframework.messaging.simp.stomp.StompHeaders headers) {
            return String.class;
        }

        @Override
        public void handleFrame(org.springframework.messaging.simp.stomp.StompHeaders headers, Object payload) {
            queue.offer((String) payload);
        }
    }
}