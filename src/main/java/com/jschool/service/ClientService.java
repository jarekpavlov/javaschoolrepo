package com.jschool.service;

import com.jschool.DTO.ClientDTO;
import com.jschool.domain.Address;
import com.jschool.domain.Client;
import com.jschool.domain.ClientBuilder;
import com.jschool.exceptions.EmptyFieldException;
import com.jschool.exceptions.NonValidNumberException;
import com.jschool.security.Authority;
import com.jschool.security.CustomSecurityClient;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Value("${orders.list.quantity}")
    private int clientsOnPage;

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

    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * is used to save a new client, save the checking email, edit an already created client
     * @param client
     * @param clientWithPassword
     * @return
     * @throws NonValidNumberException
     * @throws EmptyFieldException
     */
    public int saveClient(Client client, Client clientWithPassword) throws NonValidNumberException, EmptyFieldException {

        clientEmptyFields(client, clientWithPassword);//If Some fields are empty throwing an exception
        clientWrongFormatEntry(client);////If Some fields are filled using wrong format throwing an exception

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
            clientCreationSequence(client);//Encoding password and creating unique activation code, setting it to the client
            entityService.saveEntity(address);
            entityService.saveEntity(client);
            sendEmail(client);//sending the registration confirmation email
        }
        return 3;
    }

    private void clientCreationSequence(Client client) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(client.getPassword());
        client.setPassword(encodedPassword);
        client.setActivationCode(UUID.randomUUID().toString());
    }

    private void sendEmail(Client client) {
        if (!StringUtils.isEmpty(client.getEmail())) {
            String message = String.format(
                    "Hello %s! \n" + "To confirm your registration, please, visit the link: http://localhost:8080/MmsPr/activate/%s"
                    , client.getName()
                    , client.getActivationCode());
            mailSender.send(client.getEmail(), "Activation code", message);
        }
    }

    private void clientWrongFormatEntry(Client client) throws NonValidNumberException {
        if (client.getAddress().getFlat() < 1 || client.getAddress().getPostCode() < 1) {
            logger.warn("User entered incorrect number ");
            throw new NonValidNumberException("Some numbers are incorrect");
        }
    }

    private void clientEmptyFields(Client client, Client clientWithPassword) throws EmptyFieldException {
        String emptyS = "";
        if ((emptyS.equals(client.getPassword()) && clientWithPassword == null) || emptyS.equals(client.getName()) || emptyS.equals(client.getSurname()) || emptyS.equals(client.getPhone())) {
            logger.warn("User does not fill all fields in client registration/editing page");
            throw new EmptyFieldException("All fields required to be filled!");
        }
    }

    /**
     * Activates a client that is registering by searching the activation code
     * in the database
     * @param code
     * @return
     */
    public boolean activateClient(String code) {
        Client client = entityService.getEntityByActivationCode(Client.class, code);
        if (client == null) {
            return false;
        }
        getActivatedClient(client); //Populating client fields with the information that is needed
        entityService.updateEntity(client);
        return true;
    }

    private void getActivatedClient(Client client) {
        client.setActivationCode(null);
        Authority authority = new Authority();
        authority.setAuthority("ROLE_USER");
        authority.setClient(client);
        client.getAuthorities().add(authority);
    }

    /**
     * is used for changing the client password that already exists
     * @param newPassword1
     * @param newPassword2
     * @param customSecurityClient
     */
    public void saveClientWithChangedPassword(String newPassword1, String newPassword2, CustomSecurityClient customSecurityClient) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        if (newPassword1.equals(newPassword2)) {
            String password = encoder.encode(newPassword1);
            customSecurityClient.setPassword(password);
            entityService.updateEntity(getClientFromUserDetails(customSecurityClient));
        }
    }

    private Client getClientFromUserDetails(CustomSecurityClient customSecurityClient) {
        return new ClientBuilder()
                .setPhone(customSecurityClient.getPhone())
                .setAddress(customSecurityClient.getAddress())
                .setAuthorities(customSecurityClient.getAuthorities())
                .setSurname(customSecurityClient.getSurname())
                .setName(customSecurityClient.getName())
                .setEmail(customSecurityClient.getEmail())
                .setPassword(customSecurityClient.getPassword())
                .setId(customSecurityClient.getId())
                .setDateOfBirth(customSecurityClient.getDateOfBirth())
                .setOrders(customSecurityClient.getOrders())
                .build();
    }

    public List<ClientDTO> getUsersPaginated(Integer page) {
        List<ClientDTO> clientListPaginated;

        if (page == null) {
            clientListPaginated = getClientDtoList(0, clientsOnPage);
        } else {
            clientListPaginated = getClientDtoList(((page - 1) * clientsOnPage), clientsOnPage);
        }
        return clientListPaginated;
    }

    public List<ClientDTO> getClientDtoList(int offset, int limit) {
        return (entityService.entityList(Client.class, offset, limit))
                .stream()
                .map(this::getClientDTO)
                .collect(Collectors.toList());
    }

    public ClientDTO getClientDTO(Client client) {
        modelMapper
                .getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(client, ClientDTO.class);
    }
}
