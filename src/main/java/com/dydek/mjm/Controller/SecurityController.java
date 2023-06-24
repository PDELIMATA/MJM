package com.dydek.mjm.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {

    @GetMapping("/login")
    public String signIn() {
        return "login";
    }

    @GetMapping("/")
    public String getMainPage() {
        return "login";
    }
}