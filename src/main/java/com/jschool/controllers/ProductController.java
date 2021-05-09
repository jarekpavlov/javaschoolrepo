package com.jschool.controllers;

import com.jschool.DTO.ProductDTO;
import com.jschool.domain.Product;
import com.jschool.domain.ProductsInOrder;
import com.jschool.service.EntityService;
import com.jschool.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;

@Controller
public class ProductController {

    private EntityService entityService;
    private ProductService productService;

    @Autowired
    public ProductController(EntityService entityService, ProductService productService) {
        this.entityService = entityService;
        this.productService = productService;
    }

    @GetMapping(value = "/products")
    public String getProducts(ModelMap map, HttpSession httpSession) {
        List<ProductDTO> productList = productService.getProductDtoList();
        map.addAttribute("products", productList);
        Set<ProductsInOrder> productsInOrderSet = (Set<ProductsInOrder>) httpSession.getAttribute("productsInOrderSet");
        if (productsInOrderSet != null) {
            map.addAttribute("productsInCart", productsInOrderSet.size());
        } else {
            map.addAttribute("productsInCart", "0");
        }
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
        productService.saveProduct(product);
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

    @PostMapping(value = "/product/filter")
    public String filterProduct(@RequestParam String color,
                                @RequestParam String brand,
                                @RequestParam String title, ModelMap map) {


        List<ProductDTO> filteredList = productService.getFilteredProductList(productService.getProductDtoList(), color, brand, title);
        map.addAttribute("products", filteredList);
        return "products";
    }

}
