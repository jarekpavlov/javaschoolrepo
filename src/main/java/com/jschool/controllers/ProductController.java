package com.jschool.controllers;

import com.jschool.DAO.EntityDaoImpl;
import com.jschool.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ProductController {

    private EntityDaoImpl entityDaoImpl;
    @Autowired
    public ProductController(EntityDaoImpl entityDaoImpl){
        this.entityDaoImpl = entityDaoImpl;
    }

    @GetMapping(value = "/products")
    public String getProducts(ModelMap map){
        List productList = entityDaoImpl.entityList(Product.class);
        map.addAttribute("products",productList);
        return "products";
    }

    @GetMapping(value = "/product/edit")
    public String editProduct(ModelMap map,HttpServletRequest request){
        Long productId =Long.parseLong(request.getParameter("id")) ;
        Product product = entityDaoImpl.getEntity(Product.class, productId);
        map.addAttribute("product",product);
        return "product";
    }
    @RequestMapping(value = "/product/save", method = RequestMethod.POST)
    public String saveProduct(Product product){
        if (product.getId()!=null)
            entityDaoImpl.update(product);
        else
            entityDaoImpl.saveEntity(product);

        return "redirect:/products";
    }
    @GetMapping(value = "/product/new")
    public String newProduct(ModelMap map){
        Product product = new Product();
        map.addAttribute("product", product);
        return "product";
    }
    @GetMapping(value = "/product/delete")
    public String deleteProduct(HttpServletRequest request){
        Long id = Long.parseLong(request.getParameter("id"));
        entityDaoImpl.delete(Product.class,id);
        return "redirect:/products";
    }

}
