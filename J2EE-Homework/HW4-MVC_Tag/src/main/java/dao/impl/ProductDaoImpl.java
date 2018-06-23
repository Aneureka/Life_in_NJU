package dao.impl;

import dao.ProductDao;
import model.Product;
import util.DatabaseHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hiki on 2017-12-19
 */

public class ProductDaoImpl implements ProductDao{

    private static class holder {
        private static final ProductDaoImpl INSTANCE = new ProductDaoImpl();
    }

    private ProductDaoImpl() {}

    public static final ProductDaoImpl getInstance() {
        return ProductDaoImpl.holder.INSTANCE;
    }


    /*========================================== method ===============================================*/

    @Override
    public List<Product> findAll() {

        Connection connection = DatabaseHelper.getConnection();

        List<Product> products = new ArrayList<>();

        // get customers' basic info
        String sql = "SELECT * FROM products";
        Statement stmt = null;

        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                // retrieve the data
                int id = rs.getInt("pid");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                // construct a product and save
                products.add(new Product(id, name, price, quantity));
            }
        } catch (SQLException e) {
            System.err.println("error while finding all products because of: " + e.getMessage());
        } finally {
            if (null != stmt)
                DatabaseHelper.closeStatement(stmt);
            if (null != connection)
                DatabaseHelper.closeConnection(connection);
        }

        return products;
    }

    @Override
    public Product findByProductId(int id) {

        Connection connection = DatabaseHelper.getConnection();

        String sql = "SELECT * FROM products WHERE pid=? LIMIT 1";

        PreparedStatement pstm = null;

        try {
            pstm = connection.prepareStatement(sql);
            pstm.setInt(1, id);

            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                int productId = rs.getInt("pid");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                return new Product(productId, name, price, quantity);
            }

        } catch (SQLException e) {
            System.err.println("error while finding product because of: " + e.getMessage());
        } finally {
            if (null != pstm)
                DatabaseHelper.closeStatement(pstm);
            if (null != connection)
                DatabaseHelper.closeConnection(connection);
        }

        return null;

    }

    @Override
    public boolean add(Product product) {

        Connection connection = DatabaseHelper.getConnection();

        String sql = "INSERT INTO products(name, price, quantity) VALUES(?, ?, ?)";
        PreparedStatement pstm = null;

        try {

            pstm = connection.prepareStatement(sql);

            pstm.setString(1, product.getName());
            pstm.setDouble(2, product.getPrice());
            pstm.setInt(3, product.getQuantity());

            pstm.execute();
            return pstm.getUpdateCount() > 0;

        } catch (SQLException e) {
            System.err.println("error while adding product because of: " + e.getMessage());
        } finally {
            if (null != pstm)
                DatabaseHelper.closeStatement(pstm);
            if (null != connection)
                DatabaseHelper.closeConnection(connection);
        }

        return false;
    }

    @Override
    public boolean addAll(List<Product> products) {

        Connection connection = DatabaseHelper.getConnection();

        String sql = "INSERT INTO products(name, price, quantity) VALUES(?, ?, ?)";
        PreparedStatement pstm = null;

        try {
            connection.setAutoCommit(false);

            pstm = connection.prepareStatement(sql);

            for (Product product : products) {
                pstm.setString(1, product.getName());
                pstm.setDouble(2, product.getPrice());
                pstm.setInt(3, product.getQuantity());
                pstm.addBatch();
            }

            pstm.executeBatch();
            connection.commit();
            return pstm.getUpdateCount() > 0;

        } catch (SQLException e) {
            System.err.println("error while adding products because of: " + e.getMessage());
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

        String sql = "DELETE FROM products WHERE pid=?";
        PreparedStatement pstm = null;

        try {
            pstm = connection.prepareStatement(sql);
            pstm.setInt(1, id);

            pstm.execute();
            return pstm.getUpdateCount() > 0;

        } catch (SQLException e) {
            System.err.println("error while removing product because of: " + e.getMessage());
        } finally {
            if (null != pstm)
                DatabaseHelper.closeStatement(pstm);
            if (null != connection)
                DatabaseHelper.closeConnection(connection);
        }

        return false;
    }
}
