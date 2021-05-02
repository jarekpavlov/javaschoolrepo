package com.jschool.controllers;

import com.jschool.domain.Address;
import com.jschool.domain.Client;
import com.jschool.service.EntityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class UserController {

    private EntityService entityService;

    public UserController(EntityService entityService) {
        this.entityService = entityService;
    }

    @GetMapping(value = "/users")
    public String getUsers(ModelMap map) {
        List<Client> userList = entityService.entityList(Client.class);
        map.addAttribute("userList", userList);
        return "users";
    }

    @GetMapping(value = "/users/registration")
    public String registrationMeth(ModelMap map) {
        Client client = new Client();
        map.addAttribute("client", client);
        return "registrationPage";
    }

    @GetMapping(value = "/users/edit")
    public String userEdit(HttpServletRequest request, ModelMap map) {
        Long id = Long.parseLong(request.getParameter("id"));

        Client client = entityService.getEntity(Client.class, id);
        map.addAttribute(client);
        return "registrationPage";
    }

    @PostMapping(value = "/users/save")
    public String saveUser(Client client) {
        Address address = client.getAddress();
        if (client.getId() != null) {
            entityService.updateEntity(address);
            entityService.updateEntity(client);
        } else {
            entityService.saveEntity(address);
            entityService.saveEntity(client);
        }
        return "redirect:/";
    }

    @GetMapping(value = "/users/delete")
    public String deleteUser(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"));
        entityService.deleteEntity(Client.class, id);
        return "redirect:/users";
    }
}
