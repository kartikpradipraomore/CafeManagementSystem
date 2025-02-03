package com.cafemanagementsys.entity;
import jakarta.persistence.*;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "bills")
public class Bills {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;
    private double productPrice;
    private int quantity;
    private int totalPrice;
    @Column(name = "created_at")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "admin_id")  // Use 'admin_id' as the foreign key column name
    private Admin adminId;

    @PrePersist
    protected void onCreate() {
        this.date = LocalDate.now();
    }

    // Constructors
    public Bills() {}

    public Bills(String productName, double productPrice, int quantity, LocalDate date, int totalPrice,Admin adminId) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
        this.date = date;
        this.totalPrice = totalPrice;
        this.adminId = adminId;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public double getProductPrice() { return productPrice; }
    public void setProductPrice(double productPrice) { this.productPrice = productPrice; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Admin getAdminId() {
        return adminId;
    }
    public void setAdminId(Admin adminId) {
        this.adminId = adminId;
    }
}
