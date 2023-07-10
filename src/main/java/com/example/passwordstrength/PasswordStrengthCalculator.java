package com.example.passwordstrength;

import org.springframework.stereotype.Component;

@Component
public class PasswordStrengthCalculator {

    public int calculatePasswordStrength(String password) {
        int MAX_SCORE = 100;
        int score = 0;
        int submittedPasswordLength = password.length();

        if (submittedPasswordLength < PasswordLength.WEAK.getValue()) {
            return 0;
        }

        if (submittedPasswordLength >= PasswordLength.STRONG.getValue()) {
            score += 50;
        } else if (submittedPasswordLength> PasswordLength.MEDIUM.getValue()) {
            score += 25;
        } else if (submittedPasswordLength > PasswordLength.WEAK.getValue()) {
            score += 10;
        }

        if (hasDigit(password)) {
            score += 5;
        }

        score = hasUpperAndLowerCase(password) ? score + 20 : score - 10;
        score = hasRepeatedCharacters(password) ? score - 5 : score + 10;
        score = hasSpecialCharacters(password) ? score + 15 : score - 5;
        score += submittedPasswordLength; // Add 1 point for each character
        return Math.abs(Math.min(score, MAX_SCORE));
    }

    private boolean hasUpperAndLowerCase(String password) {
        return !password.equals(password.toLowerCase()) && !password.equals(password.toUpperCase());
    }

    private boolean hasDigit(String password) {
        String containsDigit = ".*\\d.*";
        return password.matches(containsDigit);
    }

    private boolean hasRepeatedCharacters(String password) {
        // Check up until the second to last character
        for (int i = 0; i < password.length() - 1; i++) {
            if (password.charAt(i) == password.charAt(i + 1)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasSpecialCharacters(String password) {
        String containsSpecialCharacters = ".*[!@#$%^&*()-+<>,.].*";
        return password.matches(containsSpecialCharacters);
    }

}
