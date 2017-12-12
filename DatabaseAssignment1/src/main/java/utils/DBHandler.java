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

	public static void createTable(String sql){
		Connection conn = ConnectionPool.getConnection();
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			System.out.println(sql);
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

	public static boolean insertBatch(PreparedStatement ps, List<String[]> items, int[] indices){
		Connection conn = ConnectionPool.getConnection();
		int itemSize = indices.length;
		int count = 0;
		try {
			conn.setAutoCommit(false);
			for (String[] item : items){
				for (int i = 0; i < itemSize; i++)
					ps.setObject(i+1, item[indices[i]]);
				ps.addBatch();
				if (++count % BATCH_SIZE == 0)
					ps.executeBatch();
			}
			ps.executeBatch();
			conn.commit();
			return true;
		} catch (SQLException e) {
			System.err.println("error while inserting because of: " + e);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				System.err.println("error while rolling back because of: " + e);
			}
		} finally {
			closeStatement(ps);
			closeConnection(conn);
		}
		return false;
	}

	public static boolean insert(PreparedStatement ps, List<String[]> items, int[] indices){
		Connection conn = ConnectionPool.getConnection();
		int itemSize = indices.length;
		try {
			conn.setAutoCommit(false);
			for (String[] item : items){
				for (int i = 0; i < itemSize; i++)
					ps.setObject(i+1, item[indices[i]]);
				ps.execute();
			}
			conn.commit();
			return true;
		} catch (SQLException e) {
			System.err.println("error while inserting because of: " + e);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				System.err.println("error while rolling back because of: " + e);
			}
		} finally {
			closeStatement(ps);
			closeConnection(conn);
		}
		return false;
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
