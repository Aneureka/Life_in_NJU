package cn.edu.nju.dao.impl;


import cn.edu.nju.dao.ProductDao;
import cn.edu.nju.model.Product;

import java.util.List;

/**
 * @author hiki on 2017-12-19
 */

public class ProductDaoImpl implements ProductDao {

    private AbstractDao<Product> dao;

    private static class holder {
        private static final ProductDaoImpl INSTANCE = new ProductDaoImpl();
    }

    private ProductDaoImpl() {
        dao = new AbstractDao<>(Product.class);
    }

    public static ProductDaoImpl getInstance() {
        return holder.INSTANCE;
    }


    /*========================================== method ===============================================*/

    @Override
    public List<Product> findAll() {
        return dao.findAll();
    }

    @Override
    public Product findByProductId(int id) {
        return dao.findOne(id);
    }

    @Override
    public boolean add(Product product) {
        return dao.create(product);
    }

    @Override
    public boolean addAll(List<Product> products) {
        return dao.createBatch(products);
    }

    @Override
    public boolean remove(int id) {
        return dao.removeById(id);
    }
}
