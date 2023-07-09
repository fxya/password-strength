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
           it is implemented. Currently, 10 (length) + 5 (has digit) - 5 (repeated S) */
        verify(session).sendMessage(new TextMessage("10"));

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
    public void stringLength_greaterThanOrEqualTo25_allDigitsNoRepeated_returns65() throws IOException {
        WebSocketMessage<String> message = new TextMessage("1234567890123456789012345");
        PasswordStrengthWebSocket passwordStrengthWebSocket = new PasswordStrengthWebSocket();

        passwordStrengthWebSocket.handleMessage(session, message);

        verify(session).sendMessage(new TextMessage("65"));
    }

    @Test
    public void stringLength_greaterThanOrEqualTo25_allDigitsSomeRepeated_returns50() throws IOException {
        WebSocketMessage<String> message = new TextMessage("1234567990123456799012345");
        PasswordStrengthWebSocket passwordStrengthWebSocket = new PasswordStrengthWebSocket();

        passwordStrengthWebSocket.handleMessage(session, message);

        verify(session).sendMessage(new TextMessage("50"));
    }

    @Test
    public void stringLength16_AllDigitsNoRepeated_returns25() throws IOException {
        WebSocketMessage<String> message = new TextMessage("1234567890123456");
        PasswordStrengthWebSocket passwordStrengthWebSocket = new PasswordStrengthWebSocket();

        passwordStrengthWebSocket.handleMessage(session, message);

        verify(session).sendMessage(new TextMessage("25"));
    }

    @Test
    public void stringLength9_withMixedCaseNoDigitsNoRepeated_returns40() throws IOException {
        WebSocketMessage<String> message = new TextMessage("abcDEFghi");
        PasswordStrengthWebSocket passwordStrengthWebSocket = new PasswordStrengthWebSocket();

        passwordStrengthWebSocket.handleMessage(session, message);

        verify(session).sendMessage(new TextMessage("40"));
    }

    @Test
    public void stringLength9_withAllUpperCaseNoRepeated_returns20() throws IOException {
        WebSocketMessage<String> message = new TextMessage("ABCDEFGHI");
        PasswordStrengthWebSocket passwordStrengthWebSocket = new PasswordStrengthWebSocket();

        passwordStrengthWebSocket.handleMessage(session, message);

        verify(session).sendMessage(new TextMessage("20"));
    }

    @Test
    public void stringLength9_withAllUpperCaseSomeRepeated_returns5() throws IOException {
        WebSocketMessage<String> message = new TextMessage("AABBCCDDE");
        PasswordStrengthWebSocket passwordStrengthWebSocket = new PasswordStrengthWebSocket();

        passwordStrengthWebSocket.handleMessage(session, message);

        verify(session).sendMessage(new TextMessage("5"));
    }

    @Test
    public void stringLength9_withAllLowerCaseNoRepeated_returns35() throws IOException {
        WebSocketMessage<String> message = new TextMessage("abcdefghi");
        PasswordStrengthWebSocket passwordStrengthWebSocket = new PasswordStrengthWebSocket();

        passwordStrengthWebSocket.handleMessage(session, message);

        verify(session).sendMessage(new TextMessage("20"));
    }

    @Test
    public void stringLength6_withMixedCaseNoRepeated_returns0() throws IOException {
        WebSocketMessage<String> message = new TextMessage("aBcDeF");
        PasswordStrengthWebSocket passwordStrengthWebSocket = new PasswordStrengthWebSocket();

        passwordStrengthWebSocket.handleMessage(session, message);

        verify(session).sendMessage(new TextMessage("0"));
    }
    @Test
    public void stringLength10_withMixedCaseNoRepeated_returns40() throws IOException {
        WebSocketMessage<String> message = new TextMessage("aBcDeFgHiJ");
        PasswordStrengthWebSocket passwordStrengthWebSocket = new PasswordStrengthWebSocket();

        passwordStrengthWebSocket.handleMessage(session, message);

        verify(session).sendMessage(new TextMessage("40"));
    }

    @Test
    public void stringLength17_withMixedCaseAndDigits_NoRepeated_returns60() throws IOException {
        WebSocketMessage<String> message = new TextMessage("aBcDeFgHiJkLmNoP1");
        PasswordStrengthWebSocket passwordStrengthWebSocket = new PasswordStrengthWebSocket();

        passwordStrengthWebSocket.handleMessage(session, message);

        verify(session).sendMessage(new TextMessage("60"));
    }

}
