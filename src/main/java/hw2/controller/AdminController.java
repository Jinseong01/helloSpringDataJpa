package hw2.controller;

import hw2.entity.Member;
import hw2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final MemberRepository memberRepository;

    @GetMapping("/users")
    public String viewUserList(Model model) {
        List<Member> members = memberRepository.findAll();
        model.addAttribute("members", members);
        return "admin/user_list";
    }
}