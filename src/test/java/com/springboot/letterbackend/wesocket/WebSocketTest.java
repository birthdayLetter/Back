package com.springboot.letterbackend.wesocket;

import com.springboot.letterbackend.LetterBackendApplication;
import com.springboot.letterbackend.friend.dto.request.FriendMessageDTO;
import com.springboot.letterbackend.utils.WebSocketTestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = LetterBackendApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class WebSocketTest {

    @Autowired
    private WebSocketTestUtils utils;

    @Test
    public void testWebSocketConnectionAndFriendNotification() throws Exception {
        // 1. 웹소켓 서버 연결
        StompSession session = utils.connect();
        assertNotNull(session, "웹소켓 연결 실패");

        // 2. 메시지 수신을 위한 큐 생성 및 구독
        BlockingQueue<String> messages = new LinkedBlockingQueue<>();
        session.subscribe("/friend", new WebSocketTestUtils.DefaultStompFrameHandler(messages));

        // 3. 메시지 전송
        FriendMessageDTO friendMessageDTO = new FriendMessageDTO("tester", "receiver");
        session.send("/friend/apply", utils.toJson(friendMessageDTO));

        // 4. 응답 메시지 확인
        String response = messages.poll(5, TimeUnit.SECONDS);
        assertNotNull(response, "응답 메시지 수신 실패");
        assertEquals("📬 tester님이 친구 요청을 보냈습니다!", response, "응답 메시지 내용 불일치");

        // 5. 연결 종료 (테스트 종료 시 자동 처리될 수 있지만 명시적으로 작성)
        if (session.isConnected()) {
            session.disconnect();
        }
    }
}