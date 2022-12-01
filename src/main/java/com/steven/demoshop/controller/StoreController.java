package com.steven.demoshop.controller;

import com.steven.demoshop.constant.Regex;
import com.steven.demoshop.dto.StoreRequest;
import com.steven.demoshop.model.Store;
import com.steven.demoshop.service.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/demoshop")
public class StoreController {

    @Autowired
    private StoreService storeService;


    @GetMapping("/stores/{id}")
    public ResponseEntity<?> getStore(@PathVariable @Min(1) Integer id) {
        Store store = storeService.getStore(id);

        if (store == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(store);
        }
    }

    @GetMapping("/stores")
    public ResponseEntity<?> getStores(@RequestParam(required = false) String name) {
        if (name == null) {
            List<Store> stores = storeService.getStores();
            if (stores == null) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(stores);
            }
        }
        Store store = storeService.getStore(name);
        if (store == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(store);
        }
    }

    @PostMapping("/stores")
    public ResponseEntity<?> insert(@RequestBody @Valid StoreRequest storeReq) {
        if (!checkRequestParam(storeReq)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            Store store = Store.builder()
                    .storeName(storeReq.getStoreName())
                    .storePhone(storeReq.getStorePhone())
                    .intro(storeReq.getIntro())
                    .build();
            Integer id = storeService.insertOrUpdate(store);
            Store result = storeService.getStore(id);
            if (result == null) {
                log.error("create failed");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            } else {
                log.info(result.toString());
                log.info("update success");
                return ResponseEntity.status(HttpStatus.CREATED).body(result);
            }
        }
    }

    @PutMapping("/stores/{id}")
    public ResponseEntity<?> update(
            @PathVariable @Min(1) Integer id,
            @RequestBody @Valid StoreRequest storeReq
    ) {
        if (!checkRequestParam(storeReq)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            Store store = Store.builder()
                    .storeId(id)
                    .storeName(storeReq.getStoreName())
                    .storePhone(storeReq.getStorePhone())
                    .intro(storeReq.getIntro())
                    .build();
            Integer storeId = storeService.insertOrUpdate(store);
            Store result = storeService.getStore(storeId);
            if (result == null) {
                log.error("update failed");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            } else {
                log.info(result.toString());
                log.info("update success");
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }

        }

    }

    @DeleteMapping("/stores/{id}")
    public ResponseEntity<?> deleteMember(@PathVariable @Min(1) Integer id) {
        storeService.delete(id);
        Store store = storeService.getStore(id);
        if (store == null) {
            log.info("delete success");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }else {
            log.error("delete failed");
            return ResponseEntity.status(HttpStatus.OK).body(store);
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