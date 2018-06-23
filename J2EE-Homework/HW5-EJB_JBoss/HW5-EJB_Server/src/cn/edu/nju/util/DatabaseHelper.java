package cn.edu.nju.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author hiki on 2017-12-18
 */

public class DatabaseHelper {

    // get connection from connection pool
    public static Connection getConnection(){
        try {
            return ConnectionPool.getConnection();
        } catch (SQLException e) {
            System.err.println("error while getting connection because of: " + e);
            System.exit(-1);
        }

        return null;
    }

    // close connection in a safe way
    public static void closeConnection(Connection conn){
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("error while closing connection because of: " + e.getMessage());
            }
        }
    }

    // close statement in a safe way
    public static void closeStatement(Statement stmt){
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                System.err.println("error while closing statement because of: " + e.getMessage());
            }
        }
    }
}
