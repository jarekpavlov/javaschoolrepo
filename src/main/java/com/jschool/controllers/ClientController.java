package com.jschool.controllers;

import com.jschool.domain.Address;
import com.jschool.domain.AddressBuilder;
import com.jschool.domain.Client;
import com.jschool.domain.ClientBuilder;
import com.jschool.exceptions.ChangePasswordException;
import com.jschool.exceptions.EmptyFieldException;
import com.jschool.exceptions.NonValidNumberException;
import com.jschool.security.CustomSecurityClient;
import com.jschool.service.ClientService;
import com.jschool.service.EntityService;
import com.jschool.service.ProductService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ClientController {

    @Value("${orders.list.quantity}")
    private int clientsOnPage;

    Logger logger = Logger.getLogger(this.getClass());
    private EntityService entityService;
    private ClientService clientService;
    private ProductService productService;

    public ClientController(EntityService entityService, ClientService clientService, ProductService productService) {
        this.entityService = entityService;
        this.clientService = clientService;
        this.productService = productService;
    }

    @GetMapping(value = "/admin/users")
    public String getUsers(ModelMap map, @RequestParam(required = false) Integer page) {
        map.addAttribute("userList", clientService.getUsersPaginated(page));
        map.addAttribute("pageQuantity", productService.getPageQuantity(clientService.getClientDtoList(0, Integer.MAX_VALUE), clientsOnPage));
        return "users";
    }

    @GetMapping(value = "/users/registration/register")
    public String registrationMeth(ModelMap map, @AuthenticationPrincipal Client client, HttpSession httpSession) {
        logger.info("User creation/editing method was entered");
        if (client == null) {
            client = new Client();
        }
        map.addAttribute("client", client);
        map.addAttribute("productsInCart", productService.getProductInCartQuantity(httpSession));
        return "registrationPage";
    }

    @GetMapping(value = "/users/edit")
    public String userEdit(HttpServletRequest request, ModelMap map, HttpSession httpSession) {
        logger.info("User editing method was entered");
        Long id = Long.parseLong(request.getParameter("id"));
        Client client = entityService.getEntity(Client.class, id);
        map.addAttribute(client);
        map.addAttribute("productsInCart", productService.getProductInCartQuantity(httpSession));
        return "registrationPage";
    }

    @PostMapping(value = "/users/registration/save")
    public String saveUser(@AuthenticationPrincipal Client clientWithPassword, ModelMap map
            , @RequestParam(required = false) String name
            , @RequestParam(required = false) String surname
            , @RequestParam(required = false) String country
            , @RequestParam(required = false) String city
            , @RequestParam(required = false) String street
            , @RequestParam(required = false) String house
            , @RequestParam(required = false) String password
            , @RequestParam(required = false) String repeatPassword
            , @RequestParam(required = false) String phone
            , @RequestParam(required = false) String dateOfBirth
            , @RequestParam(required = false) String email
            , @RequestParam(required = false) Long id
            , @RequestParam(required = false) Long address_id
            , @RequestParam(required = false) Short flat
            , @RequestParam(required = false) Integer postCode) throws EmptyFieldException, NonValidNumberException {
        if (!(StringUtils.isEmpty(password) || StringUtils.isEmpty(repeatPassword)))
            if (!password.equals(repeatPassword)){
                map.addAttribute("clientFailure", "The passwords didn't match");//user already exists
                return "registrationPage";
            }
        Address address = new AddressBuilder()
                .setId(address_id)
                .setCity(city)
                .setCountry(country)
                .setStreet(street)
                .setFlat(flat)
                .setHouse(house)
                .setPostCode(postCode)
                .build();
        Client client = new ClientBuilder()
                .setName(name)
                .setSurname(surname)
                .setId(id)
                .setAddress(address)
                .setDateOfBirth(dateOfBirth)
                .setEmail(email)
                .setPhone(phone)
                .setPassword(password)
                .build();
        int savingAction = clientService.saveClient(client, clientWithPassword);
        switch (savingAction){
            case 1:
                map.addAttribute("clientFailure", "The user already exists");//user already exists
                return "registrationPage";
            case 2:
                map.addAttribute("clientSuccess", "Client information was successfully changed");//changing client that already exists
                return "registrationPage";
        }
        logger.info("The user creation/editing method was accomplished");
        map.addAttribute("clientSuccess", "The confirmation mail was send to your email");
        return "registrationPage";//creating a brand new client

    }

    @GetMapping(value = "/user/registration/change-password")
    public String getChangePassword() {
        return "changePassword";
    }

    @PostMapping(value = "/user/registration/change-password")
    public String changePassword(@RequestParam String newPassword1
            , @RequestParam String newPassword2, @AuthenticationPrincipal CustomSecurityClient client, ModelMap map) throws ChangePasswordException {
        if (!newPassword1.equals(newPassword2)) {
            logger.warn("The passwords didn't match during the changing");
            map.addAttribute("passwordMatching","The passwords didn't match");
            return "changePassword";
        }
        clientService.saveClientWithChangedPassword(newPassword1, newPassword2, client);
        return "redirect:/";
    }

    @GetMapping(value = "/admin/users/delete")
    public String deleteUser(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"));
        entityService.deleteEntity(Client.class, id);
        return "redirect:/admin/users";
    }

    @GetMapping(value = "/activate/{code}")
    public String activateAccount(ModelMap map, @PathVariable String code) {
        boolean isActivated = clientService.activateClient(code);
        if (!isActivated) {
            map.addAttribute("clientFailure", "The activation code is not found.");
        } else {
            map.addAttribute("clientSuccess", "The client was successfully activated.");
        }
        return "registrationPage";
    }

}
