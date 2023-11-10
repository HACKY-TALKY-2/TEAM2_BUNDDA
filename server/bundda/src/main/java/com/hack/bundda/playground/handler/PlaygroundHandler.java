package com.hack.bundda.playground.handler;

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
public class PlaygroundHandler extends TextWebSocketHandler {
    private Logger logger = LoggerFactory.logger(PlaygroundHandler.class);
    private static List<WebSocketSession> list = new ArrayList<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        logger.info("Client에서 전송한 메시지: " + payload);
        TextMessage textMessage = new TextMessage(session.getId());
        session.sendMessage(textMessage);
        for(WebSocketSession sess: list) {
            logger.info("Seesion Id : " + sess.getId());
            sess.sendMessage(textMessage);
        }
    }

    /* Client가 접속 시 호출되는 메서드 */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        list.add(session);
        logger.info(session + " PlayGround 클라이언트 접속");
    }

    /* Client가 접속 해제 시 호출되는 메서드드 */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        logger.info(session + " PlayGround 클라이언트 접속 해제");
        list.remove(session);
    }
}
