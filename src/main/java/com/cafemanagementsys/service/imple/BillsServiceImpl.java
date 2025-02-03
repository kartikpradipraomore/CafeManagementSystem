package com.cafemanagementsys.service.imple;

import com.cafemanagementsys.entity.Admin;
import com.cafemanagementsys.entity.Bills;
import com.cafemanagementsys.repository.BillsRepository;
import com.cafemanagementsys.service.BillsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BillsServiceImpl implements BillsService {

    private BillsRepository billsRepository;

    public BillsServiceImpl(BillsRepository billsRepository) {
        this.billsRepository = billsRepository;
    }

    @Override
    public Bills saveBill(Bills bill) {
        return billsRepository.save(bill);
    }

    @Override
    public List<Bills> getAllBills() {
        return billsRepository.findAll();
    }

    @Override
    public Optional<Bills> getBillById(Long id) {
        return billsRepository.findById(id);
    }

    @Override
    public Bills updateBill(Long id, Bills bill) {
        return billsRepository.findById(id).map(existingBill -> {
            existingBill.setProductName(bill.getProductName());
            existingBill.setProductPrice(bill.getProductPrice());
            existingBill.setQuantity(bill.getQuantity());
            existingBill.setDate(LocalDate.now());
            existingBill.setTotalPrice(bill.getTotalPrice());
            existingBill.setAdminId(bill.getAdminId());
            return billsRepository.save(existingBill);
        }).orElseThrow(() -> new RuntimeException("Bill not found"));
    }

    @Override
    public void deleteBill(Long id) {
        billsRepository.deleteById(id);
    }

    @Override
    public List<Bills> getBillsByAdminId(Admin id) {
        return billsRepository.findByAdminId(id);
    }

    @Override
    public int totalPriceOfBills(Admin id) {
       return billsRepository.findByAdminId(id).stream().mapToInt(Bills::getTotalPrice).sum();
    }
}
