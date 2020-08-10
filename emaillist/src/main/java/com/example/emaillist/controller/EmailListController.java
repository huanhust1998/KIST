package com.example.emaillist.controller;

import com.example.emaillist.entity.User;
import com.example.emaillist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@RequestMapping("/email")
public class EmailListController {
    private UserRepository userRepository;
    @Autowired
    public EmailListController(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @GetMapping
    public String showEmailListForm(Model model){
        model.addAttribute("user", new User());
        return "formEmailList";
    }
    @PostMapping
    public String processForm(Model model,User user){
        userRepository.save(user);
        model.addAttribute("users",userRepository.findAll());
        return "resultset";
    }
}
