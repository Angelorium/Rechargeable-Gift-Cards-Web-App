package com.project.wsda.shop;

import com.project.wsda.card.Card;
import com.project.wsda.card.CardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/shop")
@AllArgsConstructor
public class ShopController {

    private final CardService cardService;

    @GetMapping
    public String shop(){
        return "/shop/shop";
    }

    @GetMapping("/payment")
    public String payment(){
        return "/shop/payment";
    }

    @GetMapping("/cards/check-card")
    public String checkCardForm(Model model){
        model.addAttribute("id", 1);
        return "shop/check-card";
    }

    @PostMapping("/cards/check-card")
    public String checkCard(Integer id, Model model){

        Card card = cardService.findCardById(id);

        if(card != null){
            model.addAttribute("cardId", card.getCredit());
            model.addAttribute("cardCredit", card.getCredit());
            model.addAttribute("cardState", card.getState());
            model.addAttribute("success", true);
        }
        else{
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "Gift Card Id not found");
        }

        return "shop/check-card";
    }
}
