package com.hack.bundda.config;

import com.hack.bundda.chat.handler.ChatHandler;
import com.hack.bundda.playground.handler.PlaygroundHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@RequiredArgsConstructor
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final ChatHandler chatHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatHandler, "ws/playground").setAllowedOrigins("*");
        registry.addHandler(chatHandler, "ws/chat").setAllowedOrigins("*");
    }
    // api/ws/chat
    //
}