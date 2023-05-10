package com.project.wsda.user;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLIntegrityConstraintViolationException;


@Controller
@RequestMapping("/sign-up")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public String addNewUser(@Valid User user, BindingResult bindingResult, Model model){
        if(!bindingResult.hasErrors()){
            model.addAttribute("success", true);
            model.addAttribute("successMessage", "User registered correctly");
            userRepository.save(user);
        }
        return "sign-up";
    }

    @GetMapping
    public String displayForm(User user){
        return "sign-up";
    }

    @ExceptionHandler({SQLIntegrityConstraintViolationException.class})
    public ModelAndView usernameError(){
        ModelAndView mv = new ModelAndView("sign-up");
        mv.addObject("user", new User());
        mv.addObject("usernameError", true);
        mv.addObject("errorMessage", "This username already exists");
        return mv;
    }
}
