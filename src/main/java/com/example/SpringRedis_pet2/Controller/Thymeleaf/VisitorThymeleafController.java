package com.example.SpringRedis_pet2.Controller.Thymeleaf;

import com.example.SpringRedis_pet2.Model.Visitor.Visitor;
import com.example.SpringRedis_pet2.Service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/visitor")
public class VisitorThymeleafController {

    @Autowired
    private VisitorService operations;

    @GetMapping("/login")
    public String loginPage(){
        return "visitor/login";
    }
    @GetMapping("/register")
    public String registerPage(@RequestParam(required = false) String username, Model model) {
        model.addAttribute("username", username); // Добавляем username в модель
        return "visitor/register";
    }
    @GetMapping("/login/gmail")
    private String secured(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Current authentication: " + authentication);

        Visitor user = operations.googleAuth();
        System.out.println(user);
        String password = user.getPassword();
        if (password == null || password.isEmpty()) {
            String username = user.getUsername();
            System.out.println("Redirecting to register page with username: " + username);
            return "redirect:/visitor/register?username=" + URLEncoder.encode(username, StandardCharsets.UTF_8);
        }
        System.out.println("User authenticated, redirecting to main page.");
        return "redirect:/fruit/main";
    }
}
