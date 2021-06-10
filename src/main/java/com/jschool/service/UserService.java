package com.jschool.service;

import com.jschool.DTO.ClientDTO;
import com.jschool.domain.Client;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Value("${orders.list.quantity}")
    private int clientsOnPage;

    private EntityService entityService;
    private ModelMapper modelMapper;

    @Autowired
    public UserService(EntityService entityService, ModelMapper modelMapper) {
        this.entityService = entityService;
        this.modelMapper = modelMapper;
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

    public List<ClientDTO> getClientDtoList() {
        return (entityService.entityList(Client.class))
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
