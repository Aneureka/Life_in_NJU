package cn.edu.nju.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * @author hiki on 2018-01-12
 */

public class HibernateUtil {

    private static final SessionFactory SESSION_FACTORY = new Configuration().configure().buildSessionFactory();

    public static Session getSession() {
        return SESSION_FACTORY.getCurrentSession();
    }

    public static void closeSession(Session session) {
        session.close();
    }

}
