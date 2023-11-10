package com.hack.bundda.chat;

import com.hack.bundda.member.domain.Member;
import com.hack.bundda.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final MemberRepository memberRepository;
    public void sendMessage(String targetUserId, String content, List<WebSocketSession> list) {
        Member member = memberRepository.findById(Long.valueOf(targetUserId)).orElseThrow();
        String sessionId = member.getSessionId();
        for(WebSocketSession session : list) {
            if(session.getId().equals(sessionId)) {
                try {
                    TextMessage textMessage = new TextMessage(targetUserId + ":" + content);
                    session.sendMessage(textMessage);
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
