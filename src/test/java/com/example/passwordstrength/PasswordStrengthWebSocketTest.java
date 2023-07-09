package com.example.passwordstrength;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PasswordStrengthWebSocketTest {
    private WebSocketSession session;

    @BeforeEach
    public void setUp() {
        // Create a mock WebSocketSession
        session = mock(WebSocketSession.class);
    }

    @Test
    public void testHandleMessage() throws IOException {
        // Create a mock WebSocketMessage
        String messagePayload = "password123";
        WebSocketMessage<String> message = new TextMessage(messagePayload);

        // Create an instance of the PasswordStrengthWebSocket
        PasswordStrengthWebSocket passwordStrengthWebSocket = new PasswordStrengthWebSocket();

        // Call the handleMessage method
        passwordStrengthWebSocket.handleMessage(session, message);

        /* Verify that the session sends the correct message back to the client.
           Adjust the expected response based on your password logic as
           it is implemented. */
        verify(session).sendMessage(new TextMessage("15"));

    }

    @Test
    public void emptyString_returns0() throws IOException {
        WebSocketMessage<String> message = new TextMessage("");
        PasswordStrengthWebSocket passwordStrengthWebSocket = new PasswordStrengthWebSocket();

        passwordStrengthWebSocket.handleMessage(session, message);

        verify(session).sendMessage(new TextMessage("0"));
    }

    @Test
    public void stringLength_lessThanOrEqualToEight_returns0() throws IOException {
        WebSocketMessage<String> message = new TextMessage("1234567");
        PasswordStrengthWebSocket passwordStrengthWebSocket = new PasswordStrengthWebSocket();

        passwordStrengthWebSocket.handleMessage(session, message);

        verify(session).sendMessage(new TextMessage("0"));
    }
    @Test
    public void stringLength_greaterThanOrEqualTo25_returns50() throws IOException {
        WebSocketMessage<String> message = new TextMessage("1234567890123456789012345");
        PasswordStrengthWebSocket passwordStrengthWebSocket = new PasswordStrengthWebSocket();

        passwordStrengthWebSocket.handleMessage(session, message);

        verify(session).sendMessage(new TextMessage("55"));
    }
    @Test
    public void stringLength16_AllDigits_returns15() throws IOException {
        WebSocketMessage<String> message = new TextMessage("1234567890123456");
        PasswordStrengthWebSocket passwordStrengthWebSocket = new PasswordStrengthWebSocket();

        passwordStrengthWebSocket.handleMessage(session, message);

        verify(session).sendMessage(new TextMessage("15"));
    }

    @Test
    public void stringLength9_withMixedCaseNoDigits_returns30() throws IOException {
        WebSocketMessage<String> message = new TextMessage("abcDEFghi");
        PasswordStrengthWebSocket passwordStrengthWebSocket = new PasswordStrengthWebSocket();

        passwordStrengthWebSocket.handleMessage(session, message);

        verify(session).sendMessage(new TextMessage("30"));
    }

    @Test
    public void stringLength9_withAllUpperCase_returns10() throws IOException {
        WebSocketMessage<String> message = new TextMessage("ABCDEFGHI");
        PasswordStrengthWebSocket passwordStrengthWebSocket = new PasswordStrengthWebSocket();

        passwordStrengthWebSocket.handleMessage(session, message);

        verify(session).sendMessage(new TextMessage("10"));
    }

    @Test
    public void stringLength9_withAllLowerCase_returns10() throws IOException {
        WebSocketMessage<String> message = new TextMessage("abcdefghi");
        PasswordStrengthWebSocket passwordStrengthWebSocket = new PasswordStrengthWebSocket();

        passwordStrengthWebSocket.handleMessage(session, message);

        verify(session).sendMessage(new TextMessage("10"));
    }

    @Test
    public void stringLength6_withMixedCaseNoRepeated_returns0() throws IOException {
        WebSocketMessage<String> message = new TextMessage("aBcDeF");
        PasswordStrengthWebSocket passwordStrengthWebSocket = new PasswordStrengthWebSocket();

        passwordStrengthWebSocket.handleMessage(session, message);

        verify(session).sendMessage(new TextMessage("0"));
    }
    @Test
    public void stringLength10_withMixedCaseNoRepeated_returns30() throws IOException {
        WebSocketMessage<String> message = new TextMessage("aBcDeFgHiJ");
        PasswordStrengthWebSocket passwordStrengthWebSocket = new PasswordStrengthWebSocket();

        passwordStrengthWebSocket.handleMessage(session, message);

        verify(session).sendMessage(new TextMessage("30"));
    }

    @Test
    public void stringLength17_withMixedCaseAndDigits_NoRepeated_returns50() throws IOException {
        WebSocketMessage<String> message = new TextMessage("aBcDeFgHiJkLmNoP1");
        PasswordStrengthWebSocket passwordStrengthWebSocket = new PasswordStrengthWebSocket();

        passwordStrengthWebSocket.handleMessage(session, message);

        verify(session).sendMessage(new TextMessage("50"));
    }

}
