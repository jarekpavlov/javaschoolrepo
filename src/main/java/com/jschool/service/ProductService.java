package com.jschool.service;

import com.jschool.DTO.ProductDTO;
import com.jschool.domain.Client;
import com.jschool.domain.Product;
import com.jschool.domain.ProductsInOrder;
import com.jschool.domain.ProductsWithUser;
import com.jschool.exceptions.EmptyFieldException;
import com.jschool.exceptions.NonValidNumberException;
import com.jschool.exceptions.ProductIsInOrderException;
import com.jschool.security.Authority;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final String emptyS = "";

    @Value("${upload.path}")
    private String uploadPath;

    @Value("${products.list.quantity}")
    private int prodListQuantity;

    Logger logger = Logger.getLogger(this.getClass());
    private ModelMapper modelMapper;
    private EntityService entityService;

    public ProductService() {
    }

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
     *
     * @throws EmptyFieldException
     * @throws NonValidNumberException
     * @throws IOException
     */
    public boolean saveProduct(Product product, MultipartFile productPicture) throws EmptyFieldException, NonValidNumberException, IOException {

        if (productPicture.getSize() != 0) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidName = UUID.randomUUID().toString();
            String resultName = uuidName + "-" + productPicture.getOriginalFilename();
            product.setImgName(resultName);
            productPicture.transferTo(new File(uploadPath + "/" + resultName));
        }

        emptyFieldsExceptionCheck(product);
        nonValidExceptionCheck(product);
        if (product.getId() != null)
            entityService.updateEntity(product);
        else {
            entityService.saveEntity(product);
        }
        logger.info("Employee left the saveProduct service method");
        return true;
    }

    private void nonValidExceptionCheck(Product product) throws NonValidNumberException {
        if (product.getPrice() < 0.1 || product.getMass() < 0 || product.getQuantity() < 0) {
            logger.warn("Employee entered incorrect number ");
            throw new NonValidNumberException("Some numbers are incorrect");
        }
    }

    private void emptyFieldsExceptionCheck(Product product) throws EmptyFieldException {
        if (emptyS.equals(product.getBrand()) || emptyS.equals(product.getTitle()) || emptyS.equals(product.getCategory())
                || product.getMass() == null || product.getQuantity() == null || product.getPrice() == null) {
            logger.warn("Not all fields were filled in saveProduct service method");
            throw new EmptyFieldException("All fields required to be filled!");
        }
    }

    /**
     * Method returns a collection of ProductDTO entities filtered by:
     *
     * @param color
     * @param brand
     * @param title
     * @return
     */
    public List<ProductDTO> getFilteredProducts(List<ProductDTO> productDtoList, String color, String brand, String title) {
        List<ProductDTO> filteredList = null;
        if (!StringUtils.isEmpty(color)) {
            filteredList = productDtoList
                    .stream()
                    .filter(l -> l.getColor().equalsIgnoreCase(color))
                    .collect(Collectors.toList());
        }
        if (!StringUtils.isEmpty(brand)) {
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
        if (!StringUtils.isEmpty(title)) {
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
        if (filteredList == null)
            return productDtoList;
        return filteredList;
    }

    private boolean filterIsEmpty(String color, String brand, String title) {
        if ((emptyS.equals(color) || color == null) && (emptyS.equals(brand) || brand == null)
                && (emptyS.equals(title) || title == null)) {
            return true;
        }
        return false;
    }

    /**
     * The method is picking the pagination method regarding to whether filter
     * was used or not.
     * @param page
     * @param color
     * @param brand
     * @param title
     * It returns paginated ProductDTO list
     * @return
     */
    public List<ProductDTO> getPaginationMethod(Integer page, String color, String brand, String title) {
        if (filterIsEmpty(color, brand, title)) {
            return getPaginatedList(page);
        }
        return getFilteredProductsPaginated(page, color, brand, title);
    }

    /**
     * The method returns ProductDTO list filtered by params:
     * @param color
     * @param brand
     * @param title
     * if params are empty the method returns full unfiltered list
     * @return
     */
    public List<ProductDTO> getFullOrFilteredList(String color, String brand, String title) {
        if (filterIsEmpty(color, brand, title)) {
            return getProductDtoList(0, Integer.MAX_VALUE);
        }
        return getFilteredProducts(getProductDtoList(0, Integer.MAX_VALUE), color, brand, title);
    }

    private List<ProductDTO> getFilteredProductsPaginated(Integer page, String color, String brand, String title) {
        if (page == null) {
            page = 1;
        }
        List<ProductDTO> productList = getProductDtoList(0, Integer.MAX_VALUE);
        List<ProductDTO> filteredList = getFilteredProducts(productList, color, brand, title);
        return formingFilteredPaginatedProductList(page, filteredList, prodListQuantity);
    }

    private List<ProductDTO> formingFilteredPaginatedProductList(Integer page, List<ProductDTO> filteredList, int prodListQuantity) {
        List<ProductDTO> paginatedFilteredList = new ArrayList<>();
        int size = filteredList.size();
        int end = (page - 1) * prodListQuantity + prodListQuantity;
        if (size < prodListQuantity) {
            paginatedFilteredList.addAll(filteredList);
        } else if (size < end) {
            for (int i = ((page - 1) * prodListQuantity); i < size; i++) {
                paginatedFilteredList.add(filteredList.get(i));
            }
        } else {
            for (int i = ((page - 1) * prodListQuantity); i < ((page - 1) * prodListQuantity) + prodListQuantity; i++) {
                paginatedFilteredList.add(filteredList.get(i));
            }
        }
        return paginatedFilteredList;
    }

    /**
     * The method deletes Product from the database by id which
     * is taken from request
     * @param request
     * @throws ProductIsInOrderException
     */
    public void deleteProduct(HttpServletRequest request) throws ProductIsInOrderException {
        List<ProductsInOrder> productsInOrderList = entityService.entityList(ProductsInOrder.class);
        Long id = Long.parseLong(request.getParameter("id"));
        Product product = entityService.getEntity(Product.class, id);
        for (ProductsInOrder productsInOrder : productsInOrderList) {
            if (productsInOrder.getProduct().equals(product)) {
                logger.warn("Employee tried to delete a product which is in an order");
                throw new ProductIsInOrderException("The product is in an order. You should delete the order first!");
            }
        }
        entityService.deleteEntity(Product.class, id);
    }

    /**
     * The method returns the quantity of products which are present
     * in product cart and therefore in the httpsession
     * @param httpSession
     * @return
     */
    public int getProductInCartQuantity(HttpSession httpSession) {
        Set<ProductsInOrder> productsInOrderSet = (Set<ProductsInOrder>) httpSession.getAttribute("productsInOrderSet");
        if (productsInOrderSet != null) {
            return productsInOrderSet.size();
        }
        return 0;
    }

    private List<ProductDTO> getPaginatedList(Integer page) {
        List<ProductDTO> productListPaginated;

        if (page == null) {
            productListPaginated = getProductDtoList(0, prodListQuantity);
        } else {
            productListPaginated = getProductDtoList(((page - 1) * prodListQuantity), prodListQuantity);
        }
        return productListPaginated;
    }

    /**
     * The method calculates the quantity of paginated pages
     * regarding to the list passed to the method
     * @param list
     * @param quantity
     * @return
     */
    public int getPageQuantity(List<?> list, int quantity) {
        int pageQuantity;
        if ((list.size() / (double) quantity) % 1 != 0) {
            pageQuantity = list.size() / quantity + 1;
        } else {
            pageQuantity = list.size() / quantity;
        }
        return pageQuantity;
    }

    public ProductDTO getProductDTO(Product product) {
        getModelMapper()
                .getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return getModelMapper().map(product, ProductDTO.class);
    }

    public List<ProductDTO> getProductDtoList(int offset, int limit) {

        List<Product> list1 = entityService.entityList(Product.class, offset, limit);
        System.out.println(list1);
        List<ProductDTO> list = list1
                .stream()
                .map(this::getProductDTO)
                .collect(Collectors.toList());
        System.out.println(5);
        return list;
    }

    /**
     * The method is returning the object which consist of the ProductDTO list
     * and client role for sending it as JSON in controller for the frontend
     * @param list
     * @param client
     * @return
     */
    public ProductsWithUser getProductsWithUser(List<ProductDTO> list, Client client) {
        String clientAuthority = null;
        String employeeRole = "ROLE_EMPLOYEE";
        if (client != null) {
            Set<Authority> authorities = client.getAuthorities();
            for (Authority authority : authorities) {
                if (employeeRole.equals(authority.getAuthority())) {
                    clientAuthority = employeeRole;
                    break;
                }
            }
        }
        ProductsWithUser productsWithUser = new ProductsWithUser();
        productsWithUser.setRole(clientAuthority);
        productsWithUser.setProducts(list);
        return productsWithUser;
    }
}
