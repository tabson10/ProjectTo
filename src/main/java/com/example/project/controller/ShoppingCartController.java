package com.example.project.controller;

import com.example.project.domain.Batch;
import com.example.project.domain.CartItem;
import com.example.project.domain.ShoppingCart;
import com.example.project.domain.User;
import com.example.project.service.BatchService;
import com.example.project.service.CartItemService;
import com.example.project.service.ShoppingCartService;
import com.example.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private UserService userService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private BatchService batchService;

    @RequestMapping("/cart")
    public String shoppingCart(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        ShoppingCart shoppingCart = user.getShoppingCart();

        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);

        shoppingCartService.updateShoppingCart(shoppingCart);

        model.addAttribute("cartItemList", cartItemList);
        model.addAttribute("shoppingCart", shoppingCart);

        return "shoppingCart";
    }

    @RequestMapping("/addItem")
    public String addItem(@ModelAttribute("batch") Batch batch, @ModelAttribute("quantity") String qty, Model model, Principal principal){
        User user = userService.findByUsername(principal.getName());

        Optional<Batch> batch1 = batchService.findById(batch.getBatchId());

        if(Integer.parseInt(qty) > batch1.get().getQuantity()) {
            model.addAttribute("notEnoughStock", true);
            return "forward:/batchDetail?id="+batch1.get().getBatchId();
        }

        CartItem cartItem = cartItemService.addBatchToCartItem(batch1.get(), user, Integer.parseInt(qty));
        model.addAttribute("addBatchSuccess", true);

        return "redirect:/fruitDetail/"+batch1.get().getBatchId();
    }
}
