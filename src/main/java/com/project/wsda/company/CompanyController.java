package com.project.wsda.company;

import com.project.wsda.card.Card;
import com.project.wsda.card.CardDto;
import com.project.wsda.card.CardService;
import com.project.wsda.user.User;
import com.project.wsda.user.UserDto;
import com.project.wsda.user.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/company")
@AllArgsConstructor
public class CompanyController {

    private final CardService cardService;
    private final UserService userService;

    @GetMapping
    public String company(){
        return "/company/company";
    }

    @GetMapping("/add-card")
    public String addCardForm(Model model){
        model.addAttribute("card", new CardDto());
        return "/company/add-card";
    }

    @PostMapping("/add-card")
    public String addCard(@Valid @ModelAttribute("card") CardDto cardDto, BindingResult bindingResult, Model model){
        cardDto.setState("valid");
        if(bindingResult.hasErrors()){
            model.addAttribute("card", cardDto);
            return "/company/add-card";
        }

        cardService.saveCard(cardDto);
        model.addAttribute("success", true);
        return "/company/add-card";
    }

    @PostMapping("/add-shop")
    public String addUser(@Valid @ModelAttribute("user") UserDto userDto, BindingResult bindingResult, Model model){
        User existingUser = userService.findByUsername(userDto.getUsername());

        if(existingUser != null){
            bindingResult.rejectValue("username","error.username","The inserted username is already registered");
        }

        if(bindingResult.hasErrors()){
            model.addAttribute("user", userDto);
            return "/company/add-shop";
        }
        userDto.setRole("shop");
        userService.saveUser(userDto);
        model.addAttribute("success", true);
        return "/company/add-shop";
    }

    @GetMapping("/add-shop")
    public String SignUpForm(Model model){
        model.addAttribute("user", new UserDto());
        return "/company/add-shop";
    }

    @GetMapping("/block-unblock-card")
    public String blockUnblockCardForm(Model model){
        model.addAttribute("id", 1);
        return "company/block-unblock-card";
    }

    @PostMapping("/block-unblock-card")
    public String blockUnblockCard(Integer id, Integer operation, Model model){
        Card card = cardService.findCardById(id);
        if(card != null){
            if(operation == 1){
                cardService.updateStateById(id, "valid");
                model.addAttribute("successMessage", "Card Unblocked correctly");
            }
            else{
                cardService.updateStateById(id, "invalid");
                model.addAttribute("successMessage", "Card Blocked correctly");
            }
            model.addAttribute("cardId", card.getId());
            model.addAttribute("success", true);
        }
        else{
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "Card Id not found");
        }
        return "company/block-unblock-card";
    }
}
