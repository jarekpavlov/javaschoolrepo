package com.jschool.service;

import com.jschool.DTO.ClientDTO;
import com.jschool.domain.Address;
import com.jschool.domain.Client;
import com.jschool.domain.ClientBuilder;
import com.jschool.exceptions.EmptyFieldException;
import com.jschool.exceptions.NonValidNumberException;
import com.jschool.security.CustomSecurityClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.HashSet;
import java.util.Properties;

class ClientServiceTest {

    ClientService clientService = new ClientService();

    @Test
    void getClientDTO() {

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

    @Test
    void clientEmptyFieldsTest() {

        Client client = new Client();
        client.setPassword("");
        Client clientWithPassword = null;
        Assertions.assertThrows(EmptyFieldException.class,
                () -> {
                    clientService.clientEmptyFields(client, clientWithPassword);
                });
    }

    @Test
    void clientWrongFormatEntryTest() {
        Client client = new Client();
        Address address = new Address();
        address.setFlat((short) -1);
        client.setAddress(address);
        Assertions.assertThrows(NonValidNumberException.class,
                () -> {
                    clientService.clientWrongFormatEntry(client);
                });
    }

    @Test
    void getActivatedClientTest() {

        Client emptyClient = new Client();
        emptyClient.setActivationCode("test");
        clientService.getActivatedClient(emptyClient);
        Assertions.assertNull(emptyClient.getActivationCode());

    }

    @Test
    void getClientFromUserDetailsTest() {
        Client client = new ClientBuilder()
                .setId(1L)
                .setName("Test")
                .build();
        CustomSecurityClient customSecurityClient = new CustomSecurityClient(client);
        Assertions.assertEquals(clientService.getClientFromUserDetails(customSecurityClient), client);
    }

    @Test
    void clientCreationSequenceTest() {
        String password = "Test";
        Client client = new ClientBuilder()
                .setPassword(password)
                .build();
        clientService.clientCreationSequence(client);
        Assertions.assertNotEquals(password, client.getPassword());
        Assertions.assertNotNull(client.getActivationCode());
    }

}