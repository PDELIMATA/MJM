package com.dydek.mjm.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {

    @GetMapping("/sign-in")
    public String signIn() {
        return "sign-in";
    }

    @GetMapping("/")
    public String getMainPage() {
        return "sign-in";
    }
}
