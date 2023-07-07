package com.example.passwordstrength;

import org.junit.jupiter.api.Test;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PasswordStrengthWebSocketTest {

    @Test
    public void testHandleMessage() throws IOException {
        // Create a mock WebSocketSession
        WebSocketSession session = mock(WebSocketSession.class);

        // Create a mock WebSocketMessage
        String messagePayload = "password123";
        WebSocketMessage<String> message = new TextMessage(messagePayload);

        // Create an instance of the PasswordStrengthWebSocket
        PasswordStrengthWebSocket passwordStrengthWebSocket = new PasswordStrengthWebSocket();

        // Call the handleMessage method
        passwordStrengthWebSocket.handleMessage(session, message);

        // Verify that the session sends the correct message back to the client
        verify(session).sendMessage(new TextMessage("11")); // Adjust the expected response based on your password strength logic
    }

    @Test
    public void testHandleMessageWithEmptyString_returnsZero() throws IOException {
        // Create a mock WebSocketSession
        WebSocketSession session = mock(WebSocketSession.class);

        // Create a mock WebSocketMessage
        WebSocketMessage<String> message = new TextMessage("");

        // Create an instance of the PasswordStrengthWebSocket
        PasswordStrengthWebSocket passwordStrengthWebSocket = new PasswordStrengthWebSocket();

        // Call the handleMessage method
        passwordStrengthWebSocket.handleMessage(session, message);

        // Verify that the session sends the correct message back to the client
        verify(session).sendMessage(new TextMessage("0")); // Adjust the expected response based on your password strength logic
    }


}
