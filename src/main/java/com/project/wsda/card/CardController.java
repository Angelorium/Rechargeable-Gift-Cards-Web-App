package com.project.wsda.card;

import com.project.wsda.transaction.TransactionDto;
import com.project.wsda.transaction.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.Collection;

@Controller
@AllArgsConstructor
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;
    private final TransactionService transactionService;

    @GetMapping("/check-card")
    public String checkCardForm(Model model, Authentication authentication){
        model.addAttribute("id", 1);
        return getAuthority(authentication) + "/check-card";
    }

    @PostMapping("/check-card")
    public String checkCard(Integer id, Model model, Authentication authentication){
        Card card = cardService.findCardById(id);
        if(card != null){
            model.addAttribute("cardId", card.getId());
            model.addAttribute("cardCredit", card.getCredit());
            model.addAttribute("cardState", card.getState());
            model.addAttribute("success", true);
        }
        else{
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "Card Id not found");
        }
        return getAuthority(authentication) + "/check-card";
    }

    @GetMapping("/payment")
    public String paymentForm(Authentication authentication){
        return getAuthority(authentication) + "/payment";
    }

    @PostMapping("/payment")
    public String payment(Integer id, Integer amount, Model model, Authentication authentication){
        Card card = cardService.findCardById(id);
        if(amount < 0 || amount > 10000){
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "Credit must be between 1 and 10000");
        }
        else if(card != null) {
            if(card.getState().equals("invalid")){
                model.addAttribute("error", true);
                model.addAttribute("errorMessage", "Card is not valid");
            }
            else if(card.getCredit() - amount < 0) {
                model.addAttribute("error", true);
                model.addAttribute("errorMessage", "Not enough available credit");
            }
            else {
                User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                TransactionDto transactionDto = new TransactionDto();
                transactionDto.setAmount(amount);
                transactionDto.setCardId(id);
                transactionDto.setTimestamp(LocalDateTime.now());
                transactionDto.setUsername(user.getUsername());
                transactionService.saveTransaction(transactionDto);

                int creditRemaining = card.getCredit() - amount;
                cardService.updateCreditById(id, creditRemaining);

                if(creditRemaining == 0){
                    cardService.updateStateById(id, "invalid");
                }

                model.addAttribute("success", true);
                model.addAttribute("successMessage", "Success, remaining credit: " + creditRemaining + " €");
            }
        }
        else{
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "Card Id not found");
        }
        return getAuthority(authentication) + "/payment";
    }

    @GetMapping("/recharge")
    public String rechargeForm(Authentication authentication){
        return getAuthority(authentication) + "/recharge";
    }

    @PostMapping("/recharge")
    public String recharge(Integer id, Integer amount, Model model, Authentication authentication){
        Card card = cardService.findCardById(id);
        if(amount < 0 || amount > 10000){
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "Credit must be between 1 and 10000");
        }
        else if(card != null) {
            if(card.getCredit() + amount > 1000) {
                model.addAttribute("error", true);
                model.addAttribute("errorMessage", "Error, The max card credit is 10000 €");
            }
            else {
                int newCredit = card.getCredit() + amount;
                cardService.updateCreditById(id, newCredit);
                cardService.updateStateById(id, "valid");
                model.addAttribute("success", true);
                model.addAttribute("successMessage", "Success, new credit: " + newCredit + " €");
            }
        }
        else{
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "Card Id not found");
        }
        return getAuthority(authentication) + "/recharge";
    }

    @GetMapping("/transactions")
    public String transactions(Model model, Authentication authentication){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("transactions", transactionService.findTransactionsByUser(user));
        return getAuthority(authentication) + "/transactions";
    }

    private String getAuthority(Authentication authentication) {
        if(authentication == null){
            return "public";
        }
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (final GrantedAuthority grantedAuthority : authorities) {
            String authorityName = grantedAuthority.getAuthority();
            if (authorityName.equals("company")) {
                return "company";
            }
            else if(authorityName.equals("shop")) {
                return "shop";
            }
        }
        return "";
    }

}
