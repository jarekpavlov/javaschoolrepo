package com.jschool.controllers;

import com.jschool.DAO.ClientDaoImpl;
import com.jschool.domain.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.persistence.Query;
import java.util.List;

@Controller
public class HelloController {

    private ClientDaoImpl clientDao;
    @Autowired
    public HelloController(ClientDaoImpl clientDao){
        this.clientDao = clientDao;
    }

    @RequestMapping(value = "",method = RequestMethod.GET)
    public String hello(ModelMap map){
//        Client client = new Client();
//        client.setAddress("India");
//        client.setDateofbirth("12.12.1999");
//        client.setName("Farad");
//        client.setSurname("Farad");
//        client.setPhone("+78///");
//        clientDao.save(client);
        List<Client> clients = clientDao.clientList();
        map.addAttribute("clients",clients);

        return "hello";
    }

}
