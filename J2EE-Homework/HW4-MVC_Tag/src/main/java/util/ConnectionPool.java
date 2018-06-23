package util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author hiki on 2017-12-18
 */

class ConnectionPool {

    private final static HikariDataSource dataSource;

    static {

        HikariConfig config = new HikariConfig(ConnectionPool.class.getClassLoader().getResource("datasource.properties").getPath());
        dataSource = new HikariDataSource(config);
        // if can not load property file, use below instead
//        dataSource = new HikariDataSource();
//        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/j2ee?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&characterEncoding=UTF-8");
//        dataSource.setUsername("root");
//        dataSource.setPassword("aneureka");
//        dataSource.setConnectionTimeout(100*1000);
//        dataSource.setAllowPoolSuspension(true);
//        dataSource.setMaximumPoolSize(30);
    }

    // Not allow to create an instance of ConnectionPool
    private ConnectionPool(){
    }

    public static Connection getConnection() throws SQLException{
        return dataSource.getConnection();
    }
}
