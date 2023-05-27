package com.project.wsda.shop;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/shop")
@AllArgsConstructor
public class ShopController {

    @GetMapping
    public String shop(){
        return "/shop/shop";
    }
}
