package org.example.hrapp.controller.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String redirectToVacancies() {
        return "redirect:/vacancies";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }
}