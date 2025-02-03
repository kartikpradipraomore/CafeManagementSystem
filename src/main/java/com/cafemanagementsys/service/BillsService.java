package com.cafemanagementsys.service;

import com.cafemanagementsys.entity.Admin;
import com.cafemanagementsys.entity.Bills;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface BillsService {
    Bills saveBill(Bills bill);
    List<Bills> getAllBills();
    Optional<Bills> getBillById(Long id);
    Bills updateBill(Long id, Bills bill);
    void deleteBill(Long id);
    List<Bills> getBillsByAdminId(Admin id);
    int totalPriceOfBills(Admin id);
}
