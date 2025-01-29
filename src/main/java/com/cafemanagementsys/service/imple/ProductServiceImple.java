package com.cafemanagementsys.service.imple;

import com.cafemanagementsys.entity.Product;
import com.cafemanagementsys.repository.ProductRepository;
import com.cafemanagementsys.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImple implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ResponseEntity<String> addProduct(Product product) {
        try{
            productRepository.save(product);
           return new ResponseEntity<>("Product Added Successfully.", HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>("Internal Server Error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Product>> gelAllProduct() {
        try{
            return new ResponseEntity<>(productRepository.findAll(), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Product> getProductById(Integer id) {
        if(productRepository.findById(id).isPresent()){
            return new ResponseEntity<>(productRepository.findById(id).get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<String> updateProduct(Integer id, Product product) {
        if(productRepository.findById(id).isEmpty()){
            return new ResponseEntity<>("Product Is Not Present With Given Id.", HttpStatus.NOT_FOUND);
        }
        Product oldProduct = productRepository.findById(id).get();
        oldProduct.setProductName(product.getProductName());
        oldProduct.setImageFilename(product.getImageFilename());
        oldProduct.setStockQuantity(product.getStockQuantity());
        oldProduct.setProductDescription(product.getProductDescription());
        oldProduct.setProductPrice(product.getProductPrice());
        oldProduct.setProductCategory(product.getProductCategory());
        productRepository.save(oldProduct);
        return new ResponseEntity<>("Product Updated Successfully.", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteProduct(Integer id) {
        try{
            productRepository.deleteById(id);
        }catch (Exception e){
            return new ResponseEntity<>("Internal Server Error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Product Deleted Successfully.", HttpStatus.OK);
    }
}
