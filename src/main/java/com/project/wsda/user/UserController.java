package com.project.wsda.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
