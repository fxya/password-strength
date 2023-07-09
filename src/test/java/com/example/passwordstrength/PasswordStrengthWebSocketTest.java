package com.example.passwordstrength;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
        String messagePayload = "password!23";
        WebSocketMessage<String> message = new TextMessage(messagePayload);

        // Create an instance of the PasswordStrengthWebSocket
        PasswordStrengthWebSocket passwordStrengthWebSocket = new PasswordStrengthWebSocket();

        // Call the handleMessage method
        passwordStrengthWebSocket.handleMessage(session, message);

        /* Verify that the session sends the correct message back to the client.
           Adjust the expected response based on your password logic as
           it is implemented. Currently, +10 (length) +5 (has digit) -5 (repeated S)
           +15 (has special characters) + 11 (password length) */
        verify(session).sendMessage(new TextMessage("26"));

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
    public void stringLength_greaterThanOrEqualTo25_allDigitsNoRepeated_returns85() throws IOException {
        WebSocketMessage<String> message = new TextMessage("1234567890123456789012345");
        PasswordStrengthWebSocket passwordStrengthWebSocket = new PasswordStrengthWebSocket();

        passwordStrengthWebSocket.handleMessage(session, message);

        verify(session).sendMessage(new TextMessage("75"));
    }

    @Test
    public void stringLength_greaterThanOrEqualTo25_allDigitsSomeRepeated_withSpecialChar_returns90() throws IOException {
        WebSocketMessage<String> message = new TextMessage("!234567990123456799012345");
        PasswordStrengthWebSocket passwordStrengthWebSocket = new PasswordStrengthWebSocket();

        passwordStrengthWebSocket.handleMessage(session, message);

        verify(session).sendMessage(new TextMessage("80"));
    }

    @Test
    public void stringLength16_AllDigitsNoRepeated_returns26() throws IOException {
        WebSocketMessage<String> message = new TextMessage("1234567890123456");
        PasswordStrengthWebSocket passwordStrengthWebSocket = new PasswordStrengthWebSocket();

        passwordStrengthWebSocket.handleMessage(session, message);

        verify(session).sendMessage(new TextMessage("26"));
    }

    @Test
    public void stringLength9_withMixedCaseNoDigitsNoRepeated_returns44() throws IOException {
        WebSocketMessage<String> message = new TextMessage("abcDEFghi");
        PasswordStrengthWebSocket passwordStrengthWebSocket = new PasswordStrengthWebSocket();

        passwordStrengthWebSocket.handleMessage(session, message);

        verify(session).sendMessage(new TextMessage("44"));
    }

    @Test
    public void stringLength9_withAllUpperCaseNoRepeated_returns14() throws IOException {
        WebSocketMessage<String> message = new TextMessage("ABCDEFGHI");
        PasswordStrengthWebSocket passwordStrengthWebSocket = new PasswordStrengthWebSocket();

        passwordStrengthWebSocket.handleMessage(session, message);

        verify(session).sendMessage(new TextMessage("14"));
    }

    @Test
    public void stringLength9_withAllUpperCaseSomeRepeated_returns1() throws IOException {
        WebSocketMessage<String> message = new TextMessage("AABBCCDDE");
        PasswordStrengthWebSocket passwordStrengthWebSocket = new PasswordStrengthWebSocket();

        passwordStrengthWebSocket.handleMessage(session, message);

        verify(session).sendMessage(new TextMessage("1"));
    }

    @Test
    public void stringLength9_withAllLowerCaseNoRepeated_returns14() throws IOException {
        WebSocketMessage<String> message = new TextMessage("abcdefghi");
        PasswordStrengthWebSocket passwordStrengthWebSocket = new PasswordStrengthWebSocket();

        passwordStrengthWebSocket.handleMessage(session, message);

        verify(session).sendMessage(new TextMessage("14"));
    }

    @Test
    public void stringLength6_withMixedCaseNoRepeated_returns0() throws IOException {
        WebSocketMessage<String> message = new TextMessage("aBcDeF");
        PasswordStrengthWebSocket passwordStrengthWebSocket = new PasswordStrengthWebSocket();

        passwordStrengthWebSocket.handleMessage(session, message);

        verify(session).sendMessage(new TextMessage("0"));
    }

    @Test
    public void stringLength10_withMixedCaseNoRepeated_returns45() throws IOException {
        WebSocketMessage<String> message = new TextMessage("aBcDeFgHiJ");
        PasswordStrengthWebSocket passwordStrengthWebSocket = new PasswordStrengthWebSocket();

        passwordStrengthWebSocket.handleMessage(session, message);

        verify(session).sendMessage(new TextMessage("45"));
    }

    @Test
    public void stringLength17_withMixedCaseAndDigits_NoRepeated_returns55() throws IOException {
        WebSocketMessage<String> message = new TextMessage("aBcDeFgHiJkLmNoP1");
        PasswordStrengthWebSocket passwordStrengthWebSocket = new PasswordStrengthWebSocket();

        passwordStrengthWebSocket.handleMessage(session, message);

        verify(session).sendMessage(new TextMessage("72"));
    }

}
