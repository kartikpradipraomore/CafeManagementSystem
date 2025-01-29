package com.cafemanagementsys.service;

import com.cafemanagementsys.entity.Admin;
import com.cafemanagementsys.entity.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    ResponseEntity<String> addProduct(Product product);
    ResponseEntity<List<Product>> gelAllProduct();
    ResponseEntity<Product> getProductById(Integer id);
    ResponseEntity<String> updateProduct(Integer id, Product product);
    ResponseEntity<String> deleteProduct(Integer id);
    List<Product> getProductsByAdmin(Admin admin);

}
