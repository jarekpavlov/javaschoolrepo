package com.jschool.service;

import com.jschool.DTO.ClientDTO;
import com.jschool.domain.Address;
import com.jschool.domain.Client;
import com.jschool.exceptions.EmptyFieldException;
import com.jschool.exceptions.NonValidNumberException;
import com.jschool.security.Authority;
import com.jschool.security.CustomSecurityClient;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {
    Logger logger = Logger.getLogger(this.getClass());
    private ModelMapper modelMapper;
    private EntityService entityService;

    public ClientService() {}

    @Autowired
    public ClientService(ModelMapper modelMapper, EntityService entityService) {
        this.entityService = entityService;
        this.modelMapper = modelMapper;
    }

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public void saveClient(Client client, Client clientWithPassword) throws NonValidNumberException, EmptyFieldException {
        String emptyS = "";
        if ((emptyS.equals(client.getPassword()) && clientWithPassword == null) || emptyS.equals(client.getName()) || emptyS.equals(client.getSurname()) || emptyS.equals(client.getPhone())) {
            logger.warn("User does not fill all fields in client registration/editing page");
            throw new EmptyFieldException("All fields required to be filled!");
        }
        if(client.getAddress().getFlat()<1 || client.getAddress().getPostCode()<1){
            logger.warn("User entered incorrect number ");
            throw new NonValidNumberException("Some numbers are incorrect");
        }

        Address address = client.getAddress();

        if (client.getId() != null) {
            client.setPassword(clientWithPassword.getPassword());
            entityService.updateEntity(address);
            entityService.updateEntity(client);
        } else if (!client.getPassword().equals("")) {
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            String encodedPassword = encoder.encode(client.getPassword());
            client.setPassword(encodedPassword);
            Authority authority = new Authority();
            authority.setAuthority("ROLE_USER");
            client.getAuthorities().add(authority);
            authority.setClient(client);
            entityService.saveEntity(address);
            entityService.saveEntity(client);
        }
    }

    public void saveClientWithChangedPassword(String newPassword1, String newPassword2, CustomSecurityClient customSecurityClient){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        if (newPassword1.equals(newPassword2)){
            String password = encoder.encode(newPassword1);
            customSecurityClient.setPassword(password);
            Client client = getClientFromUserDetails(customSecurityClient);
            entityService.updateEntity(client);
        }
    }

    public ClientDTO getClientDTO(Client client) {

        getModelMapper()
                .getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return getModelMapper().map(client, ClientDTO.class);
    }

    public Client getClientFromUserDetails(CustomSecurityClient customSecurityClient){
       Client client = new Client();
       client.setPhone(customSecurityClient.getPhone());
       client.setAddress(customSecurityClient.getAddress());
       client.setAuthorities(customSecurityClient.getAuthorities());
       client.setSurname(customSecurityClient.getSurname());
       client.setName(customSecurityClient.getName());
       client.setEmail(customSecurityClient.getEmail());
       client.setPassword(customSecurityClient.getPassword());
       client.setId(customSecurityClient.getId());
       client.setDateOfBirth(customSecurityClient.getDateOfBirth());
       client.setOrders(customSecurityClient.getOrders());

        return client;
    }

    public List<ClientDTO> getClientDtoList() {
        return entityService.entityList(Client.class)
                .stream()
                .map(this::getClientDTO)
                .collect(Collectors.toList());
    }
}
