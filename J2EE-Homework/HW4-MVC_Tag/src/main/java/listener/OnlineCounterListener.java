package listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author hiki on 2017-12-21
 */

@WebListener
public class OnlineCounterListener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener{

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        context.setAttribute("userCount", 0);
        context.setAttribute("loginUserCount", 0);
        System.out.println("Application initialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Application shut down");
    }
    
    
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        ServletContext context = se.getSession().getServletContext();
        synchronized (context) {
            int userCount = (int) context.getAttribute("userCount");
            context.setAttribute("userCount", userCount+1);
        }
        System.out.println("Session created, userCount++");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        ServletContext context = se.getSession().getServletContext();
        synchronized (context) {
            int userCount = (int) context.getAttribute("userCount");
            context.setAttribute("userCount", userCount-1);
        }
        System.out.println("Session destroyed, userCount--");
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        ServletContext context = event.getSession().getServletContext();
        synchronized (context) {
            if ("login".equals(event.getName())) {
                int loginUserCount = (int) context.getAttribute("loginUserCount");
                context.setAttribute("loginUserCount", loginUserCount+1);
                System.out.println("Session Attribute [login] added, loginUserCount++");
            }
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        ServletContext context = event.getSession().getServletContext();
        synchronized (context) {
            if ("login".equals(event.getName())) {
                int loginUserCount = (int) context.getAttribute("loginUserCount");
                context.setAttribute("loginUserCount", loginUserCount-1);
                System.out.println("Session Attribute [login] removed, loginUserCount--");
            }
        }
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        System.out.println("Session Attribute replaced");
    }


    
}
