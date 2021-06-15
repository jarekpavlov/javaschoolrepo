package com.jschool.controllers;

import com.jschool.DTO.ProductDTO;
import com.jschool.domain.Client;
import com.jschool.domain.Product;
import com.jschool.domain.ProductBuilder;
import com.jschool.domain.ProductsWithUser;
import com.jschool.exceptions.EmptyFieldException;
import com.jschool.exceptions.NonValidNumberException;
import com.jschool.exceptions.ProductIsInOrderException;
import com.jschool.service.EntityService;
import com.jschool.service.ProductService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
public class ProductController {

    @Value("${upload.path}")
    private String uploadPath;

    @Value("${products.list.quantity}")
    private int productPerPage;

    Logger logger = Logger.getLogger(this.getClass());
    private EntityService entityService;
    private ProductService productService;

    @Autowired
    public ProductController(EntityService entityService, ProductService productService) {
        this.entityService = entityService;
        this.productService = productService;
    }

    @GetMapping(value = "/products")
    public ModelAndView getProducts(HttpSession httpSession
            , @RequestParam(required = false) Integer page
            , @RequestParam(required = false) String color
            , @RequestParam(required = false) String brand
            , @RequestParam(required = false) String title) {
        ModelAndView model = new ModelAndView();
        model.setViewName("products");
        model.addObject("products", productService.getPaginationMethod(page, color, brand, title));
        model.addObject("pageQuantity", productService.getPageQuantity(productService.getFullOrFilteredList(color, brand, title), productPerPage));
        model.addObject("productsInCart", productService.getProductInCartQuantity(httpSession));
        model.addObject("color", color);
        model.addObject("brand", brand);
        model.addObject("title", title);
        return model;
    }

    @GetMapping(value = "/products-json")
    @ResponseBody
    public ProductsWithUser getProductsJson(@AuthenticationPrincipal Client client,
                                            @RequestParam(required = false) Integer page
            , @RequestParam(required = false) String color
            , @RequestParam(required = false) String brand
            , @RequestParam(required = false) String title) {

        List<ProductDTO> list = productService.getPaginationMethod(page, color, brand, title);
        return productService.getProductsWithUser(list, client);
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
    public String saveProduct(@RequestParam(required = false) MultipartFile productPicture
            , @RequestParam(required = false) Long id
            , @RequestParam(required = false) Float price
            , @RequestParam(required = false) Integer quantity
            , @RequestParam(required = false) String brand
            , @RequestParam(required = false) String title
            , @RequestParam(required = false) String color
            , @RequestParam(required = false) String category
            , @RequestParam(required = false) String imgName
            , @RequestParam(required = false) Float mass
            , @RequestParam(required = false) Float volume
    ) throws EmptyFieldException, NonValidNumberException, IOException {
        logger.debug("Employee entered saveProduct method");
        Product product = new ProductBuilder()
                .setBrand(brand)
                .setCategory(category)
                .setColor(color)
                .setId(id)
                .setMass(mass)
                .setPrice(price)
                .setQuantity(quantity)
                .setImgName(imgName)
                .setTitle(title)
                .setVolume(volume)
                .build();
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

        List<ProductDTO> filteredList = productService.getFilteredProducts(productService.getProductDtoList(0, Integer.MAX_VALUE), color, brand, title);
        map.addAttribute("products", filteredList);
        return "products";
    }

}
