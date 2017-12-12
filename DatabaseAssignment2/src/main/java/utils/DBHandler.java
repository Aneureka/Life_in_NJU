package utils;

import java.sql.*;
import java.util.List;

/**
 * @author Hiki
 * @create 2017-11-06 10:17
 */
public class DBHandler {

	private final static int BATCH_SIZE = 100;

	public static Connection getConnection(){
		return ConnectionPool.getConnection();
	}

	public static void execute(String sql){
		Connection conn = ConnectionPool.getConnection();
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			closeStatement(stmt);
			closeConnection(conn);
		}
	}

	public static ResultSet select(String sql){
		Connection conn = ConnectionPool.getConnection();
		try {
			Statement stmt = conn.createStatement();
			return stmt.executeQuery(sql);
		} catch (SQLException e) {
			System.err.println("error while querying because of: " + e);
		}
		return null;
	}

	public static void update(String sql){
		Connection conn = ConnectionPool.getConnection();
		try {
			Statement stmt = conn.createStatement();
			int a = stmt.executeUpdate(sql);
			System.out.println(a);
		} catch (SQLException e) {
			System.err.println("error while updating because of: " + e);
		}
	}


	private static void closeConnection(Connection conn){
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				System.err.println("error while closing connection because of: " + e.getMessage());
			}
		}
	}

	private static void closeStatement(Statement stmt){
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				System.err.println("error while closing statement because of: " + e.getMessage());
			}
		}
	}
}
