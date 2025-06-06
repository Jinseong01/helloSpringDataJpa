package hw2.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {

    @GetMapping("/")
    public String rootRedirect(Authentication authentication, RedirectAttributes redirectAttributes) {
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName(); // 현재 로그인한 사용자 email
            redirectAttributes.addFlashAttribute("success", "환영합니다, " + email + "님!");
            return "redirect:/products";
        } else {
            return "redirect:/auth/login";
        }
    }
}
