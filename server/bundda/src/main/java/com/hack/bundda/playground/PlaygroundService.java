package com.hack.bundda.playground;

import com.hack.bundda.member.domain.Member;
import com.hack.bundda.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlaygroundService {
    private Logger logger = Logger.getLogger(PlaygroundService.class.getName());
    private final MemberRepository memberRepository;
    public void request(String currentUserId, String targetUserId, List<WebSocketSession> list) {
        Member destination = memberRepository.findById(Long.valueOf(targetUserId)).orElseThrow();
        String sessionId = destination.getSessionId();
        for(WebSocketSession session : list) {
            if(session.getId().equals(sessionId)) {
                try {
                    session.sendMessage(new TextMessage("request:" + currentUserId));
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void agree(String currentUserId, String targetUserId, List<WebSocketSession> list) {
        // Create Chat Room
        Member destination = memberRepository.findById(Long.valueOf(targetUserId)).orElseThrow();
        Member source = memberRepository.findById(Long.valueOf(currentUserId)).orElseThrow();
        String dsessionId = destination.getSessionId();
        String sSessionId = source.getSessionId();
        for(WebSocketSession session : list) {
            if(session.getId().equals(dsessionId)) {
                try {
                    session.sendMessage(new TextMessage("agree:" + currentUserId));
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        for(WebSocketSession session : list) {
            if(session.getId().equals(sSessionId)) {
                try {
                    session.sendMessage(new TextMessage("agree:" + targetUserId));
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
