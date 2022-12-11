package com.steven.demoshop.serviceimpl;

import com.steven.demoshop.dao.AdminDao;
import com.steven.demoshop.dto.AdminRequest;
import com.steven.demoshop.model.Administrator;
import com.steven.demoshop.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Component
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;

    @Override
    public Integer addAdmin(AdminRequest adminRequest) {
        Administrator test = adminDao.selectOne(adminRequest.getMemberId(), adminRequest.getStoreId());
        if (test == null) {
            Administrator admin = Administrator.builder()
                    .memberId(adminRequest.getMemberId())
                    .storeId(adminRequest.getStoreId())
                    .permission(adminRequest.getPermission())
                    .build();
            return adminDao.insert(admin);
        } else {
            log.error("Member:{} Store:{} already created", adminRequest.getMemberId(), adminRequest.getStoreId());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Integer modifyPermission(AdminRequest adminRequest) {
        Administrator test = adminDao.selectOne(adminRequest.getMemberId(), adminRequest.getStoreId());
        if (test != null) {
            Administrator admin = Administrator.builder()
                    .memberId(adminRequest.getMemberId())
                    .storeId(adminRequest.getStoreId())
                    .permission(adminRequest.getPermission())
                    .build();
            return adminDao.update(admin);
        } else {
            log.error("Member:{} Store:{} already created", adminRequest.getMemberId(), adminRequest.getStoreId());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public List<Administrator> getStoreAdmins(Integer storeId) {
        return adminDao.selectByStore(storeId);
    }


    @Override
    public void deleteAdmin(Integer memberId, Integer storeId) {
        adminDao.delete(memberId, storeId);
    }
}
