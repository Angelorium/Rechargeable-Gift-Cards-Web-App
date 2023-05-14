package com.project.wsda.company;

import com.project.wsda.card.Card;
import com.project.wsda.card.CardDto;
import com.project.wsda.card.CardService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/company")
@AllArgsConstructor
public class CompanyController {

    private final CardService cardService;

    @GetMapping
    public String company(){
        return "/company/company";
    }

    @GetMapping("/cards/add-card")
    public String addCardForm(Model model){
        model.addAttribute("card", new CardDto());
        return "/company/add-card";
    }

    @GetMapping("/cards/view-cards")
    public String getCards(Model model){
        List<Card> cards = cardService.findAllCards();
        model.addAttribute("cards", cards);
        return "/company/view-cards";
    }

    @PostMapping("/cards/add-card")
    public String addCard(@Valid @RequestAttribute("card") CardDto cardDto, BindingResult bindingResult, Model model){

        cardDto.setState("valid");
        if(bindingResult.hasErrors()){
            model.addAttribute("card", cardDto);
            return "/company/add-card";
        }

        cardService.saveCard(cardDto);
        model.addAttribute("success", true);
        return "/company/add-card";
    }
}
