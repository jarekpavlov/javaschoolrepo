package com.jschool.service;

import com.jschool.DAO.EntityDao;
import com.jschool.DTO.ClientDTO;
import com.jschool.count.JoinCountByClient;
import com.jschool.count.JoinCountByProduct;
import com.jschool.count.JoinCountSum;
import com.jschool.domain.Client;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
@Transactional
public class EntityService {

    private EntityDao entityDaoImpl;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public EntityService(EntityDao entityDaoImpl) {
        this.entityDaoImpl = entityDaoImpl;
    }

    public <T> T saveEntity(T entity) {
        entityDaoImpl.openCurrentSession();
        entityDaoImpl.saveEntity(entity);
        return entity;
    }

    public <T> T updateEntity(T entity) {
        entityDaoImpl.openCurrentSession();
        entityDaoImpl.updateEntity(entity);
        return entity;
    }

    public <T> T getEntity(Class<T> type, Long id) {
        entityDaoImpl.openCurrentSession();
        return entityDaoImpl.getEntity(type, id);
    }

    public <T> T deleteEntity(Class<T> type, Long id) {
        entityDaoImpl.openCurrentSession();
        return entityDaoImpl.deleteEntity(type, id);
    }

    public <T> List<T> entityList(Class<T> type) {
        entityDaoImpl.openCurrentSession();
        return entityDaoImpl.entityList(type);
    }
    public <T> List<T> entityList(Class<T> type, int offset, int limit) {
        entityDaoImpl.openCurrentSession();
        return entityDaoImpl.entityList(type,offset,limit);
    }

    public <T> T getEntityByEmail(Class<T> type, String email) {
        T someEntity;
        entityDaoImpl.openCurrentSession();
        List<T> entities = entityDaoImpl.getEntityByEmail(type, email);
        for (T entity : entities) {
            someEntity = entity;
            return someEntity;
        }
        return null;
    }

    public Set<JoinCountByClient> getBestClient(int daysAgo) {
        entityDaoImpl.openCurrentSession();
        List<?> bestClient = entityDaoImpl.getBestClient(daysAgo);
        Set<JoinCountByClient> joinCountByClients = new TreeSet<>();
        for (int i = 0; i < bestClient.size(); i++) {
            JoinCountByClient joinCountByClient = new JoinCountByClient();
            Object[] result = (Object[]) bestClient.get(i);
            joinCountByClient.setResultAmount((Double) result[0]);
            joinCountByClient.setClient_id((Long) result[1]);
            joinCountByClients.add(joinCountByClient);
        }
        return joinCountByClients;
    }

    public Set<JoinCountByProduct> getBestProduct(int daysAgo) {
        entityDaoImpl.openCurrentSession();
        List<?> bestProduct = entityDaoImpl.getBestProduct(daysAgo);
        Set<JoinCountByProduct> joinCountByProductSet = new TreeSet<>();
        for (int i = 0; i < bestProduct.size(); i++) {
            JoinCountByProduct joinCountByProduct = new JoinCountByProduct();
            Object[] result = (Object[]) bestProduct.get(i);
            joinCountByProduct.setResultAmount((Double) result[0]);
            joinCountByProduct.setProduct_id((Long) result[1]);
            joinCountByProduct.setBrand(result[2].toString());
            joinCountByProduct.setQuantity((Long)result[3]);
            joinCountByProductSet.add(joinCountByProduct);
        }
        return joinCountByProductSet;

    }

    public JoinCountSum getSum(int daysAgo) {
        entityDaoImpl.openCurrentSession();
        Object object = entityDaoImpl.getSum(daysAgo);
        JoinCountSum joinCountSum = new JoinCountSum();
        joinCountSum.setResultSum((Double) object);

        return joinCountSum;
    }

    public <T> T getEntityByActivationCode( Class<T> type, String code) {
        entityDaoImpl.openCurrentSession();
        List<T> entityByActivationCode = entityDaoImpl.getEntityByActivationCode(type, code);
        for (T entity : entityByActivationCode) {
            return entity;
        }
        return null;
    }
}
