package com.jschool.service;

import com.jschool.domain.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    EntityService entityService;
    @Autowired
    public UserDetailServiceImpl (EntityService entityService){
        this.entityService = entityService;
    }
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        entityService.getEntityByEmail(Client.class,s);
        return null;
    }
}
