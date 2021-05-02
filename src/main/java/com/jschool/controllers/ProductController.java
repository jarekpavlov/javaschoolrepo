package com.jschool.controllers;

import com.jschool.domain.Product;
import com.jschool.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ProductController {

    private EntityService entityService;

    @Autowired
    public ProductController(EntityService entityService) {
        this.entityService = entityService;
    }

    @GetMapping(value = "/products")
    public String getProducts(ModelMap map) {
        List<Product> productList = entityService.entityList(Product.class);
        map.addAttribute("products", productList);
        return "products";
    }

    @GetMapping(value = "/product/edit")
    public String editProduct(ModelMap map, HttpServletRequest request) {
        Long productId = Long.parseLong(request.getParameter("id"));
        Product product = entityService.getEntity(Product.class, productId);
        map.addAttribute("product", product);
        return "product";
    }

    @RequestMapping(value = "/product/save", method = RequestMethod.POST)
    public String saveProduct(Product product) {
        if (product.getId() != null)
            entityService.updateEntity(product);
        else
            entityService.saveEntity(product);

        return "redirect:/products";
    }

    @GetMapping(value = "/product/new")
    public String newProduct(ModelMap map) {
        Product product = new Product();
        map.addAttribute("product", product);
        return "product";
    }

    @GetMapping(value = "/product/delete")
    public String deleteProduct(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"));
        entityService.deleteEntity(Product.class, id);
        return "redirect:/products";
    }

}
