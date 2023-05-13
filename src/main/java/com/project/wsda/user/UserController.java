package com.project.wsda.user;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/add-user")
    public String addUser(@Valid @ModelAttribute("user") UserDto userDto, BindingResult bindingResult, Model model){
        User existingUser = userService.findByUsername(userDto.getUsername());

        if(existingUser != null){
            bindingResult.rejectValue("username","error.username","The inserted username is already registered");
        }

        if(bindingResult.hasErrors()){
            model.addAttribute("user", userDto);
            return "sign-up";
        }
        userService.saveUser(userDto);
        model.addAttribute("success", true);
        return "sign-up";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/sign-up")
    public String SignUpForm(Model model){
        model.addAttribute("user", new UserDto());
        return "sign-up";
    }
}
