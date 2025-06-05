package hw2.controller;

import hw2.entity.Member;
import hw2.entity.Role;
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

import java.util.ArrayList;
import java.util.List;

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

        if (authService.checkEmailExists(member.getEmail())) {
            model.addAttribute("emailExists", true);
            return "auth/signup";
        }
        else {
            List<Role> memberRoles = new ArrayList<>();

            Role role = authService.findByRolename("ROLE_USER");
            memberRoles.add(role);

            // 특정 이메일 주소인 경우 ADMIN 역할 추가
            if ("admin@hansung.ac.kr".equals(member.getEmail())) {
                Role roleAdmin = authService.findByRolename("ROLE_ADMIN");
                memberRoles.add(roleAdmin);
            }

            authService.createMember(member, memberRoles);

            return "redirect:/";
        }
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }
}
