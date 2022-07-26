package com.project.MyShop.controllers;

import com.project.MyShop.models.Product;
import com.project.MyShop.models.User;
import com.project.MyShop.services.ProdService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ProdController {
    private final ProdService prodService;

    @GetMapping("/")
    public String prod(@RequestParam(required = false, defaultValue = "") String searchWord,
                       Principal principal,
                       Model model) {
        model.addAttribute("searchWord", searchWord);
        model.addAttribute("products", prodService.listProd(searchWord));
        model.addAttribute("user", prodService.getUserByPrincipal(principal));
        return "prod";
    }

    @GetMapping("/product/{id}")
    public String infoProd(@PathVariable Long id, Principal principal, Model model) {
        Product product = prodService.getProdById(id);
        model.addAttribute("user", prodService.getUserByPrincipal(principal));
        model.addAttribute("product", product);
        model.addAttribute("images", product.getImages());
        model.addAttribute("authorProduct", product.getUser());
        return "prod-info";
    }

    @PostMapping("/product/create")
    public String createProd(
            @RequestParam("file1") MultipartFile file1,
            @RequestParam("file2") MultipartFile file2,
            @RequestParam("file3") MultipartFile file3,
            Product prod,
            Principal principal
    ) throws IOException {
        prodService.saveProd(principal, prod, file1, file2, file3);
        return "redirect:/my/prod";
    }

    @PostMapping("/product/delete/{id}")
    public String deleteProd(@PathVariable Long id) {
        prodService.deleteProd(id);
        return "redirect:/my/prod";
    }

    @GetMapping("/my/prod")
    public String userProducts(Principal principal, Model model) {
        User user = prodService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        model.addAttribute("products", user.getProducts());
        return "my-prod";
    }
}
