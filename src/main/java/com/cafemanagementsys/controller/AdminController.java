package com.cafemanagementsys.controller;

import com.cafemanagementsys.entity.Product;
import com.cafemanagementsys.repository.AdminRepository;
import com.cafemanagementsys.service.AdminService;
import com.cafemanagementsys.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.cafemanagementsys.entity.Admin;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProductService productService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminRepository adminRepository;

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("admin", new Admin());
        return "/admin/signup";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Admin admin, Model model) {
        adminService.addAdmin(admin);
        return "redirect:/admin/login";
    }

    @GetMapping("/login")
    public String login(Model model) {
        Admin admin = new Admin();
        model.addAttribute("admin", admin);
        return "/admin/login";
    }


    // ========================== AFTER ADMIN LOGIN ===========================================================================================================
    @GetMapping("/index")
    public String index(Model model) {
        return "/admin/index";
    }

    @GetMapping("/products")
    public String products(Model model) {
        List<Product> products = productService.gelAllProduct().getBody();
        model.addAttribute("products", products);
        return "/admin/products";
    }

    @GetMapping("/accounts")
    public String accounts(Model model) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            String email = userDetails.getUsername();
            Admin admin = adminRepository.findByEmail(email); // Fetch full admin details
            model.addAttribute("admin", admin);
        }
        return "/admin/accounts";
    }

    @GetMapping("/add-product")
    public String addProduct(Model model) {
        model.addAttribute("product",new Product());
        return "/admin/add-product";
    }

    @GetMapping("/products/update/{id}")
    public String editProduct(@PathVariable Integer id, Model model) {
        Product product = productService.getProductById(id).getBody();
        model.addAttribute("product",product);
        return "/admin/edit-product";
    }

    @PostMapping("/products/update2/{id}")
    public String editProduct2(@PathVariable Integer id, @ModelAttribute Product product) {
        productService.updateProduct(id, product);
        return "redirect:/admin/products";
    }



    @GetMapping("/products/delete/{id}")
    public String deleteProduct( @PathVariable Integer id) {
        productService.deleteProduct(id);
        return "redirect:/admin/products";
    }



    private static final String UPLOAD_DIR = "uploads/";

    @PostMapping("/products")
    public String addProduct(
            @ModelAttribute Product product,
            @org.springframework.web.bind.annotation.RequestParam("file") MultipartFile file
    ) {



        if (!file.isEmpty()) {
            try {
                // Save the file to the upload directory
                String filename = file.getOriginalFilename();
                Path filePath = Paths.get(UPLOAD_DIR, filename);
                Files.createDirectories(filePath.getParent());
                Files.write(filePath, file.getBytes());
                product.setImageFilename(filename);
            } catch (IOException e) {
                e.printStackTrace();
                return ("Failed to upload image.");
            }
        }

        productService.addProduct(product);

        return "redirect:/admin/products";

    }



}
