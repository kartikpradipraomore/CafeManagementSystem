package com.cafemanagementsys.controller;

import com.cafemanagementsys.entity.Bills;
import com.cafemanagementsys.entity.Product;
import com.cafemanagementsys.repository.AdminRepository;
import com.cafemanagementsys.service.AdminService;
import com.cafemanagementsys.service.BillsService;
import com.cafemanagementsys.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import java.util.Optional;

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
        return "admin/signup";
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
        return "admin/login";
    }



    // ========================== AFTER ADMIN LOGIN ===========================================================================================================
    @GetMapping("/index")
    public String index(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            String email = userDetails.getUsername();
            Admin admin = adminRepository.findByEmail(email); // Fetch full admin details
            model.addAttribute("admin", admin);
        }
        return "admin/index";
    }

    @GetMapping("/products")
    public String products(Model model) {
        // Fetch the authenticated Admin
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            String email = userDetails.getUsername();
            Admin admin = adminRepository.findByEmail(email); // Fetch full admin details

            // Fetch products belonging to the authenticated admin
            List<Product> products = productService.getProductsByAdmin(admin);

            model.addAttribute("products", products);
        }
        return "admin/products";
    }


    @GetMapping("/accounts")
    public String accounts(Model model) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            String email = userDetails.getUsername();
            Admin admin = adminRepository.findByEmail(email); // Fetch full admin details
            model.addAttribute("admin", admin);
        }
        return "admin/accounts";
    }

    @PostMapping("/accounts/update")
    public String accountsUpdate(@ModelAttribute Admin formAdmin, Model model) {
        adminService.updateAdmin(formAdmin);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            String email = userDetails.getUsername();
            Admin admin = adminRepository.findByEmail(email); // Fetch full admin details
            model.addAttribute("admin", admin);
        }
        return "admin/accounts";
    }
    @GetMapping("/account/delete/{id}")
    public String accountDelete(@PathVariable String id, Model model) {
        adminService.deleteAdmin(id);
        return "redirect:/";
    }



    @GetMapping("/add-product")
    public String addProduct(Model model) {
        // Fetch the authenticated Admin
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            String email = userDetails.getUsername();
            Admin admin = adminRepository.findByEmail(email); // Fetch full admin details
            model.addAttribute("admin", admin);

            // Create a new Product object and associate the admin
            Product product = new Product();
            product.setAdminId(admin);  // Set the admin for the product
            model.addAttribute("product", product);
        }
        return "admin/add-product";
    }

    @GetMapping("/products/update/{id}")
    public String editProduct(@PathVariable Integer id, Model model) {
        Product product = productService.getProductById(id).getBody();
        model.addAttribute("product",product);
        return "admin/edit-product";
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


    // ===============================================================================================================================

    @Autowired
    private BillsService billsService;

    @GetMapping("/bill")
    public String showBillForm(Model model) {
        // Fetch the authenticated Admin
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            String email = userDetails.getUsername();
            Admin admin = adminRepository.findByEmail(email); // Fetch full admin details
            model.addAttribute("admin", admin);
            int total = billsService.totalPriceOfBills(admin);
            Bills bill = new Bills();
            bill.setAdminId(admin);

            model.addAttribute("totalPrice",total);
            model.addAttribute("bills", bill);
            model.addAttribute("allBills", billsService.getBillsByAdminId(admin)); // Fetch all bills to display in the left section

        }
        return "admin/bill";
    }

    @PostMapping("/bill")
    public String createBill(@ModelAttribute Bills bill, Model model) {
        // Calculate the total price
        bill.setTotalPrice((int) (bill.getQuantity() * bill.getProductPrice()));

        // Save the bill in the database
        billsService.saveBill(bill);

        // Add the new bill to the model to display it in the left section

        // Fetch the authenticated Admin
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            String email = userDetails.getUsername();
            Admin admin = adminRepository.findByEmail(email); // Fetch full admin details

            // Fetch products belonging to the authenticated admin
            List<Bills> Bills = billsService.getBillsByAdminId(admin);
            int total = billsService.totalPriceOfBills(admin);

            model.addAttribute("allBills",Bills);
            model.addAttribute("totalPrice",total);
        }


        model.addAttribute("allBills", billsService.getAllBills());

        // Redirect to the same page to avoid resubmitting the form
        return "redirect:/admin/bill";
    }

    @GetMapping("/bill/delete/{id}")
    public String deleteBill(@PathVariable Long id, Model model) {

        billsService.deleteBill(id);
        // Fetch the authenticated Admin
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            String email = userDetails.getUsername();
            Admin admin = adminRepository.findByEmail(email); // Fetch full admin details

            // Fetch products belonging to the authenticated admin
            List<Bills> Bills = billsService.getBillsByAdminId(admin);
            model.addAttribute("allBills",Bills);
            int total = billsService.totalPriceOfBills(admin);
            model.addAttribute("totalPrice",total);
        }

        return "redirect:/admin/bill";
    }

    @GetMapping("/bill/update/{id}")
    public String updateBill(@PathVariable Long id, Model model) {

        Optional<Bills> bill = billsService.getBillById(id);
        if (bill.isPresent()) {
            model.addAttribute("bills", bill.get());
        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            String email = userDetails.getUsername();
            Admin admin = adminRepository.findByEmail(email); // Fetch full admin details

            // Fetch products belonging to the authenticated admin
            List<Bills> Bills = billsService.getBillsByAdminId(admin);

            model.addAttribute("allBills",Bills);
            int total = billsService.totalPriceOfBills(admin);
            model.addAttribute("totalPrice",total);
        }
        return "/admin/updateBill";
    }



    @PostMapping("/bill/update/{id}")
    public String updateBill(@PathVariable Long id,@ModelAttribute Bills bills, Model model) {
        // Calculate the total price
        bills.setTotalPrice((int) (bills.getQuantity() * bills.getProductPrice()));
        billsService.updateBill(id,bills);
        // Fetch the authenticated Admin
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            String email = userDetails.getUsername();
            Admin admin = adminRepository.findByEmail(email); // Fetch full admin details

            // Fetch products belonging to the authenticated admin
            List<Bills> Bills = billsService.getBillsByAdminId(admin);

            model.addAttribute("allBills",Bills);
            int total = billsService.totalPriceOfBills(admin);
            model.addAttribute("totalPrice",total);
        }

        return "redirect:/admin/bill";
    }



}
