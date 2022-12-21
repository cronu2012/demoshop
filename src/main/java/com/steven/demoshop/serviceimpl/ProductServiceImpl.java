package com.steven.demoshop.serviceimpl;

import com.steven.demoshop.dao.ProductDao;
import com.steven.demoshop.dto.product.ProductQueryParam;
import com.steven.demoshop.model.Product;
import com.steven.demoshop.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Slf4j
@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Integer addProduct(List<Product> products) {
        return productDao.inserts(products);
    }

    @Override
    public Integer modifyProduct(Product product) {
        return productDao.update(product);
    }

    @Transactional
    @Override
    public Integer modifyProduct(List<Map<String, Integer>> mapList) {
        List<Map<String, Integer>> parameter = new ArrayList<>();
        List<Product> products = productDao.selectAll();
        for (Map map : mapList) {
            Integer id = (Integer) map.get("productId");
            Integer quantity = (Integer) map.get("quantity");
            Product product = new Product();
            for(Product p:products){
                if(p.getProductId()==id) product = p;
            }
            Integer newStock = product.getStock() - quantity;
            Map<String, Integer> mapPara = new HashMap<>();
            mapPara.put("productId", id);
            mapPara.put("stock", newStock);
            parameter.add(mapPara);
        }
        return productDao.updatesStock(parameter);
    }

    @Override
    public List<Product> getAll() {
        return productDao.selectAll();
    }

    @Override
    public Product getProduct(Integer productId) {
        Product product = productDao.selectByID(productId);
        if (product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            return product;
        }
    }

    @Override
    public List<Product> getProducts(ProductQueryParam queryParam) {
        List<Product> products = productDao.selectQuery(queryParam);
        return products;
    }

    @Override
    public void delete(Integer productId) {
        productDao.delete(productId);
    }

    @Override
    public void deleteByStore(Integer storeId) {
        productDao.deleteByStore(storeId);
    }
}
