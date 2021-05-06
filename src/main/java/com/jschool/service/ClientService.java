package com.jschool.service;

import com.jschool.DTO.ClientDTO;
import com.jschool.domain.Address;
import com.jschool.domain.Client;
import com.jschool.security.Authority;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private ModelMapper modelMapper;
    private EntityService entityService;

    public ClientService(ModelMapper modelMapper, EntityService entityService) {
        this.entityService = entityService;
        this.modelMapper = modelMapper;
    }

    public void saveClient(Client client, Client clientWithPassword) {

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

    public ClientDTO getClientDTO(Client client) {

        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(client, ClientDTO.class);
    }

    public List<ClientDTO> getClientDtoList() {
        return entityService.entityList(Client.class)
                .stream()
                .map(this::getClientDTO)
                .collect(Collectors.toList());
    }
}
