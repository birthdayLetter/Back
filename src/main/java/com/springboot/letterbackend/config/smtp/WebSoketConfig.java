//package com.springboot.letterbackend.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.simp.config.ChannelRegistration;
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.socket.config.annotation.*;
//
//@Configuration
//@EnableWebSocket
//@RequiredArgsConstructor
//@EnableWebMvc
//public class WebSoketConfig implements WebSocketConfigurer {
//
//    private final ChatWebSocketHandler chatWebSocketHandler;
//
//    /**
//     * WebSocket 연결을 위해서 Handler를 구성합니다.
//     *
//     * @param registry
//     */
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        System.out.println("[+] 최초 WebSocket 연결을 위한 등록 Handler");
//        registry
//                // 클라이언트에서 웹 소켓 연결을 위해 "ws-stomp"라는 엔드포인트로 연결을 시도하면 ChatWebSocketHandler 클래스에서 이를 처리합니다.
//                .addHandler(chatWebSocketHandler, "/ws")
//                // 접속 시도하는 모든 도메인 또는 IP에서 WebSocket 연결을 허용합니다.
//                .setAllowedOriginPatterns("*")
//                ;
//    }
//}
//