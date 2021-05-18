package com.jschool.service;

import com.jschool.DTO.ProductDTO;
import com.jschool.domain.Product;
import com.jschool.domain.ProductsInOrder;
import com.jschool.exceptions.EmptyFieldException;
import com.jschool.exceptions.NonValidNumberException;
import com.jschool.exceptions.ProductIsInOrderException;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    Logger logger = Logger.getLogger(this.getClass());
    private ModelMapper modelMapper;
    private EntityService entityService;

    public ProductService() {}

    @Autowired
    public ProductService(ModelMapper modelMapper, EntityService entityService) {
        this.entityService = entityService;
        this.modelMapper = modelMapper;
    }

    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    public void saveProduct(Product product) throws EmptyFieldException, NonValidNumberException {
        if(product.getPrice()<1 || product.getMass()<1 || product.getPrice()<1 || product.getQuantity()<1){
            logger.warn("Employee entered incorrect number ");
            throw new NonValidNumberException("Some numbers are incorrect");
        }
        String emptyS = "";
        if(emptyS.equals(product.getBrand()) || emptyS.equals(product.getTitle()) || emptyS.equals(product.getCategory()) || product.getPrice()==null){
            logger.warn("Not all fields were filled in saveProduct service method");
            throw new EmptyFieldException("All fields required to be filled!");
        }
        if (product.getId() != null)
            entityService.updateEntity(product);
        else {
            entityService.saveEntity(product);
        }
        logger.info("Employee left the saveProduct service method");
    }

    /**
     * Method returns a collection of ProductDTO entities filtered by:
     *
     * @param color
     * @param brand
     * @param title
     * @return
     */
    public List<ProductDTO> getFilteredProductList(List<ProductDTO> productDtoList, String color, String brand, String title) {
        String emptyS = "";
        List<ProductDTO> filteredList = null;
        if (!emptyS.equals(color)) {
            filteredList = productDtoList
                    .stream()
                    .filter(l -> l.getColor().equalsIgnoreCase(color))
                    .collect(Collectors.toList());
        }
        if (!emptyS.equals(brand)) {
            if (filteredList == null) {
                filteredList = productDtoList
                        .stream()
                        .filter(l -> l.getBrand().equalsIgnoreCase(brand))
                        .collect(Collectors.toList());
            } else {
                filteredList = filteredList
                        .stream()
                        .filter(l -> l.getBrand().equalsIgnoreCase(brand))
                        .collect(Collectors.toList());
            }
        }
        if (!emptyS.equals(title)) {
            if (filteredList == null) {
                filteredList = productDtoList
                        .stream()
                        .filter(l -> l.getTitle().equalsIgnoreCase(title))
                        .collect(Collectors.toList());
            } else {
                filteredList = filteredList
                        .stream()
                        .filter(l -> l.getTitle().equalsIgnoreCase(title))
                        .collect(Collectors.toList());
            }
        }
        if (filteredList==null)
            return productDtoList;
        return filteredList;
    }

    public void deleteProduct(HttpServletRequest request) throws ProductIsInOrderException {
        List<ProductsInOrder> productsInOrderList = entityService.entityList(ProductsInOrder.class);
        Long id = Long.parseLong(request.getParameter("id"));
        Product product = entityService.getEntity(Product.class,id);
        for (ProductsInOrder productsInOrder : productsInOrderList){
            if(productsInOrder.getProduct().equals(product)){
                logger.warn("Employee tried to delete a product which is in an order");
                throw new ProductIsInOrderException("The product is in an order. You should delete the order first!");
            }
        }
        entityService.deleteEntity(Product.class, id);
    }

    public ProductDTO getProductDTO(Product product) {
        getModelMapper()
                .getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return getModelMapper().map(product, ProductDTO.class);
    }

    public List<ProductDTO> getProductDtoList() {
        return entityService.entityList(Product.class)
                .stream()
                .map(this::getProductDTO)
                .collect(Collectors.toList());
    }
}
