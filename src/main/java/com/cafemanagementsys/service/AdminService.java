package com.cafemanagementsys.service;

import com.cafemanagementsys.entity.Admin;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {

    void addAdmin(Admin admin);
    void updateAdmin(Admin admin);
    void deleteAdmin(String id);

}
