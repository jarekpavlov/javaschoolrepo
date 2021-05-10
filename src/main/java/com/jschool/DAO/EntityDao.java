package com.jschool.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Repository
public class EntityDao {

    private SessionFactory factory;
    private Session currentSession;

    public EntityDao(SessionFactory factory) {
        this.factory = factory;
    }

    public <T> T saveEntity(T entity) {
        currentSession.saveOrUpdate(entity);
        return entity;
    }

    public <T> T getEntity(Class<T> type, Long id) {
        T entity = currentSession.get(type, id);
        return entity;
    }

    public <T> T updateEntity(T entity) {
        currentSession.saveOrUpdate(entity);
        return entity;
    }

    public <T> T deleteEntity(Class<T> type, Long id) {
        T reference = getEntity(type, id);
        currentSession.delete(reference);
        return reference;
    }

    public <T> List<T> entityList(Class<T> type) {
        Query query = currentSession.createQuery("select c from " + type.getName() + " c");
        return (List<T>) query.getResultList();
    }

    public <T> List<T> getEntityByEmail(Class<T> type, String email) {
        Query query = currentSession.createQuery("select c from " + type.getName() + " c where c.email='" + email + "'");
        return (List<T>) query.getResultList();
    }

    public List<?> getBestClient(int daysAgo) {
        String dateBefore_daysS = getDateBefore(daysAgo);
        Query query = currentSession.createQuery("select sum(m.product.price*m.quantity) as resultAmount, m.order.client.id as" +
                "  client_id from products_in_order m inner join m.order inner join m.product inner join m.order.client" +
                " where m.order.dateOfOrder > '" + dateBefore_daysS + "'" +
                " and m.order.orderStatus = '3' group by m.order.client.id order by sum(m.product.price*m.quantity)");
        query.setMaxResults(10);
        return query.getResultList();
    }

    public List<?> getBestProduct(int daysAgo) {
        String dateBefore_daysS = getDateBefore(daysAgo);
        Query query = currentSession.createQuery("select sum(m.product.price*m.quantity), m.product.id, m.quantity" +
                " from products_in_order m inner join m.order inner join m.product inner join m.order.client" +
                " where m.order.dateOfOrder > '" + dateBefore_daysS + "'" +
                "and m.order.orderStatus = '3' group by m.product.id order by sum(m.product.price*m.quantity)");
        query.setMaxResults(10);
        return query.getResultList();
    }

    public Object getSum(int daysAgo) {
        String dateBefore_daysS = getDateBefore(daysAgo);
        Query query = currentSession.createQuery("select sum(m.product.price*m.quantity)" +
                " from products_in_order m inner join m.order inner join m.product inner join m.order.client" +
                " where m.order.dateOfOrder > '" + dateBefore_daysS + "'" + "and m.order.orderStatus = '3'");
        return query.getSingleResult();
    }

    private String getDateBefore(int daysAgo) {
        Instant now = Instant.now();
        Instant before = now.minus(Duration.ofDays(daysAgo));
        Date dateBefore_days = Date.from(before);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return dateFormat.format(dateBefore_days);
    }

    public Session openCurrentSession() {
        currentSession = factory.getCurrentSession();
        return currentSession;
    }

}
