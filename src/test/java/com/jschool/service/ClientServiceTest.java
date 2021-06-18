package com.jschool.service;

import com.jschool.DTO.ClientDTO;
import com.jschool.domain.Address;
import com.jschool.domain.Client;
import com.jschool.domain.ClientBuilder;
import com.jschool.exceptions.EmptyFieldException;
import com.jschool.exceptions.NonValidNumberException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.HashSet;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    EntityService entityService;

    @Mock
    MailSender mailSender;

    @InjectMocks
    ClientService clientServiceInjected;

    ClientService clientService = new ClientService();

    @Test
    void getClientDTO() {

        clientService.setModelMapper(new ModelMapper());

        Client client = new ClientBuilder()
                .setPassword("asdf")
                .setId(2L)
                .setEmail("aa@aa")
                .setName("Name")
                .setSurname("Surname")
                .setDateOfBirth("11.11.1999")
                .setAuthorities(new HashSet<>())
                .setAddress(new Address())
                .setPhone("111111111")
                .build();

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
                    clientService.saveClient(client, clientWithPassword);
                });
    }

    @Test
    void clientWrongFormatEntryTest() {
        Client client = new ClientBuilder()
                .setName("name")
                .setSurname("surname")
                .setPhone("+7812212222")
                .build();
        Client clientWithPassword = new Client();
        Address address = new Address();
        address.setFlat((short) -1);
        client.setAddress(address);
        Assertions.assertThrows(NonValidNumberException.class,
                () -> {
                    clientService.saveClient(client, clientWithPassword);
                });
    }

    @Test
    void userExistsTest() throws NonValidNumberException, EmptyFieldException {
        Client client = new ClientBuilder()
                .setName("name")
                .setEmail("11@ya.ru")
                .setSurname("surname")
                .setPhone("+7812212222")
                .build();
        Client clientWithPassword = new Client();
        Address address = new Address();
        address.setFlat((short) 1);
        address.setPostCode(123332);
        client.setAddress(address);
        when(entityService.getEntityByEmail(Client.class, client.getEmail())).thenReturn(client);
        Assertions.assertEquals(1, clientServiceInjected.saveClient(client, clientWithPassword));

    }

    @Test
    void updateEntityTest() throws NonValidNumberException, EmptyFieldException {
        Client client = new ClientBuilder()
                .setId(5L)
                .setName("name")
                .setEmail("11@ya.ru")
                .setSurname("surname")
                .setPhone("+7812212222")
                .build();
        Client clientWithPassword = new Client();
        Address address = new Address();
        address.setFlat((short) 1);
        address.setPostCode(123332);
        client.setAddress(address);
        when(entityService.getEntityByEmail(Client.class, client.getEmail())).thenReturn(client);
        Assertions.assertEquals(2, clientServiceInjected.saveClient(client, clientWithPassword));
    }

    @Test
    void sendEmailTest() throws NonValidNumberException, EmptyFieldException {
        Client client = new ClientBuilder()
                .setName("name")
                .setEmail("11@ya.ru")
                .setSurname("surname")
                .setActivationCode("5")
                .setPhone("+7812212222")
                .setPassword("password")
                .build();
        Client clientWithPassword = new Client();
        Address address = new Address();
        address.setFlat((short) 1);
        address.setPostCode(123332);
        client.setAddress(address);
        when(entityService.getEntityByEmail(Client.class, client.getEmail())).thenReturn(null);
        Assertions.assertEquals(3, clientServiceInjected.saveClient(client, clientWithPassword));
    }

    @Test
    void activateClientTest() {
        Client client = new ClientBuilder()
                .setName("name")
                .setEmail("11@ya.ru")
                .setSurname("surname")
                .setActivationCode("5")
                .setPhone("+7812212222")
                .setPassword("password")
                .build();
        when(entityService.getEntityByActivationCode(Client.class, client.getActivationCode())).thenReturn(client);
        Assertions.assertTrue(clientServiceInjected.activateClient("5"));

    }



}