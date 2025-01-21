package com.cafemanagementsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cafemanagementsys.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

}
