package cn.edu.nju.dao;

import cn.edu.nju.model.Product;

import java.util.List;

/**
 * @author hiki on 2017-12-30
 */

public interface ProductDao {

    List<Product> findAll();

    Product findByProductId(int id);

    boolean add(Product product);

    boolean addAll(List<Product> products);

    boolean remove(int id);

}
