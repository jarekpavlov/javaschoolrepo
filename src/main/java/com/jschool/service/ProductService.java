package com.jschool.service;

import com.jschool.DTO.ProductDTO;
import com.jschool.domain.Product;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private ModelMapper modelMapper;
    private EntityService entityService;

    public ProductService(ModelMapper modelMapper, EntityService entityService) {
        this.entityService = entityService;
        this.modelMapper = modelMapper;
    }

    public void saveProduct(Product product) {
        if (product.getId() != null)
            entityService.updateEntity(product);
        else
            entityService.saveEntity(product);
    }

    /**
     * Method returns a collection of ProductDTO entities filtered by:
     *
     * @param color
     * @param brand
     * @param title
     * @return
     */
    public List<ProductDTO> getFilteredProductList(String color, String brand, String title) {
        List<ProductDTO> productDtoList = getProductDtoList();
        List<ProductDTO> filteredList = null;
        if (!color.equals("")) {
            filteredList = productDtoList.stream()
                    .filter(l -> l.getColor().equals(color)).collect(Collectors.toList());
        }
        if (!brand.equals("")) {
            if (filteredList == null) {
                filteredList = productDtoList.stream()
                        .filter(l -> l.getBrand().equals(brand)).collect(Collectors.toList());
            } else {
                filteredList = filteredList.stream()
                        .filter(l -> l.getBrand().equals(brand)).collect(Collectors.toList());
            }
        }
        if (!title.equals("")) {
            if (filteredList == null) {
                filteredList = productDtoList.stream()
                        .filter(l -> l.getTitle().equals(title)).collect(Collectors.toList());
            } else {
                filteredList = filteredList.stream()
                        .filter(l -> l.getTitle().equals(title)).collect(Collectors.toList());
            }
        }
        return filteredList;
    }

    public List<Product> getTopTenProducts() {

        List<Product> productList = new ArrayList<>();
        return productList;
    }

    public ProductDTO getProductDTO(Product product) {
        modelMapper
                .getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(product, ProductDTO.class);
    }

    public List<ProductDTO> getProductDtoList() {
        return entityService.entityList(Product.class)
                .stream()
                .map(this::getProductDTO)
                .collect(Collectors.toList());
    }
}
