package com.jschool.service;

import com.jschool.DTO.ClientDTO;
import com.jschool.domain.Client;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Value("${orders.list.quantity}")
    private int clientsOnPage;

    private EntityService entityService;
    private ProductService productService;
    private ModelMapper modelMapper;

    @Autowired
    public UserService(EntityService entityService, ProductService productService, ModelMapper modelMapper) {
        this.entityService = entityService;
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    public ModelMap getUsersPaginated(ModelMap map, Integer page) {
        List<ClientDTO> clientList = getClientDtoList();
        List<ClientDTO> clientListPaginated;

        if (page == null) {
            clientListPaginated = getClientDtoList(0, clientsOnPage);
        } else {
            clientListPaginated = getClientDtoList(((page - 1) * clientsOnPage), clientsOnPage);
        }
        map.addAttribute("userList", clientListPaginated);
        productService.getPageQuantityModelMap(clientList, map, clientsOnPage);
        return map;
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
