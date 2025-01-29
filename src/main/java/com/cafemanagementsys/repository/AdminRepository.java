package com.cafemanagementsys.repository;

import com.cafemanagementsys.entity.Admin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends CrudRepository<Admin, String> {

    Admin findByEmail(String email);

}
