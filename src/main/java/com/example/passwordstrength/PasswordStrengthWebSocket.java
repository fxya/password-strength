package com.example.passwordstrength;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Component
public class PasswordStrengthWebSocket implements WebSocketHandler {

    final private PasswordStrengthCalculator passwordStrengthCalculator;

    @Autowired
    public PasswordStrengthWebSocket(PasswordStrengthCalculator passwordStrengthCalculator) {
        this.passwordStrengthCalculator = passwordStrengthCalculator;
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws IOException {
        String password = message.getPayload().toString();
        int passwordStrength = passwordStrengthCalculator.calculatePasswordStrength(password);

        // Send the password strength back to the client
        session.sendMessage(new TextMessage(Integer.toString(passwordStrength)));
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Connection established");
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.err.println(exception.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        System.out.println("Connection closed");
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

}
