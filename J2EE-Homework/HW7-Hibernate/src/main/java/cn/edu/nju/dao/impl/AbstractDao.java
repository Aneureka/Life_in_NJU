package cn.edu.nju.dao.impl;

import cn.edu.nju.util.HibernateUtil;
import org.hibernate.Session;

import javax.persistence.RollbackException;
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

    AbstractDao(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T findOne(int id) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        T res = session.get(clazz, id);
        session.getTransaction().commit();
        session.close();
        return res;
    }

    public List<T> findAll() {
        return findByQuery("from " + clazz.getName());
    }

    public List<T> findByQuery(String query) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        List<T> res = session.createQuery(query, clazz).getResultList();
        session.getTransaction().commit();
        session.close();
        return res;
    }

    public List<T> findByNativeQuery(String query) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        List res = session.createNativeQuery(query, clazz).getResultList();
        session.getTransaction().commit();
        session.close();
        return convertToGenericList(res);
    }

    public T findOneByQuery(String query) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        T res = session.createQuery(query, clazz).getSingleResult();
        session.getTransaction().commit();
        session.close();
        return res;
    }

    public int findCountByQuery(String query) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        int res = session.createQuery(query, Long.class).getSingleResult().intValue();
        session.getTransaction().commit();
        session.close();
        return res;
    }


    public boolean create(T entity) {
        if (entity == null)
            return true;
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
            session.close();
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
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            for (T t : entities) {
                session.save(t);
            }
            session.getTransaction().commit();
            session.close();
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
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
            session.close();
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
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
            session.close();
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
