package hw2.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String rootRedirect(Authentication authentication) {
        // 인증된 경우
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/products";
        }
        // 인증되지 않은 경우
        else {
            return "redirect:/auth/login";
        }
    }
}
