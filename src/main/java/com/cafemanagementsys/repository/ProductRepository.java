package com.cafemanagementsys.repository;

import com.cafemanagementsys.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cafemanagementsys.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    // Custom query method to find products by admin
    List<Product> findByAdminId(Admin admin);

}
