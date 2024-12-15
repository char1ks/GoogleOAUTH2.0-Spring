package com.example.SpringRedis_pet2.Controller.Thymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/fruit")
public class FruitThymeleafController {

    @GetMapping("/main")
    public String mainPage(){
        return "fruit/mainPage";
    }
    @GetMapping("/concrete/{id}")
    public String concretePage(){
        return "fruit/concretePage";
    }
    @GetMapping("/new")
    public String newPage(){
        return "fruit/newPage";
    }
}
