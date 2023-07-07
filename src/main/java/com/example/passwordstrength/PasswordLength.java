package com.example.passwordstrength;

public enum PasswordLength {
    WEAK (8),
    MEDIUM(16),
    STRONG(25);

    private final int value;

    PasswordLength(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
