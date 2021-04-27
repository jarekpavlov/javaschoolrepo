package com.jschool.service;

import com.jschool.DAO.EntityDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import java.util.List;

@Service
public class EntityService {

    private EntityDaoImpl entityDaoImpl;

    @Autowired//This annotation is not necessary
    public  EntityService(EntityDaoImpl entityDaoImpl){
        this.entityDaoImpl=entityDaoImpl;
    }
    public <T> T saveEntity(T entity){
        entityDaoImpl.openCurrentSessionWithTransaction();
        entityDaoImpl.saveEntity(entity);
        entityDaoImpl.closeCurrentSessionWithTransaction();
        return entity;
    }
    public<T> T updateEntity(T entity) {
        entityDaoImpl.openCurrentSessionWithTransaction();
        entityDaoImpl.updateEntity(entity);
        entityDaoImpl.closeCurrentSessionWithTransaction();
        return entity;
    }
    public <T> T getEntity( Class<T> type,Long id) {
        entityDaoImpl.openCurrentSession();
        T entity = entityDaoImpl.getEntity(type, id);
        entityDaoImpl.closeCurrentSession();
        return entity;
    }
    public <T> T deleteEntity(Class<T> type, Long id) {
        entityDaoImpl.openCurrentSessionWithTransaction();
        T reference = entityDaoImpl.deleteEntity(type,id);
        entityDaoImpl.closeCurrentSessionWithTransaction();
        return reference;
    }
    public <T> List<T> entityList(Class<T> type) {
        entityDaoImpl.openCurrentSession();
        List<T> entityList = entityDaoImpl.entityList(type);
        entityDaoImpl.closeCurrentSession();
        return  entityList;
    }

}
