package hw2.controller;

import hw2.entity.Member;
import hw2.exception.EmailAlreadyExistsException;
import hw2.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/signup")
    public String signup(Model model) {

        Member member = new Member();
        model.addAttribute("member", member);

        return "auth/signup";
    }

    @PostMapping("/signup")
    public String signupPost(
            @Valid @ModelAttribute("member") Member member,
            BindingResult bindingResult,
            Model model
    ) {
        // 유효성 실패 시 재반환
        if (bindingResult.hasErrors()) {
            return "auth/signup";
        }

        // 회원가입 진행
        try {
            authService.createMember(member);
            return "redirect:/";
        }
        // 이메일 중복 예외 발생 시
        catch (EmailAlreadyExistsException e) {
            model.addAttribute("emailExists", true);
            return "auth/signup";
        }
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }
}
