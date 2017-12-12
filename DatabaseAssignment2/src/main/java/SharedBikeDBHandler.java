
import entity.Record;
import entity.User;
import entity.Location;
import utils.DBHandler;

import java.sql.*;
import java.util.*;

/**
 * @author Hiki
 * @create 2017-11-06 10:12
 */
public class SharedBikeDBHandler {

	private final Map<Integer, User> userMap;

	private final List<Record> records;

	private final List<Integer> bikeIds;

	public SharedBikeDBHandler() {
		userMap = FileLoader.loadUserInMap();
		records = FileLoader.loadRecord(userMap);
		bikeIds = FileLoader.loadBikeId();
	}

	public void createTables(){
		String userSql = "CREATE TABLE `user`(\n" +
				"  `uid` INT(11) NOT NULL,\n" +
				"  `name` CHAR(30) NOT NULL,\n" +
				"  `phone` CHAR(20) NOT NULL,\n" +
				"  `balance` DOUBLE NOT NULL,\n" +
				"  `address` VARCHAR(50) DEFAULT NULL,\n" +
				"  PRIMARY KEY (uid)\n" +
				") ENGINE=InnoDB DEFAULT CHARSET=utf8;";

		String bikeSql = "CREATE TABLE `bike`(\n" +
				"  `bid` INT(11) NOT NULL,\n" +
				"  PRIMARY KEY (bid)\n" +
				") ENGINE=InnoDB DEFAULT CHARSET=utf8;";

		String recordSql = "CREATE TABLE `record`(\n" +
				"  `uid` INT(11) NOT NULL,\n" +
				"  `bid` INT(11) NOT NULL,\n" +
				"  `start_point` CHAR(40) NOT NULL,\n" +
				"  `start_time` DATETIME NOT NULL,\n" +
				"  `end_point` CHAR(40) NOT NULL,\n" +
				"  `end_time` DATETIME NOT NULL,\n" +
				"  `cost` DOUBLE NOT NULL,\n" +
				"  PRIMARY KEY (uid, bid, start_time)\n" +
				") ENGINE=InnoDB DEFAULT CHARSET=utf8;";


		DBHandler.execute(userSql);
		DBHandler.execute(bikeSql);
		DBHandler.execute(recordSql);

	}

	public void insertAllData(){
		insertBike();
		insertUser();
		insertRecord();
	}

