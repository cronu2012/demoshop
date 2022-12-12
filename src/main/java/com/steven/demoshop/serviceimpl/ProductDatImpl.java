package com.steven.demoshop.serviceimpl;

import com.steven.demoshop.dao.ProductDao;
import com.steven.demoshop.model.Product;
import com.steven.demoshop.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ProductDatImpl implements ProductService {


    @Autowired
    private ProductDao productDao;

    @Override
    public Integer addProduct(List<Product> products) {
        return productDao.inserts(products);
    }

    @Override
    public Integer modifyProduct(Product product) {
        return null;
    }

    @Override
    public List<Product> getAll() {
        return null;
    }

    @Override
    public Product getProduct(Integer productId) {
        return null;
    }

    @Override
    public List<Product> getByStore(Integer storeId) {
        return null;
    }

    @Override
    public void delete(Integer productId) {

    }

    @Override
    public void deleteByStore(Integer storeId) {

    }
}
