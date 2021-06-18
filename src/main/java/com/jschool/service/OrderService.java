package com.jschool.service;

import com.jschool.DTO.OrderDTO;
import com.jschool.count.JoinCountByProduct;
import com.jschool.domain.*;
import com.jschool.exceptions.NonValidNumberException;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Value("${orders.list.quantity}")
    private int ordersOnPage;

    @Value("${statistic.days}")
    private int statisticDays;


    @Value("${queue.name}")
    private String queueName;

    private EntityService entityService;
    private ModelMapper modelMapper;
    private AmqpTemplate template;
    private Logger logger = Logger.getLogger(this.getClass());
    private ProductService productService;

    public OrderService() {
    }

    @Autowired
    public OrderService(EntityService entityService, ProductService productService, ModelMapper modelMapper, AmqpTemplate template) {
        this.entityService = entityService;
        this.modelMapper = modelMapper;
        this.template = template;
        this.productService = productService;
    }

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * This method is taking @param numberForOrder to create a session using
     *
     * @param httpSession with ProductsInOrder instance to store the Products into the cart
     */
    public void addToCart(int numberForOrder, HttpSession httpSession, Long id) {

        Product product = entityService.getEntity(Product.class, id);
        ProductsInOrder productsInOrder = new ProductsInOrderBuilder()
                .setProduct(product)
                .setQuantity(numberForOrder)
                .setPrice(product.getPrice())
                .build();

        Set<ProductsInOrder> productsInOrderSet = (Set<ProductsInOrder>) httpSession.getAttribute("productsInOrderSet");
        if (productsInOrderSet != null) {
            productsInOrderSet.add(productsInOrder);
            httpSession.setAttribute("productsInOrderSet", productsInOrderSet);
        } else {
            Set<ProductsInOrder> temp = new HashSet<>();
            temp.add(productsInOrder);
            httpSession.setAttribute("productsInOrderSet", temp);
        }
    }

    /**
     * The method is taking the session instance using
     *
     * @param httpSession to a new Order object to store it
     *                    into the database
     */
    private void createOrder(HttpSession httpSession, Client client, String paymentMethod, String deliveryMethod) throws NonValidNumberException {
        Set<ProductsInOrder> productsInOrderSetTemp = (Set<ProductsInOrder>) httpSession.getAttribute("productsInOrderSet");
        Order order = getPopulatedOrder(client, paymentMethod, deliveryMethod, productsInOrderSetTemp);//populating order fields with actual information
        for (ProductsInOrder temp : productsInOrderSetTemp) {
            temp.setOrder(order);
            Long product_id = temp.getProduct().getId();
            Product product = entityService.getEntity(Product.class, product_id);
            int newQuantity = product.getQuantity() - temp.getQuantity();
            product.setQuantity(newQuantity);
            entityService.updateEntity(product);
        }
        entityService.saveEntity(order);
        httpSession.removeAttribute("productsInOrderSet");
    }

    private Order getPopulatedOrder(Client client, String paymentMethod, String deliveryMethod, Set<ProductsInOrder> productsInOrderSetTemp) {
        Date date = new Date();
        return new OrderBuilder()
                .setDateOfOrder(date)
                .setClient(client)
                .setDeliveryMethod(deliveryMethod)
                .setPayment(paymentMethod)
                .setProductsInOrderSet(productsInOrderSetTemp)
                .setOrderStatus(OrderStatus.PENDING_PAYMENT)
                .setPaymentStatus(PaymentStatus.PENDING_PAYMENT)
                .build();
    }

    public void deleteFromCart(HttpSession session, Long id) {
        Set<ProductsInOrder> productsInOrderSet = (Set<ProductsInOrder>) session.getAttribute("productsInOrderSet");
        Iterator<ProductsInOrder> itr = productsInOrderSet.iterator();
        while (itr.hasNext()) {
            if (itr.next().getProduct().getId().equals(id)) {
                itr.remove();
                break;
            }
        }
        session.setAttribute("productsInOrderSet", productsInOrderSet);
    }

    public void saveFromCart(Map<String, String> quantityMap, HttpSession session, @AuthenticationPrincipal Client client, String paymentMethod, String deliveryMethod) throws NonValidNumberException {
        if (paymentMethod == null)
            paymentMethod = "Card";
        if (deliveryMethod == null)
            deliveryMethod = "Delivery to the store";
        Set<ProductsInOrder> productsInOrderSet = (Set<ProductsInOrder>) session.getAttribute("productsInOrderSet");
        setProductQuantity(quantityMap, productsInOrderSet);//Setting product number that was taken from the cart page
        session.setAttribute("productsInOrderSet", productsInOrderSet);
        createOrder(session, client, paymentMethod, deliveryMethod);
    }

    private void setProductQuantity(Map<String, String> quantityMap, Set<ProductsInOrder> productsInOrderSet) throws NonValidNumberException {
        for (ProductsInOrder temp : productsInOrderSet) {
            Long productId = temp.getProduct().getId();
            String quantity = quantityMap.get(String.valueOf(productId));
            if (Integer.parseInt(quantity) < 1) {
                throw new NonValidNumberException("Some numbers are incorrect");
            }
            temp.setQuantity(Integer.parseInt(quantity));
        }
    }

    public Set<ProductsInOrder> getProductsInOrder(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"));
        Order order = entityService.getEntity(Order.class, id);
        return order.getProductsInOrderSet();
    }

    public OrderDTO getOrderFromJoinTable(Set<ProductsInOrder> productsInOrderSet) {
        ProductsInOrder productsInOrder = new ProductsInOrder();
        for (ProductsInOrder entity : productsInOrderSet) {
            productsInOrder = entity;
            break;
        }
        Order order = productsInOrder.getOrder();
        return getOrderDTO(order);
    }

    public void saveOrderStatus(OrderStatus orderStatus, PaymentStatus paymentStatus, Long id) {

        Set<JoinCountByProduct> bestProductBefore = entityService.getBestProduct(statisticDays);

        Order order = entityService.getEntity(Order.class, id);
        order.setOrderStatus(orderStatus);
        order.setPaymentStatus(paymentStatus);
        entityService.updateEntity(order);
        Set<JoinCountByProduct> bestProductAfter = entityService.getBestProduct(statisticDays);
        if (!bestProductAfter.equals(bestProductBefore)) {
            try {
                template.convertAndSend(queueName, "The best products list is changed");
            } catch (Exception e) {
                logger.error("You have no mq connection");
            }
        }
    }

    public List<OrderDTO> getPaginatedOrderList(Integer page) {
        List<OrderDTO> orderListPaginated;

        if (page == null) {
            orderListPaginated = getOrderDtoList(0, ordersOnPage);
        } else {
            orderListPaginated = getOrderDtoList(((page - 1) * ordersOnPage), ordersOnPage);
        }
        return orderListPaginated;
    }

    public OrderDTO getOrderDTO(Order order) {
        getModelMapper()
                .getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return getModelMapper().map(order, OrderDTO.class);
    }

    public List<OrderDTO> getOrderDtoList(int offset, int limit) {
        return (entityService.entityList(Order.class, offset, limit))
                .stream()
                .map(this::getOrderDTO)
                .collect(Collectors.toList());
    }

    public double getTotalPerOrder(Set<ProductsInOrder> productsInOrderSet) {
        double totalPerOrder = 0D;
        for (ProductsInOrder product : productsInOrderSet) {
            totalPerOrder = totalPerOrder + product.getPrice() * product.getQuantity();
        }
        return totalPerOrder;
    }
}
