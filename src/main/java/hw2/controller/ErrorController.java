package hw2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
@RequiredArgsConstructor
public class ErrorController {

    @GetMapping("/403")
    public String error403() {
        return "error/403";
    }
}