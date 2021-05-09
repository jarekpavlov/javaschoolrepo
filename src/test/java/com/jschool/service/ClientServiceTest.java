package com.jschool.service;

import com.jschool.DTO.ClientDTO;
import com.jschool.domain.Address;
import com.jschool.domain.Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.HashSet;

class ClientServiceTest {

    @Test
    void getClientDTO() {

        ClientService clientService = new ClientService();
        clientService.setModelMapper(new ModelMapper());

        Client client = new Client();

        client.setPassword("asdf");
        client.setId(2L);
        client.setEmail("aa@aa");
        client.setName("Name");
        client.setSurname("Surname");
        client.setDateOfBirth("11.11.1999");
        client.setAuthorities(new HashSet<>());
        client.setAddress(new Address());
        client.setPhone("111111111");

        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(2L);
        clientDTO.setEmail("aa@aa");
        clientDTO.setName("Name");
        clientDTO.setSurname("Surname");
        clientDTO.setDateOfBirth("11.11.1999");
        clientDTO.setAddress(new Address());
        clientDTO.setPhone("111111111");

        Assertions.assertEquals(clientDTO, clientService.getClientDTO(client));
    }
}