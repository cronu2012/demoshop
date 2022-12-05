package com.steven.demoshop.service;

import com.steven.demoshop.dto.AdminRequest;
import com.steven.demoshop.model.Administrator;

import java.util.List;

public interface AdminService {
    Integer addAdmin(AdminRequest adminRequest);

    Integer modifyPermission(AdminRequest adminRequest);

    List<Administrator> getStoreAdmins(Integer storeId);

    void deleteAdmin(Integer memberId,Integer storeId);
}
