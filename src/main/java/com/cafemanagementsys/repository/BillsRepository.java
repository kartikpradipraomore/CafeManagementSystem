package com.cafemanagementsys.repository;

import com.cafemanagementsys.entity.Admin;
import com.cafemanagementsys.entity.Bills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillsRepository extends JpaRepository<Bills, Long> {

    List<Bills> findByAdminId(Admin id);


    
}
