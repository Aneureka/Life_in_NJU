package utils;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * A connection pool which provides connection.
 * @author Hiki
 * @create 2017-11-05 17:44
 */
public class ConnectionPool {

	private static HikariDataSource dataSource;

	static {
		dataSource = new HikariDataSource();
		dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/school?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&characterEncoding=UTF-8");
		dataSource.setUsername("root");
		dataSource.setPassword("aneureka");
		dataSource.setConnectionTimeout(100*1000);
		dataSource.setAllowPoolSuspension(true);
		dataSource.setMaximumPoolSize(30);
	}

	// Not allow to create an instance of utils.ConnectionPool
	private ConnectionPool(){}

	public static Connection getConnection(){
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			System.err.println("error while getting connection because of: " + e);
		}
		System.exit(-1);
		return null;
	}

}
