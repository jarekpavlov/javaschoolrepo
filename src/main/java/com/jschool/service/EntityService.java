package com.jschool.service;

import com.jschool.DAO.EntityDaoImpl;
import com.jschool.domain.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class EntityService {

    private EntityDaoImpl entityDaoImpl;

    @Autowired//This annotation is not necessary
    public  EntityService(EntityDaoImpl entityDaoImpl){
        this.entityDaoImpl=entityDaoImpl;
    }
    public <T> T saveEntity(T entity){
        entityDaoImpl.openCurrentSession();
        entityDaoImpl.saveEntity(entity);
        return entity;
    }
    public<T> T updateEntity(T entity) {
        entityDaoImpl.openCurrentSession();
        entityDaoImpl.updateEntity(entity);
        return entity;
    }
    public <T> T getEntity( Class<T> type,Long id) {
        entityDaoImpl.openCurrentSession();
        T entity = entityDaoImpl.getEntity(type, id);
        return entity;
    }
    public <T> T deleteEntity(Class<T> type, Long id) {
        entityDaoImpl.openCurrentSession();
        T reference = entityDaoImpl.deleteEntity(type,id);
        return reference;
    }
    public <T> List<T> entityList(Class<T> type) {
        entityDaoImpl.openCurrentSession();
        List<T> entityList = entityDaoImpl.entityList(type);
        return  entityList;
    }
    public <T> T getEntityByEmail( Class<T> type,String email) {
        T someEntity;
        entityDaoImpl.openCurrentSession();
        List<T> entities= entityDaoImpl.getEntityByEmail(type, email);
        for (T entity:entities){
            someEntity = entity;
            return someEntity;
        }
        return null;
    }

}
