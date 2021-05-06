package com.jschool.controllers;

import com.jschool.DTO.ClientDTO;
import com.jschool.domain.Client;
import com.jschool.service.ClientService;
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
    private ClientService clientService;

    public UserController(EntityService entityService, ClientService clientService) {
        this.entityService = entityService;
        this.clientService = clientService;
    }

    @GetMapping(value = "/admin/users")
    public String getUsers(ModelMap map) {
        List<ClientDTO> userList = clientService.getClientDtoList();
        map.addAttribute("userList", userList);
        return "users";
    }

    @GetMapping(value = "/users/registration/register")
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

    @PostMapping(value = "/users/registration/save")
    public String saveUser(Client client) {
        clientService.saveClient(client);
        return "redirect:/";
    }

    @GetMapping(value = "/admin/users/delete")
    public String deleteUser(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"));
        entityService.deleteEntity(Client.class, id);
        return "redirect:/admin/users";
    }
}
