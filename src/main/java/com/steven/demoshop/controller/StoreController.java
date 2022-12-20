package com.steven.demoshop.controller;

import com.steven.demoshop.constant.Regex;
import com.steven.demoshop.dto.StoreRequest;
import com.steven.demoshop.dto.product.ProductAdd;
import com.steven.demoshop.dto.product.ProductModify;
import com.steven.demoshop.model.Product;
import com.steven.demoshop.model.Store;
import com.steven.demoshop.service.ProductService;
import com.steven.demoshop.service.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Validated
@RestController
@RequestMapping("/demoshop")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @Autowired
    private ProductService productService;

    @GetMapping("/stores/{id}")
    public ResponseEntity<?> getStore(@PathVariable @Min(1) Integer id) {
        Store store = storeService.getStore(id);
        if (store == null) {
            log.error("StoreId:{} is not found", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(store);
        }
    }

    @GetMapping("/stores")
    public ResponseEntity<?> getStores(@RequestParam(required = false) String name) {
        if (name == null) {
            List<Store> stores = storeService.getStores();
            return ResponseEntity.status(HttpStatus.OK).body(stores);
        }
        Store store = storeService.getStore(name);
        if (store == null) {
            log.error("Store Name:{} is not found", name);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(store);
        }
    }

    @PostMapping("/stores")
    public ResponseEntity<?> createStore(@RequestBody @Valid StoreRequest storeReq) {
        Store store = Store.builder()
                .storeName(storeReq.getStoreName())
                .storePhone(storeReq.getStorePhone())
                .intro(storeReq.getIntro())
                .build();
        Integer id = storeService.createStore(store);
        Store result = storeService.getStore(id);
        if (result == null) {
            log.error("create failed");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            log.info(result.toString());
            log.info("create success");
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }

    }

    @PutMapping("/stores/{id}")
    public ResponseEntity<?> modifyStore(
            @PathVariable @Min(1) Integer id,
            @RequestBody @Valid StoreRequest storeReq
    ) {
        Store store = Store.builder()
                .storeId(id)
                .storeName(storeReq.getStoreName())
                .storePhone(storeReq.getStorePhone())
                .intro(storeReq.getIntro())
                .build();
        Integer storeId = storeService.modifyStore(store);
        Store result = storeService.getStore(storeId);
        if (result == null) {
            log.error("update failed");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            log.info(result.toString());
            log.info("update success");
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
    }

    @DeleteMapping("/stores/{id}")
    public ResponseEntity<?> deleteStore(@PathVariable @Min(1) Integer id) {
        storeService.delete(id);
        Store store = storeService.getStore(id);
        if (store == null) {
            log.info("delete success");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            log.error("delete failed");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(store);
        }
    }

    @PostMapping("/products")
    public ResponseEntity<String> addProducts(@RequestBody @Valid List<ProductAdd> productAdds) {
        List<Product> list = new ArrayList<>();
        for (ProductAdd add : productAdds) {
            Product item = Product.builder()
                    .storeId(add.getStoreId())
                    .category(add.getCategory())
                    .productName(add.getProductName())
                    .price(add.getPrice())
                    .info(add.getInfo())
                    .stock(add.getStock())
                    .status(add.getStatus())
                    .build();
            list.add(item);
        }
        Integer result = productService.addProduct(list);
        return ResponseEntity.status(HttpStatus.CREATED).body("已成功新增" + result + "筆商品");
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<?> modifyProduct(
            @PathVariable @Min(1) Integer id,
            @RequestBody @Valid ProductModify pm
    ) {
        Product result = productService.getProduct(id);

        Product product = Product.builder()
                .productId(id)
                .storeId(pm.getStoreId() == null ? result.getStoreId() : pm.getStoreId())
                .category(pm.getCategory() == null ? result.getCategory() : pm.getCategory())
                .productName(pm.getProductName() == null ? result.getProductName() : pm.getProductName())
                .price(pm.getPrice() == null ? result.getPrice() : pm.getPrice())
                .info(pm.getInfo() == null ? result.getInfo() : pm.getInfo())
                .stock(pm.getStock() == null ? result.getStock() : pm.getStock())
                .status(pm.getStatus() == null ? result.getStatus() : pm.getStatus())
                .build();
        Integer i = productService.modifyProduct(product);
        Product updatedProduct = productService.getProduct(i);
        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
    }

    @PutMapping("/products/stock")
    public ResponseEntity<?> modifyStocks(@RequestBody List<Map<String, Integer>> mapList) {
        List<Map<String, Integer>> list = new ArrayList<>();
        for (Map map : mapList) {
            Integer id = (Integer) map.get("productId");
            Integer quantity = (Integer) map.get("quantity");
            if (!checkMapParam(id, quantity)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            Map<String, Integer> para = new HashMap<>();
            para.put("productId", id);
            para.put("quantity", quantity);
            list.add(para);
        }
        Integer i = productService.modifyProduct(list);
        return ResponseEntity.status(HttpStatus.OK).body("Updated " + i + " rows");
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable @Min(1) Integer productId) {
        Product product = productService.getProduct(productId);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(
            @RequestParam(required = false) @Min(1) Integer storeId
    ) {
        if (storeId == null) {
            List<Product> products = productService.getAll();
            return ResponseEntity.status(HttpStatus.OK).body(products);
        } else {
            List<Product> product = productService.getStoreProduct(storeId);
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }
    }


    boolean checkMapParam(Integer id, Integer quantity) {
        if (id < 1 || quantity < 1 || quantity > 10) {
            return false;
        } else {
            return true;
        }
    }

    boolean checkRequestParam(StoreRequest storeReq) {
        boolean isCurrent = true;

        if (!storeReq.getStoreName().matches(Regex.NAME.getRegexString())) {
            isCurrent = false;
            log.error("store name error");
        }
        if (!storeReq.getStorePhone().matches(Regex.PHONE.getRegexString())) {
            isCurrent = false;
            log.error("store phone error");
        }
        return isCurrent;
    }
}
