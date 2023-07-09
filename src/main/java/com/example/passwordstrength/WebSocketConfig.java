package com.example.passwordstrength;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final PasswordStrengthWebSocket passwordStrengthWebSocket;

    public WebSocketConfig(PasswordStrengthWebSocket passwordStrengthWebSocket) {
        this.passwordStrengthWebSocket = passwordStrengthWebSocket;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(passwordStrengthWebSocket, "/api/password/strength").setAllowedOrigins("*");
    }
}

