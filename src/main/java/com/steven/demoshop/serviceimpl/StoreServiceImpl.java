package com.steven.demoshop.serviceimpl;

import com.steven.demoshop.dao.StoreDao;
import com.steven.demoshop.model.Store;
import com.steven.demoshop.service.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class StoreServiceImpl implements StoreService {

    @Autowired
    private StoreDao storeDao;

    @Override
    public Integer insertOrUpdate(Store store) {
        List<Store> stores = storeDao.selectAll();
        for (Store s : stores) {
            if (store.getStorePhone().equals(s.getStorePhone())) {
                log.error("This phone number already used");
                return null;
            }
        }
        return storeDao.insertOrUpdate(store);
    }

    @Override
    public void delete(Integer id) {
        storeDao.delete(id);
    }

    @Override
    public List<Store> getStores() {
        return storeDao.selectAll();
    }

    @Override
    public Store getStore(Integer id) {
        return storeDao.selectOne(id);
    }

    @Override
    public Store getStore(String name) {
        return storeDao.selectOne(name);
    }
}
