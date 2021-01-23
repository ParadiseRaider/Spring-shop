package ru.geekbrains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.controller.repr.ProductRepr;
import ru.geekbrains.service.ProductService;
import ru.geekbrains.service.StockService;

@Controller
@RequestMapping("/main")
public class MainController {

    private final ProductService productService;
    private final StockService stockService;

    @Autowired
    public MainController(ProductService productService, StockService stockService) {
        this.productService = productService;
        this.stockService = stockService;
    }

    @GetMapping
    public String indexPage(Model model) {
        model.addAttribute("products", productService.findAllAndSplitProductsBy());
        return "index";
    }

    @GetMapping("/product/{id}")
    public String productPage(Model model, @PathVariable("id") Long id) {
        ProductRepr productRepr = productService.findById(id).orElseThrow(IllegalArgumentException::new);
        productRepr.setCountInStock(stockService.getStockInfo(productRepr.getId()).getCount());
        model.addAttribute("productId", productRepr);
        model.addAttribute("products", productService.findAllAndSplitProductsBy());
        return "product";
    }
}
