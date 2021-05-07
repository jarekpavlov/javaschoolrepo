package com.jschool.controllers;

import com.jschool.count.ClientCount;
import com.jschool.domain.Client;
import com.jschool.domain.Order;
import com.jschool.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Controller
public class HelloController {

    @Autowired
    private EntityService entityService;

    @GetMapping(value = "/test")
    public String hello() {
        List<Order> list = entityService.getPeriodOrders(10);
        Set<ClientCount> clientCountSet = new TreeSet<>();
        for (Order order : list) {
            Client client = order.getClient();
            ClientCount clientCount = new ClientCount();
            boolean isAdded = false;
            if (!clientCountSet.isEmpty()) {
                Iterator<ClientCount> itr = clientCountSet.iterator();
                while (itr.hasNext()) {
                    ClientCount clientCountItr = itr.next();
                    if (clientCountItr.getClient().equals(client)) {
                        int i = clientCountItr.getQuantity();
                        clientCount.setQuantity(++i);
                        clientCount.setClient(clientCountItr.getClient());
                        itr.remove();
                        isAdded = true;
                        break;
                    }
                }
            }
            if (!isAdded) {
                clientCount.setClient(client);
                clientCount.setQuantity(1);
            }
            clientCountSet.add(clientCount);
        }
        for (ClientCount clientCount : clientCountSet) {
            System.out.println(clientCount);
        }
        return "users";
    }

    @GetMapping(value = "")
    public String startPage() {
        return "startPage";
    }


}
