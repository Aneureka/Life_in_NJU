package cn.edu.nju.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author hiki on 2017-12-18
 */

class ConnectionPool {

    private DataSource dataSource;

    private static ConnectionPool pool = new ConnectionPool();

    // Not allow to create an instance of ConnectionPool
    private ConnectionPool() {
        this.dataSource = new ComboPooledDataSource("hiki");
    }

    public static Connection getConnection() throws SQLException{
        return pool.dataSource.getConnection();
    }
}
