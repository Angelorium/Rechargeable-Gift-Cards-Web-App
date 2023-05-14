package com.project.wsda.security;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorsController implements ErrorController {

    @GetMapping("/access-denied")
    public String accessDenied(){
        return "/access-denied";
    }

    @GetMapping("/error")
    public String error(HttpServletRequest request, Model model){
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if(status != null){
            int code = Integer.parseInt(status.toString());

            if(code == HttpStatus.NOT_FOUND.value()){
                model.addAttribute("errorMessage", "Page Not Found");
            } else if (code == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                model.addAttribute("errorMessage", "Internal Server Error");
            }
            else{
                model.addAttribute("errorMessage", "Generic Error");
            }
        }
        return "error";
    }
}
