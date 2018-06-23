package cn.edu.nju.dao.impl;

import cn.edu.nju.dao.OrderDao;
import cn.edu.nju.model.Order;
import cn.edu.nju.util.DatabaseHelper;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * @author hiki on 2017-12-19
 */

public class OrderDaoImpl implements OrderDao {

    private static class holder {
        private static final OrderDaoImpl INSTANCE = new OrderDaoImpl();
    }

    private OrderDaoImpl() {}

    public static final OrderDaoImpl getInstance() {
        return holder.INSTANCE;
    }


    /*========================================== method ===============================================*/

    @Override
    public List<Order> findAll() {

        Connection connection = DatabaseHelper.getConnection();
        String sql = "SELECT * FROM orders";
        Statement stmt = null;

        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            return getFromResultSet(rs);
        } catch (SQLException e) {
            System.err.println("error while finding all orders because of: " + e.getMessage());
        } finally {
            if (null != stmt)
                DatabaseHelper.closeStatement(stmt);
            if (null != connection)
                DatabaseHelper.closeConnection(connection);
        }

        return new ArrayList<>();

    }

    @Override
    public List<Order> findByCustomerId(int id, int page) {

        Connection connection = DatabaseHelper.getConnection();
        String sql = "SELECT * FROM orders WHERE cid=? LIMIT ?,?";
        PreparedStatement pstm = null;

        try {
            pstm = connection.prepareStatement(sql);
            pstm.setInt(1, id);
            pstm.setInt(2, (page-1)*Order.PAGE_SIZE);
            pstm.setInt(3, Order.PAGE_SIZE);

            ResultSet rs = pstm.executeQuery();
            return getFromResultSet(rs);
        } catch (SQLException e) {
            System.err.println("error while finding orders by customer id because of: " + e.getMessage());
        } finally {
            if (null != pstm)
                DatabaseHelper.closeStatement(pstm);
            if (null != connection)
                DatabaseHelper.closeConnection(connection);
        }

        return new ArrayList<>();
    }

    @Override
    public Order findByOrderId(int id) {
        Connection connection = DatabaseHelper.getConnection();
        String sql = "SELECT * FROM orders WHERE oid=?";
        PreparedStatement pstm = null;

        try {
            pstm = connection.prepareStatement(sql);
            pstm.setInt(1, id);

            ResultSet rs = pstm.executeQuery();
            List<Order> res = getFromResultSet(rs);
            return res.size() > 0 ? res.get(0) : null;

        } catch (SQLException e) {
            System.err.println("error while finding order because of: " + e.getMessage());
        } finally {
            if (null != pstm)
                DatabaseHelper.closeStatement(pstm);
            if (null != connection)
                DatabaseHelper.closeConnection(connection);
        }

        return null;
    }

    @Override
    public int findCount(int id) {

        Connection connection = DatabaseHelper.getConnection();
        String sql = "SELECT count(*) FROM orders WHERE cid=?";
        PreparedStatement pstm = null;

        try {
            pstm = connection.prepareStatement(sql);
            pstm.setInt(1, id);

            ResultSet rs = pstm.executeQuery();
            if (rs.next())
                return rs.getInt(1);
        } catch (SQLException e) {
            System.err.println("error while finding orders' count by customer id because of: " + e.getMessage());
        } finally {
            if (null != pstm)
                DatabaseHelper.closeStatement(pstm);
            if (null != connection)
                DatabaseHelper.closeConnection(connection);
        }

        return 0;

    }

    @Override
    public boolean add(Order order) {

        Connection connection = DatabaseHelper.getConnection();

        String sql = "INSERT INTO orders(cid, pid, quantity, total_price) " +
                "VALUES (?, ?, ?, ?)";
        PreparedStatement pstm = null;

        try {

            pstm = connection.prepareStatement(sql);
            pstm.setInt(1, order.getCustomerId());
            pstm.setInt(2, order.getProductId());
            pstm.setInt(3, order.getQuantity());
            pstm.setDouble(4, order.getTotalPrice());

            pstm.execute();
            return pstm.getUpdateCount() > 0;

        } catch (SQLException e) {
            System.err.println("error while adding order because of: " + e.getMessage());
        } finally {
            if (null != pstm)
                DatabaseHelper.closeStatement(pstm);
            if (null != connection)
                DatabaseHelper.closeConnection(connection);
        }

        return false;

    }

    @Override
    public boolean addAll(List<Order> orders) {

        Connection connection = DatabaseHelper.getConnection();
        String sql = "INSERT INTO orders(cid, pid, quantity, total_price) " +
                "VALUES (?, ?, ?, ?)";
        PreparedStatement pstm = null;

        try {
            connection.setAutoCommit(false);
            pstm = connection.prepareStatement(sql);

            for (Order order : orders) {
                pstm.setInt(1, order.getCustomerId());
                pstm.setInt(2, order.getProductId());
                pstm.setInt(3, order.getQuantity());
                pstm.setDouble(4, order.getTotalPrice());
                pstm.addBatch();
            }

            pstm.executeBatch();
            connection.commit();
            return pstm.getUpdateCount() > 0;

        } catch (SQLException e) {
            System.err.println("error while adding orders because of: " + e.getMessage());
        } finally {
            if (null != pstm)
                DatabaseHelper.closeStatement(pstm);
            if (null != connection)
                DatabaseHelper.closeConnection(connection);
        }

        return false;

    }

    @Override
    public boolean remove(int id) {

        Connection connection = DatabaseHelper.getConnection();

        String sql = "DELETE FROM orders WHERE oid=?";
        PreparedStatement pstm = null;

        try {
            pstm = connection.prepareStatement(sql);
            pstm.setInt(1, id);

            pstm.execute();
            return pstm.getUpdateCount() > 0;

        } catch (SQLException e) {
            System.err.println("error while removing order because of: " + e.getMessage());
        } finally {
            if (null != pstm)
                DatabaseHelper.closeStatement(pstm);
            if (null != connection)
                DatabaseHelper.closeConnection(connection);
        }

        return false;

    }


    private List<Order> getFromResultSet(ResultSet rs) throws SQLException {

        List<Order> orders = new ArrayList<>();

        while (rs.next()) {
            int orderId = rs.getInt("oid");
            int customerId = rs.getInt("cid");
            int productId = rs.getInt("pid");
            int quantity = rs.getInt("quantity");
            double totalPrice = rs.getDouble("total_price");
            LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
            orders.add(new Order(orderId, customerId, productId, quantity, totalPrice, createdAt));
        }

        return orders;
    }

}
