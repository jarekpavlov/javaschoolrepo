package com.jschool.service;

import com.jschool.DTO.ProductDTO;
import com.jschool.domain.Product;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

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

    public void saveProduct(Product product){
        if (product.getId() != null)
            entityService.updateEntity(product);
        else
            entityService.saveEntity(product);
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
