package com.jschool.service;

import com.jschool.DTO.ProductDTO;
import com.jschool.domain.Client;
import com.jschool.domain.Product;
import com.jschool.domain.ProductBuilder;
import com.jschool.domain.ProductsWithUser;
import com.jschool.exceptions.EmptyFieldException;
import com.jschool.exceptions.NonValidNumberException;
import com.jschool.security.Authority;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductServiceTest {


    ProductService productService = new ProductService();
    List<ProductDTO> list = new ArrayList<>();
    List<ProductDTO> expectedList = new ArrayList<>();

    @BeforeEach
    void beforeEach() {

        ProductDTO product1 = new ProductDTO();
        ProductDTO product2 = new ProductDTO();
        ProductDTO product3 = new ProductDTO();
        ProductDTO product4 = new ProductDTO();
        ProductDTO product5 = new ProductDTO();

        product1.setBrand("brand1");
        product1.setCategory("category1");
        product1.setColor("color1");
        product1.setId(1L);

        product2.setBrand("brand1");
        product2.setCategory("category2");
        product2.setColor("color1");
        product1.setId(2L);

        product3.setBrand("brand2");
        product3.setCategory("category1");
        product3.setColor("color2");
        product1.setId(3L);

        product4.setBrand("brand3");
        product4.setCategory("category3");
        product4.setColor("color3");
        product1.setId(4L);

        product5.setBrand("brand1");
        product5.setCategory("category4");
        product5.setColor("color4");
        product1.setId(5L);

        list.add(product1);
        list.add(product2);
        list.add(product3);
        list.add(product4);
        list.add(product5);

        expectedList.add(product1);
        expectedList.add(product2);
        List<ProductDTO> filteredListAfterMethod = productService.getFilteredProducts(list, "color1", "brand1", "");
    }

    @Test
    void getFilteredProducts() {

        List<ProductDTO> filteredListAfterMethod = productService.getFilteredProducts(list, "color1", "brand1", "");
        Assertions.assertEquals(expectedList, filteredListAfterMethod);
    }

    @Test
    void getFilteredProducts2() {

        Assertions.assertNotEquals(list, productService.getFilteredProducts(list, "color2", "brand3", ""));
    }


    @Test
    void getProductDTO() {
        productService.setModelMapper(new ModelMapper());
        Product product = new Product();
        product.setId(1L);
        product.setBrand("brand1");
        product.setCategory("category1");
        product.setColor("color");
        product.setPrice(100F);
        product.setMass(100F);
        product.setQuantity(10);
        product.setTitle("title1");
        product.setVolume(100.5F);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setBrand("brand1");
        productDTO.setCategory("category1");
        productDTO.setColor("color");
        productDTO.setPrice(100F);
        productDTO.setQuantity(10);
        productDTO.setTitle("title1");
        Assertions.assertEquals(productDTO, productService.getProductDTO(product));
    }

    @Test
    void filterIsEmptyTest() {
        String color = "green";
        Assertions.assertFalse(productService.filterIsEmpty(color, null, null));
    }

    @Test
    void nonValidExceptionTest() {
        Product product = new ProductBuilder()
                .setPrice(0F)
                .setMass(0F)
                .build();

        Assertions.assertThrows(NonValidNumberException.class,
                () -> {
                    productService.nonValidExceptionCheck(product);
                });
    }
    @Test
    void testGetPaginationMethod(){

        EntityService entityService = mock(EntityService.class, Mockito.RETURNS_DEEP_STUBS);
        Product product1 = new Product();
        Product product2 = new Product();
        Product product3 = new Product();
        Product product4 = new Product();
        Product product5 = new Product();

        product1.setBrand("brand1");
        product1.setCategory("category1");
        product1.setColor("color1");
        product1.setId(1L);

        product2.setBrand("brand1");
        product2.setCategory("category2");
        product2.setColor("color1");
        product2.setId(2L);

        product3.setBrand("brand2");
        product3.setCategory("category1");
        product3.setColor("color2");
        product3.setId(3L);

        product4.setBrand("brand3");
        product4.setCategory("category3");
        product4.setColor("color3");
        product4.setId(4L);

        product5.setBrand("brand1");
        product5.setCategory("category4");
        product5.setColor("color4");
        product5.setId(5L);
        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        products.add(product3);
        products.add(product4);
        products.add(product5);

        when(entityService.entityList(Product.class,0,2147483647)).thenReturn(products);

        //List<Product> products1 = entityService.entityList(Product.class,0,2147483647);
        //System.out.println(products1);

        List<ProductDTO> productDTO = productService.getPaginationMethod(1,"color1","brand1",null);
        System.out.println(productDTO);
    }

    @Test
    void emptyFieldExceptionCheckTest() {
        Product product = new ProductBuilder()
                .setPrice(10F)
                .setMass(1F)
                .build();
        Assertions.assertThrows(EmptyFieldException.class,
                () -> {
                    productService.emptyFieldsExceptionCheck(product);
                });
    }

    @Test
    void formingFilteredPaginatedTest() {
        List<ProductDTO> filteredList = new ArrayList<>();
        ProductDTO product1 = new ProductDTO();
        product1.setId(1L);
        ProductDTO product2 = new ProductDTO();
        product1.setId(2L);
        ProductDTO product3 = new ProductDTO();
        product1.setId(3L);
        filteredList.add(product1);
        filteredList.add(product2);
        filteredList.add(product3);
        List<ProductDTO> paginatedList = productService.formingFilteredPaginatedProductList(1, filteredList, 2);
        List<ProductDTO> paginatedMockList = new ArrayList<>();
        paginatedMockList.add(product1);
        paginatedMockList.add(product2);
        Assertions.assertEquals(paginatedMockList, paginatedList);
    }

    @Test
    void getPageQuantityTest() {

        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        Product product2 = new Product();
        Product product3 = new Product();

        products.add(product1);
        products.add(product2);
        products.add(product3);

        Assertions.assertEquals(productService.getPageQuantity(products, 2), 2);
    }

    @Test
    void getProductsWithUser() {

        ProductDTO product1 = new ProductDTO();
        product1.setId(1L);
        ProductDTO product2 = new ProductDTO();
        product1.setId(2L);
        ProductDTO product3 = new ProductDTO();
        product1.setId(3L);

        List<ProductDTO> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);
        productList.add(product3);

        Client client = new Client();
        client.setId(1L);
        client.setName("Name");

        Authority authority = new Authority();
        authority.setAuthority("ROLE_EMPLOYEE");
        authority.setClient(client);
        authority.setId(1L);

        Authority authority2 = new Authority();
        authority2.setAuthority("ROLE");
        authority2.setClient(client);
        authority2.setId(2L);

        Set<Authority> authorityList = new HashSet<>();
        authorityList.add(authority);
        client.setAuthorities(authorityList);

        ProductsWithUser productsWithUser = productService.getProductsWithUser(productList, client);
        Assertions.assertEquals(productsWithUser.getRole(),"ROLE_EMPLOYEE");
        Assertions.assertEquals(productsWithUser.getProducts(),productList);
        authorityList.clear();
        authorityList.add(authority2);
        client.setAuthorities(authorityList);
        productsWithUser = productService.getProductsWithUser(productList, client);
        Assertions.assertNull(productsWithUser.getRole());

    }
}