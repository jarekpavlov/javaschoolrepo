package com.jschool.controllers;

import com.jschool.DAO.EntityDaoImpl;
import com.jschool.domain.Address;
import com.jschool.domain.Client;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class UserController {

    private EntityDaoImpl entityDaoImpl;

    public UserController(EntityDaoImpl entityDaoImpl){
        this.entityDaoImpl=entityDaoImpl;
    }

    @GetMapping(value = "/users")
    public String getUsers(ModelMap map){
        List<Client> userList = entityDaoImpl.entityList(Client.class);
        map.addAttribute("userList", userList);
        return "users";
    }
    @GetMapping(value = "/users/registration")
    public String registrationMeth(ModelMap map){
        Client client = new Client();
        map.addAttribute("client",client);
        return "registrationPage";
    }
    @GetMapping(value = "/users/edit")
    public String userEdit(HttpServletRequest request,ModelMap map){
        Long id = Long.parseLong(request.getParameter("id"));

        Client client = entityDaoImpl.getEntity(Client.class, id);
        map.addAttribute(client);
        return "registrationPage";
    }
    @PostMapping(value = "/users/save")
    public String saveUser(Client client){
        Address address = client.getAddress();
        if(client.getId()!=null) {
            entityDaoImpl.update(address);
            entityDaoImpl.update(client);
        }
        else {
            entityDaoImpl.saveEntity(address);
            entityDaoImpl.saveEntity(client);
        }
        return "redirect:/";
    }
    @GetMapping(value = "/users/delete")
    public String deleteUser(HttpServletRequest request){
        Long id = Long.parseLong(request.getParameter("id"));
        entityDaoImpl.delete(Client.class, id);
        return "redirect:/users";
    }
}
