import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hiki on 2017/6/3.
 */
public class DataProvider {

    // Basic Database Info
    private final static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private final static String DB_URL = "jdbc:mysql://123.206.187.246:3306/Quantour?createDatabaseIfNotExist=true&characterEncoding=utf-8&useUnicode=true";
    private final static String USER = "root";
    private final static String PWD = "Leftovers_4";
    private Connection conn;

    // Data
    private List<Double> historyDatas;

    public DataProvider() {
        try {
            connect();
            System.out.println("Connect to database successfully...");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        historyDatas = new ArrayList<>();
    }

    // 新建连接
    private void connect() throws ClassNotFoundException, SQLException {
        if (conn == null) {
            // 注册JDBC Driver
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PWD);
        }

    }

    private void disConnect(){
        if (conn != null)
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

    }

    // 获取数据
    public void loadData(String symbol) {

        // 清除已有数据
        historyDatas.clear();

        try {
            Statement statement = conn.createStatement();
            ResultSet res = statement.executeQuery("SELECT\n" +
                    "*\n" +
                    "FROM\n" +
                    "stock_1d_data\n" +
                    "WHERE\n" +
                    "stock_1d_data.`CODE` = '" + symbol + "' AND\n" +
                    "date(DateTime) >= '1995-01-01'\n" +
                    "ORDER BY\n" +
                    "stock_1d_data.DateTime ASC");

            while (res.next()) {
                Double close = res.getDouble("CLOSE");
//                LocalDate date = res.getDate("DateTime").toLocalDate();
//                System.out.println(date.toString() + " " + close);
                historyDatas.add(close);
            }

            System.out.println("Load data successfully...");
//            for (Double each : historyDatas) {
//                System.out.println(each);
//            }
            disConnect();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



    public static void main(String[] args) {
        DataProvider dp = new DataProvider();
        dp.loadData("000001.SZ");
        for (Double each : dp.historyDatas) {
            System.out.println(each);
        }
    }


    public List<Double> getHistoryDatas() {
        return historyDatas;
    }
}
