package com.hack.bundda.chat.handler;

import com.hack.bundda.chat.ChatService;
import com.hack.bundda.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ChatHandler extends TextWebSocketHandler {
    private static List<WebSocketSession> list = new ArrayList<>();
    private Logger logger = LoggerFactory.logger(ChatHandler.class);
    private final MemberService memberService;
    private final ChatService chatService;
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        logger.info("Client에서 전송한 메시지: " + payload);
        String[] split = payload.split(":");
        String cmd = split[0]; // connect, close, request, agree, reject
        String currentUserId = split[1];
        String targetUserId = split[2];
        String content = split[3];
        logger.info("cmd: " + cmd);
        logger.info("currentUserId: " + currentUserId);
        logger.info("targetUserId: " + targetUserId);
        logger.info("content: " + content);
        // ws://domain/api/ws/chat -> connect websocket -> sessionId
        // connect:1:-1:-1
        // send:1:2:안녕하세요
        switch (cmd) {
            case "connect":
                memberService.updateChatRoom(currentUserId, session.getId());
                break;
            case "close":
                memberService.closeChatRoom(currentUserId);
                break;
            case "send": // 대화 요청
                chatService.sendMessage(targetUserId, content, list);
                break;
        }

    }

    /* Client가 접속 시 호출되는 메서드 */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        list.add(session);
        session.sendMessage(new TextMessage(session.getId()));
        logger.info(session + " Chat 클라이언트 접속");
    }

    /* Client가 접속 해제 시 호출되는 메서드드 */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        logger.info(session + " Chat 클라이언트 접속 해제");
        list.remove(session);
    }
}