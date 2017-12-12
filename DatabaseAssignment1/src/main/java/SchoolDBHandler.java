
import utils.ConnectionPool;
import utils.DBHandler;
import utils.FileLoader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author Hiki
 * @create 2017-11-06 10:12
 */
public class SchoolDBHandler {

	/**
	 * File locations.
	 */

	private final List<String[]> studentInfos;
	private final List<String[]> dormPhoneInfos;

	public SchoolDBHandler() {
		studentInfos = FileLoader.loadExcelFile("data/分配方案.xls", 7, 1);
		dormPhoneInfos = FileLoader.loadTxtFile("data/电话.txt", 1);
	}

	public void createTables(){
		createTableOfStudent();
		createTableOfDormitory();
	}

	public void insertAllData(){
		insertStudent();
		insertDorm();
		insertLocation();
	}

	public List<String> selectDepartment(){
		try {
			String sql1 = "SELECT * FROM `student` S WHERE S.sname='王小星';";
			ResultSet rs1 = DBHandler.select(sql1);
			rs1.next();
			String gender = rs1.getString("gender");
			String department = rs1.getString("department");
			String sql2 = "SELECT * FROM `location` L1 WHERE L1.dname IN (\n" +
						  "  SELECT L.dname FROM `location` L WHERE L.gender='" + gender + "' AND L.department='" + department + "'\n);";
			ResultSet rs2 = DBHandler.select(sql2);
			List<String> departments = new ArrayList<>();
			while (rs2.next())
				departments.add(rs2.getString("department"));
			return departments;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void updateFee(){
		String sql = "UPDATE `dorm` SET fee=1200 WHERE dname='陶园1舍';";
		DBHandler.update(sql);
	}

	public void exchangeDorm(){
		try {
			String sql1 = "SELECT * FROM `location` WHERE department='软件学院' AND gender='男'";
			ResultSet rs1 = DBHandler.select(sql1);
			rs1.next();
			String maleDormName = rs1.getString("dname");
			String sql2 = "SELECT * FROM `location` WHERE department='软件学院' AND gender='女'";
			ResultSet rs2 = DBHandler.select(sql2);
			rs2.next();
			String femaleDormName = rs2.getString("dname");
			String sql3 = "UPDATE `location` SET dname='" + maleDormName + "' WHERE department='软件学院' AND gender='女'";
			String sql4 = "UPDATE `location` SET dname='" + femaleDormName + "' WHERE department='软件学院' AND gender='男'";
			DBHandler.update(sql3);
			DBHandler.update(sql4);

		} catch (SQLException e) {
			e.printStackTrace();
		}


	}

	private void insertDorm(){
		// construct a list to save the dormitory information
		int size = 4;
		List<String[]> dormInfos = new ArrayList<>();
		Map<String, Integer> dormNames = new HashMap<>();
		for (int i = 0; i < dormPhoneInfos.size(); i++) {
			String[] item = new String[size];
			item[0] = dormPhoneInfos.get(i)[0];
			item[2] = dormPhoneInfos.get(i)[1];
			dormInfos.add(item);
			dormNames.put(item[0], i);
		}
		for (String[] studentInfo: studentInfos) {
			String dormName = studentInfo[5];
			if (dormNames.keySet().contains(dormName)) {
				dormInfos.get(dormNames.get(dormName))[1] = studentInfo[6];
				dormInfos.get(dormNames.get(dormName))[3] = studentInfo[4];
				dormNames.remove(dormName);
			}
		}

		String sql = "INSERT INTO `dorm` VALUES (?, ?, ?, ?)";
		Connection conn = DBHandler.getConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			conn.setAutoCommit(false);
			for (String[] dormInfo : dormInfos){
				ps.setString(1, dormInfo[0]);
				ps.setDouble(2, Double.parseDouble(dormInfo[1]));
				ps.setString(3, dormInfo[2]);
				ps.setString(4, dormInfo[3]);
				ps.execute();
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void insertStudent(){
		String sql = "INSERT INTO `student` VALUES (?, ?, ?, ?)";
		Connection conn = DBHandler.getConnection();
		PreparedStatement ps = null;
		int count = 0;
		int batchSize = 1000;
		try {
			ps = conn.prepareStatement(sql);
			conn.setAutoCommit(false);
			for (String[] studentInfo : studentInfos){
				ps.setString(1, studentInfo[1]);
				ps.setString(2, studentInfo[2]);
				ps.setString(3, studentInfo[3]);
				ps.setString(4, studentInfo[0]);
				ps.addBatch();
				if (++count % batchSize == 0)
					ps.executeBatch();
			}
			ps.executeBatch();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void insertLocation(){
		// construct a list to save the location information
		int size = 3;
		List<String[]> locationInfos = new ArrayList<>();
		for (String[] studentInfo : studentInfos){
			boolean exist = false;
			for (String[] locationInfo : locationInfos)
				if (locationInfo[0] == studentInfo[0] && locationInfo[1] == studentInfo[3])
					exist = true;
			if (!exist)
				locationInfos.add(new String[]{studentInfo[0], studentInfo[3], studentInfo[5]});
		}

		String sql = "INSERT INTO `location` VALUES (?, ?, ?)";
		Connection conn = DBHandler.getConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			conn.setAutoCommit(false);
			for (String[] locationInfo : locationInfos){
				ps.setString(1, locationInfo[0]);
				ps.setString(2, locationInfo[1]);
				ps.setString(3, locationInfo[2]);
				ps.execute();
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void createTableOfStudent(){
		String sql = "CREATE TABLE `student`(\n" +
					 "  `sid` CHAR(9) NOT NULL,\n" +
					 "  `sname` CHAR(30) NOT NULL,\n" +
					 "  `gender` CHAR(2) NOT NULL,\n" +
					 "  `department` CHAR(40) NOT NULL,\n" +
					 "  `dname` VARCHAR(20) NOT NULL,\n" +
					 "  PRIMARY KEY (sid)\n" +
					 ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
		DBHandler.createTable(sql);
	}

	private void createTableOfDormitory(){
		String sql = "CREATE TABLE `dorm` (\n" +
					 "  `dname` CHAR(20) NOT NULL,\n" +
					 "  `fee` DOUBLE NOT NULL,\n" +
					 "  `phone` CHAR(20) NOT NULL,\n" +
					 "  `campus` CHAR(20) NOT NULL,\n" +
					 "  PRIMARY KEY (dname)\n" +
					 ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
		DBHandler.createTable(sql);
	}


	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		SchoolDBHandler handler = new SchoolDBHandler();
		handler.exchangeDorm();
		long end = System.currentTimeMillis();
		System.out.println("程序运行时间： "+(end-start)+"ms");
	}
}

