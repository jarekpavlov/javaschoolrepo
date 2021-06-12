package com.jschool.service;

import com.jschool.DTO.ClientDTO;
import com.jschool.domain.Address;
import com.jschool.domain.Client;
import com.jschool.exceptions.EmptyFieldException;
import com.jschool.exceptions.NonValidNumberException;
import com.jschool.exceptions.UserExists;
import com.jschool.security.Authority;
import com.jschool.security.CustomSecurityClient;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClientService {
    Logger logger = Logger.getLogger(this.getClass());
    private ModelMapper modelMapper;
    private EntityService entityService;
    private MailSender mailSender;

    public ClientService() {
    }

    @Autowired
    public ClientService(ModelMapper modelMapper, EntityService entityService, MailSender mailSender) {
        this.entityService = entityService;
        this.modelMapper = modelMapper;
        this.mailSender = mailSender;
    }

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public int saveClient(Client client, Client clientWithPassword) throws NonValidNumberException, EmptyFieldException {
        String emptyS = "";
        if ((emptyS.equals(client.getPassword()) && clientWithPassword == null) || emptyS.equals(client.getName()) || emptyS.equals(client.getSurname()) || emptyS.equals(client.getPhone())) {
            logger.warn("User does not fill all fields in client registration/editing page");
            throw new EmptyFieldException("All fields required to be filled!");
        }
        if (client.getAddress().getFlat() < 1 || client.getAddress().getPostCode() < 1) {
            logger.warn("User entered incorrect number ");
            throw new NonValidNumberException("Some numbers are incorrect");
        }

        if (entityService.getEntityByEmail(Client.class, client.getEmail()) != null && client.getId() == null) {
            return 1;
        }

        Address address = client.getAddress();

        if (client.getId() != null) {
            client.setPassword(clientWithPassword.getPassword());
            entityService.updateEntity(address);
            entityService.updateEntity(client);
            return 2;
        } else if (!client.getPassword().equals("")) {
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            String encodedPassword = encoder.encode(client.getPassword());
            client.setPassword(encodedPassword);
            client.setActivationCode(UUID.randomUUID().toString());
            entityService.saveEntity(address);
            entityService.saveEntity(client);
            if (!StringUtils.isEmpty(client.getEmail())) {
                String message = String.format(
                        "Hello %s! \n"+
                                "To confirm your registration, please, visit the link: http://localhost:8080/MmsPr/activate/%s"
                        ,client.getName()
                        ,client.getActivationCode()
                );
                mailSender.send(client.getEmail(), "Activation code",message);
            }
        }
        return 3;
    }
    public boolean activateClient(String code) {
        Client client = entityService.getEntityByActivationCode(Client.class, code);
        if (client == null) {
            return false;
        }
        client.setActivationCode(null);
        Authority authority = new Authority();
        authority.setAuthority("ROLE_USER");
        client.getAuthorities().add(authority);
        authority.setClient(client);
        entityService.updateEntity(client);
        return true;
    }

    public void saveClientWithChangedPassword(String newPassword1, String newPassword2, CustomSecurityClient customSecurityClient) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        if (newPassword1.equals(newPassword2)) {
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

    public Client getClientFromUserDetails(CustomSecurityClient customSecurityClient) {
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
