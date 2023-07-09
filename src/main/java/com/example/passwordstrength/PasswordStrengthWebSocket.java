package com.example.passwordstrength;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Component
public class PasswordStrengthWebSocket implements WebSocketHandler {

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws IOException {
        String password = message.getPayload().toString();
        int passwordStrength = calculatePasswordStrength(password);

        // Send the password strength back to the client
        session.sendMessage(new TextMessage(Integer.toString(passwordStrength)));
    }

    private int calculatePasswordStrength(String password) {
        int MAX_SCORE = 100;
        int score = 0;

        if (password.length() < PasswordLength.WEAK.getValue()) {
            return 0;
        }

        if (password.length() >= PasswordLength.STRONG.getValue()) {
            score += 50;
        } else if (password.length() > PasswordLength.MEDIUM.getValue()) {
            score += 25;
        } else if (password.length() > PasswordLength.WEAK.getValue()) {
            score += 10;
        }

        if (hasUpperAndLowerCase(password)) {
            score += 20;
        }
        if (hasDigit(password)) {
            score += 5;
        }

        return Math.min(score, MAX_SCORE);
    }


    private boolean hasUpperAndLowerCase(String password) {
        return !password.equals(password.toLowerCase()) && !password.equals(password.toUpperCase());
    }

    private boolean hasDigit(String password) {
        String containsDigit = ".*\\d.*";
        return password.matches(containsDigit);
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
