package cn.edu.nju.dao.impl;


import cn.edu.nju.dao.CustomerDao;
import cn.edu.nju.model.Customer;
import cn.edu.nju.util.DatabaseHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hiki on 2017-12-18
 */

public class CustomerDaoImpl implements CustomerDao {

    private static class holder {
        private static final CustomerDaoImpl INSTANCE = new CustomerDaoImpl();
    }

    private CustomerDaoImpl() {}

    public static final CustomerDaoImpl getInstance() {
        return holder.INSTANCE;
    }

    /*========================================== method ===============================================*/

    @Override
    public List<Customer> findAll() {

        Connection connection = DatabaseHelper.getConnection();

        List<Customer> customers = new ArrayList<>();

        // get customers' basic info
        String sql = "SELECT * FROM customers";
        Statement stmt = null;

        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                // retrieve the data
                int id = rs.getInt("cid");
                String password = rs.getString("password");
                String name = rs.getString("name");
                // construct a customer and save
                customers.add(new Customer(id, password, name));
            }

        } catch (SQLException e) {
            System.err.println("error while finding all customers because of: " + e.getMessage());
        } finally {
            if (null != stmt)
                DatabaseHelper.closeStatement(stmt);
            if (null != connection)
                DatabaseHelper.closeConnection(connection);
        }

        return customers;

    }

    @Override
    public Customer findByCustomerId(int id) {
        Connection connection = DatabaseHelper.getConnection();

        // get customer's basic info
        String sql = "SELECT * FROM customers WHERE cid=? LIMIT 1";
        PreparedStatement pstm = null;

        try {
            pstm = connection.prepareStatement(sql);
            pstm.setInt(1, id);

            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                // retrieve the data
                int customerId = rs.getInt("cid");
                String password = rs.getString("password");
                String name = rs.getString("name");
                return new Customer(customerId, password, name);
            }

        } catch (SQLException e) {
            System.err.println("error while finding customer because of: " + e.getMessage());
        } finally {
            if (null != pstm)
                DatabaseHelper.closeStatement(pstm);
            if (null != connection)
                DatabaseHelper.closeConnection(connection);
        }

        return null;
    }

    @Override
    public boolean login(int id, String password) {

        Connection connection = DatabaseHelper.getConnection();
        String sql = "SELECT * FROM customers WHERE cid=? AND password=?";
        PreparedStatement pstm = null;
        try {
            // construct the statement
            pstm = connection.prepareStatement(sql);
            pstm.setInt(1, id);
            pstm.setString(2, password);

            // get result
            ResultSet rs = pstm.executeQuery();
            if (rs.next())
                return true;
        } catch (SQLException e) {
            System.err.println("error while logging in because of: " + e.getMessage());
        } finally {
            if (null != pstm)
                DatabaseHelper.closeStatement(pstm);
            if (null != connection)
                DatabaseHelper.closeConnection(connection);
        }

        return false;
    }

    @Override
    public boolean add(Customer customer) {

        Connection connection = DatabaseHelper.getConnection();
        String sql = "INSERT INTO customers VALUES(?, ?, ?)";
        PreparedStatement pstm = null;

        try {
            // construct the statement
            pstm = connection.prepareStatement(sql);
            pstm.setInt(1, customer.getId());
            pstm.setString(2, customer.getPassword());
            pstm.setString(3, customer.getName());

            // get result
            pstm.execute();
            return pstm.getUpdateCount() > 0;

        } catch (SQLException e) {
            System.err.println("error while creating customer because of: " + e.getMessage());
        } finally {
            if (null != pstm)
                DatabaseHelper.closeStatement(pstm);
            if (null != connection)
                DatabaseHelper.closeConnection(connection);
        }

        return false;
    }

    @Override
    public boolean isExisted(int id){

        Connection connection = DatabaseHelper.getConnection();
        String sql = "SELECT * FROM customers WHERE cid=?";
        PreparedStatement pstm = null;
        try {
            // construct the statement
            pstm = connection.prepareStatement(sql);
            pstm.setInt(1, id);

            // get result
            ResultSet rs = pstm.executeQuery();
            if (rs.next())
                return true;
        } catch (SQLException e) {
            System.err.println("error while judging if customer exists in because of: " + e.getMessage());
        } finally {
            if (null != pstm)
                DatabaseHelper.closeStatement(pstm);
            if (null != connection)
                DatabaseHelper.closeConnection(connection);
        }

        return false;
    }

}

