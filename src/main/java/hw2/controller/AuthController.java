package hw2.controller;

import hw2.entity.Member;
import hw2.exception.EmailAlreadyExistsException;
import hw2.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
            model.addAttribute("errorMsg", e.getMessage());
            return "auth/signup";
        }
    }

    @GetMapping("/login")
    public String login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model
    ) {
        if (error != null) {
            model.addAttribute("errorMsg", "아이디 또는 비밀번호가 올바르지 않습니다.");
        }
        if (logout != null) {
            model.addAttribute("logoutMsg", "로그아웃되었습니다.");
        }
        return "auth/login";
    }
}
