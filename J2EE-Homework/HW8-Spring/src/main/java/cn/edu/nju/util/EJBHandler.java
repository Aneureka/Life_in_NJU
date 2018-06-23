package cn.edu.nju.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

/**
 * @author hiki on 2018-01-04
 */

public class EJBHandler {

    public static Object getEJB(String jndipath){
        try{
            Properties jndiProps = new Properties();
            jndiProps.put(Context.URL_PKG_PREFIXES,"org.jboss.ejb.client.naming");
            final Context context = new InitialContext(jndiProps);
            return context.lookup(jndipath);
        }catch (NamingException e){
            System.err.println("error while getting ejb of " + jndipath + " because of " + e);
        }
        return null;
    }

}