	public void addAddress(){
		// Create a temporal table to save the [uid]-[location] relationship.
		String cttSql = "CREATE TABLE user_location (\n" +
				"  `uid` INT(11) NOT NULL,\n" +
				"  `location` VARCHAR(50) NOT NULL,\n" +
				"  `frequent` INT(11) NOT NULL,\n" +
				"  PRIMARY KEY (uid)\n" +
				") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
		DBHandler.execute(cttSql);
//		 Select into the temporal table
		String querySql = "SELECT R.uid AS uid, R.end_point AS location, count(R.end_point) AS frequency FROM `record` R\n" +
				"  WHERE time(R.end_time) BETWEEN '18:00' AND '24:00'\n" +
				"  GROUP BY uid, location;";
		ResultSet rs = DBHandler.select(querySql);
		// Begin saving the temporal location data
		Map<Integer, Location> ulMap = new HashMap<>();
		try {
			while (rs.next()){
				int uid = rs.getInt("uid");
				String location = rs.getString("location");
				int frequency = rs.getInt("frequency");
				if (ulMap.containsKey(uid)){
					if (ulMap.get(uid).getFrequency() < frequency)
						ulMap.put(uid, new Location(location, frequency));
				} else {
					ulMap.put(uid, new Location(location, frequency));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Iterate
		Iterator<Map.Entry<Integer, Location>> itr = ulMap.entrySet().iterator();
		while (itr.hasNext()){
			Map.Entry<Integer, Location> entry = itr.next();
			int uid = entry.getKey();
			String location = entry.getValue().getLocation();
			String locationSql = "UPDATE `user` SET address=" + "'" + location + "' WHERE uid=" + String.valueOf(uid);
//			System.out.println(locationSql);
			DBHandler.update(locationSql);
		}

	}


	public void saveBikeUsed(){
		String createTableSql = "CREATE TABLE `bike_use` (\n" +
				"  `bid` INT(11) NOT NULL,\n" +
				"  `end_point` INT(11) NOT NULL,\n" +
				"  PRIMARY KEY (bid)\n" +
				") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
		DBHandler.execute(createTableSql);
		String insertSql = "INSERT INTO `bike_use`(\n" +
				"SELECT R1.bid, R1.end_point FROM `record` R1 WHERE EXISTS\n" +
				"  (SELECT * FROM\n" +
				"      (SELECT R2.bid, count(TIMESTAMPDIFF(HOUR, R2.start_time, R2.end_time)) AS use_time FROM `record` R2\n" +
				"      WHERE DATEDIFF(R2.end_time, CURTIME()) <= 30\n" +
				"      GROUP BY R2.bid\n" +
				"      HAVING use_time > 200) u WHERE u.bid=R1.bid) AND NOT EXISTS(\n" +
				"    SELECT R3.bid, R3.end_time FROM `record` R3 WHERE R3.bid=R1.bid AND R3.end_time>R1.end_time)\n" +
				");";
		DBHandler.execute(insertSql);
	}

	private void insertBike(){
		String sql = "INSERT INTO `bike` VALUES (?)";
		Connection conn = DBHandler.getConnection();
		PreparedStatement ps = null;
		int count = 0;
		int batchSize = 1000;
		try {
			ps = conn.prepareStatement(sql);
			conn.setAutoCommit(false);
			for (int bikeId : bikeIds){
				ps.setInt(1, bikeId);
				ps.addBatch();
				if (++count % batchSize == 0){
					ps.executeBatch();
				}
			}
			ps.executeBatch();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void insertRecord(){
		Connection conn = DBHandler.getConnection();
		Statement ps = null;
		int count = 0;
		int batchSize = 5000;
		try {
			ps = conn.createStatement();
			conn.setAutoCommit(false);
			List<String> values = new ArrayList<>();
			for (Record record : records){
				StringBuilder valueBuilder = new StringBuilder("(");
				valueBuilder
						.append(record.getUid()).append(", ")
						.append(record.getBid()).append(", ")
						.append("\"").append(record.getStartPoint()).append("\", ")
						.append("\"").append(record.getStartTime()).append("\", ")
						.append("\"").append(record.getEndPoint()).append("\", ")
						.append("\"").append(record.getEndTime()).append("\", ")
						.append(record.getCost()).append(")");
				values.add(valueBuilder.toString());
				if (++count % batchSize == 0){
					ps.execute(buildMultiInsertSql("INSERT INTO `record` VALUES ", values));
					values.clear();
				}
			}
			if(!values.isEmpty())
				ps.execute(buildMultiInsertSql("INSERT INTO `record` VALUES ", values));
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void insertUser(){
		Connection conn = DBHandler.getConnection();
		Statement ps = null;
		int count = 0;
		int batchSize = 5000;
		try {
			ps = conn.createStatement();
			conn.setAutoCommit(false);
			List<String> values = new ArrayList<>();
			for (User user : userMap.values()){
				StringBuilder valueBuilder = new StringBuilder("(");
				valueBuilder
						.append(user.getUid()).append(", ")
						.append("\"").append(user.getName()).append("\", ")
						.append("\"").append(user.getPhone()).append("\", ")
						.append(user.getBalance()).append(")");
				values.add(valueBuilder.toString());
				if (++count % batchSize == 0){
//					System.out.println(buildMultiInsertSql("INSERT INTO `user`(uid, name, phone, balance) VALUES ", values));
					ps.execute(buildMultiInsertSql("INSERT INTO `user`(uid, name, phone, balance) VALUES ", values));
					values.clear();
				}
			}
			if(!values.isEmpty())
				ps.execute(buildMultiInsertSql("INSERT INTO `user`(uid, name, phone, balance) VALUES ", values));
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static String buildMultiInsertSql(String head, List<String> values){
		String tail = String.join(", ", values);
		StringBuilder builder = new StringBuilder(head);
		builder.append(tail).append(";");
		return builder.toString();
	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		SharedBikeDBHandler handler = new SharedBikeDBHandler();
		handler.saveBikeUsed();
		long end = System.currentTimeMillis();
		System.out.println("程序运行时间："+(end-start)+"ms");


//		long start = System.currentTimeMillis();
//		SharedBikeDBHandler handler = new SharedBikeDBHandler();
//		long end = System.currentTimeMillis();
//		System.out.println("Time for reading file and modifying data："+(end-start)+"ms");
//		long start1 = System.currentTimeMillis();
//		handler.insertUser();
//		long end1 = System.currentTimeMillis();
//		System.out.println("Time for inserting user data "+(end1-start1)+"ms");
//		long start2 = System.currentTimeMillis();
//		handler.insertBike();
//		long end2 = System.currentTimeMillis();
//		System.out.println("Time for inserting bike data："+(end2-start2)+"ms");
//		long start3 = System.currentTimeMillis();
//		handler.insertRecord();
//		long end3 = System.currentTimeMillis();
//		System.out.println("Time for inserting record data: "+(end3-start3)+"ms");
	}
}

