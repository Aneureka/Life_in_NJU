package cn.edu.nju.dao.impl;


import cn.edu.nju.dao.ProductDao;
import cn.edu.nju.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author hiki on 2017-12-19
 */

@Repository
@Transactional
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private AbstractDao<Product> dao;


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
