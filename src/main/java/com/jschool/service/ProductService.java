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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Value("${upload.path}")
    private String uploadPath;

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

    /**
     * This method is saving/updating a @param product entity regarding if there is an id.
     * It process @param productPicture, creating a random name for it and saves it to data base and
     * upload path directory
     * @throws EmptyFieldException
     * @throws NonValidNumberException
     * @throws IOException
     */
    public void saveProduct(Product product, MultipartFile productPicture) throws EmptyFieldException, NonValidNumberException, IOException {

        if(productPicture!=null){
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidName = UUID.randomUUID().toString();
            String resultName = uuidName+"-"+productPicture.getOriginalFilename();
            product.setImgName(resultName);
            productPicture.transferTo(new File(uploadPath+"/"+resultName));
        }

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

    public ModelMap getCartModelMap(ModelMap map, HttpSession httpSession){
        Set<ProductsInOrder> productsInOrderSet = (Set<ProductsInOrder>) httpSession.getAttribute("productsInOrderSet");
        if (productsInOrderSet != null) {
            map.addAttribute("productsInCart", productsInOrderSet.size());
        } else {
            map.addAttribute("productsInCart", "0");
        }
        return map;
    }
    public ModelMap getPaginatedMap(ModelMap map, Integer page){
        final double onPage = 10;
        List<ProductDTO> productList = getProductDtoList();
        List<ProductDTO> productListPaginated;
        int pageQuantity;

        if((productList.size()/onPage) % 1 != 0){
            pageQuantity = productList.size()/10 + 1;
        } else {
            pageQuantity = productList.size()/10;
        }
        if(page == null){
            productListPaginated = getProductDtoList(0,(int)onPage);
        } else {
            productListPaginated = getProductDtoList(((page-1)*(int)onPage),(int)onPage);
        }
        map.addAttribute("products", productListPaginated);
        map.addAttribute("pageQuantity",pageQuantity);
        return map;
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
    public List<ProductDTO> getProductDtoList(int offset, int limit) {
        return entityService.entityList(Product.class, offset, limit)
                .stream()
                .map(this::getProductDTO)
                .collect(Collectors.toList());
    }
}
