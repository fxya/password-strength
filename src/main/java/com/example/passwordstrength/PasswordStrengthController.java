package com.example.passwordstrength;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PasswordStrengthController {

    @GetMapping("/password/strength")
    public String getIndexPage() {
        return "index";
    }
}
