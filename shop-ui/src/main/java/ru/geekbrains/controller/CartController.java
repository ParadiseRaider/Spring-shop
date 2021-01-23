package ru.geekbrains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.service.CartService;
import ru.geekbrains.service.ProductService;
import ru.geekbrains.service.model.LineItem;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final ProductService productService;
    private final CartService cartService;

    @Autowired
    public CartController(ProductService productService, CartService cartService) {
        this.productService = productService;
        this.cartService = cartService;
    }

    @GetMapping
    public String cartPage(Model model) {
        model.addAttribute("lineItems", cartService.getLineItems());
        model.addAttribute("subTotal", cartService.getSubTotal());
        return "cart";
    }

    @GetMapping("/{id}")
    public String updateCart(@PathVariable("id") Long id) {
        LineItem lineItem = new LineItem(productService.findById(id)
                .orElseThrow(IllegalArgumentException::new));
        cartService.updateCart(lineItem);
        return "redirect:/cart";
    }

    @DeleteMapping
    public String deleteAll() {
        cartService.removeAllProduct();
        return "redirect:/cart";
    }
}
