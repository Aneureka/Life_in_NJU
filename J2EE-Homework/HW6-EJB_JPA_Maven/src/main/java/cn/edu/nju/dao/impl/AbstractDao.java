package cn.edu.nju.dao.impl;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hiki on 2018-01-11
 */


public class AbstractDao<T> {

    /**
     * Use for data transaction of t
     */
    private Class<T> clazz;

    @PersistenceUnit(name = "j2ee")
    private EntityManagerFactory factory;

    @PersistenceContext
    private EntityManager em;

    AbstractDao(Class<T> clazz) {
        this.clazz = clazz;
        factory = Persistence.createEntityManagerFactory("j2ee");
        em = factory.createEntityManager();
    }

    public T findOne(int id) {
        return em.find(clazz, id);
    }

    public List<T> findAll() {
        return findByQuery("from " + clazz.getName() + " tmp");
    }

    public List<T> findByQuery(String query) {
        List<T> res = em.createQuery(query, clazz).getResultList();
        em.clear();
        return res;
    }

    public List<T> findByNativeQuery(String query) {
        List res = em.createNativeQuery(query, clazz).getResultList();
        return convertToGenericList(res);
    }

    public T findOneByQuery(String query) {
        return em.createQuery(query, clazz).getSingleResult();
    }

    public int findCountByQuery(String query) {
        return em.createQuery(query, Long.class).getSingleResult().intValue();
    }


    public boolean create(T entity) {
        if (entity == null)
            return true;
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } catch (RollbackException e) {
            System.err.println("Failed to create " + entity.getClass().getName() + " because of: " + e);
            return false;
        }
        return true;
    }

    public boolean createBatch(List<T> entities) {
        if (entities == null || entities.isEmpty()) {
            return true;
        }

        try {
            em.getTransaction().begin();
            for (T t : entities) {
                em.persist(t);
            }
            em.getTransaction().commit();
        } catch (RollbackException e) {
            System.err.println("Failed to create batch of " + entities.get(0).getClass().getName() + " because of: " + e);
            return false;
        }
        return true;
    }

    public boolean update(T entity) {
        if (entity == null) {
            return false;
        }
        try {
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
        } catch (RollbackException e) {
            System.err.println("Failed to update " + entity.getClass().getName() + " because of: " + e);
            return false;
        }
        return true;
    }

    private boolean remove(T entity) {
        if (entity == null) {
            return true;
        }
        try {
            em.getTransaction().begin();
            em.remove(entity);
            em.getTransaction().commit();
        } catch (RollbackException e) {
            System.err.println("Failed to remove " + entity.getClass().getName() + " because of: " + e);
            return false;
        }
        return true;
    }

    public boolean removeById(int id) {
        T entity = findOne(id);
        return remove(entity);
    }

    private List<T> convertToGenericList(List from) {
        List<T> to = new ArrayList<>(from.size());
            for (Object o : from) {
                to.add((T) o);
            }
        return to;
    }

}
