package com.jschool.controllers;

import com.jschool.DTO.ProductDTO;
import com.jschool.domain.Product;
import com.jschool.exceptions.EmptyFieldException;
import com.jschool.exceptions.NonValidNumberException;
import com.jschool.exceptions.ProductIsInOrderException;
import com.jschool.service.EntityService;
import com.jschool.service.ProductService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
public class ProductController {

    @Value("${upload.path}")
    private String uploadPath;

    Logger logger = Logger.getLogger(this.getClass());
    private EntityService entityService;
    private ProductService productService;

    @Autowired
    public ProductController(EntityService entityService, ProductService productService) {
        this.entityService = entityService;
        this.productService = productService;
    }

    @GetMapping(value = "/products")
    public String getProducts(ModelMap map, HttpSession httpSession, @RequestParam(required = false) Integer page) {
        productService.getPaginatedMap(map,page);
        productService.getCartModelMap(map, httpSession);
        return "products";
    }

    @GetMapping(value = "admin/product/edit")
    public String editProduct(ModelMap map, HttpServletRequest request) {
        logger.info("employee entered the editProduct controller method");
        Long productId = Long.parseLong(request.getParameter("id"));
        Product product = entityService.getEntity(Product.class, productId);
        map.addAttribute("product", product);
        return "product";
    }

    @RequestMapping(value = "/product/save", method = RequestMethod.POST)
    public String saveProduct(Product product, @RequestParam(required = false) MultipartFile productPicture) throws EmptyFieldException, NonValidNumberException, IOException {

        logger.debug("Employee entered saveProduct method");
        productService.saveProduct(product, productPicture);
        return "redirect:/products";
    }

    @GetMapping(value = "admin/product/new")
    public String newProduct(ModelMap map) {
        logger.info("employee entered the newProduct controller method");
        Product product = new Product();
        map.addAttribute("product", product);
        return "product";
    }

    @GetMapping(value = "/admin/product/delete")
    public String deleteProduct(HttpServletRequest request) throws ProductIsInOrderException {
        productService.deleteProduct(request);
        return "redirect:/products";
    }

    @PostMapping(value = "/product/filter")
    public String filterProduct(@RequestParam String color,
                                @RequestParam String brand,
                                @RequestParam String title, ModelMap map) {

        List<ProductDTO> filteredList = productService.getFilteredProducts(productService.getProductDtoList(), color, brand, title);
        map.addAttribute("products", filteredList);
        return "products";
    }

}
