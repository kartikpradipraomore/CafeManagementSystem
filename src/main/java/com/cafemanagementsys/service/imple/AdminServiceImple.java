package com.cafemanagementsys.service.imple;

import com.cafemanagementsys.entity.Admin;
import com.cafemanagementsys.repository.AdminRepository;
import com.cafemanagementsys.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AdminServiceImple implements AdminService {


    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Uses BCrypt

    public void addAdmin(Admin admin) {
        // Hash password before saving
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        adminRepository.save(admin);
    }
}