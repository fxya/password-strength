package com.example.passwordstrength;

import jakarta.validation.constraints.NotNull;
import jakarta.websocket.OnMessage;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

@Component
@ServerEndpoint("/api/password/strength")
public class PasswordStrengthWebSocket implements WebSocketHandler {

    @OnMessage
    public void onMessage(Session session, String message) {
        int passwordStrength = calculatePasswordStrength(message);

        // Send the password strength back to the client
        session.getAsyncRemote().sendText(Integer.toString(passwordStrength));
    }

    private int calculatePasswordStrength(String password) {
        // Implement password strength calculation logic here
        return 0;
    }

    @Override
    public void afterConnectionEstablished(@NotNull WebSocketSession session) throws Exception {

    }

    @Override
    public void handleMessage(@NotNull WebSocketSession session, @NotNull WebSocketMessage<?> message) throws Exception {

    }

    @Override
    public void handleTransportError(@NotNull WebSocketSession session, @NotNull Throwable exception) throws Exception {

    }

    @Override
    public void afterConnectionClosed(@NotNull WebSocketSession session, @NotNull CloseStatus closeStatus) throws Exception {

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

}