package com.example.mvccrud.dao;

import com.example.mvccrud.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminDao extends JpaRepository<Admin,Integer> {
   Optional<Admin> findAdminByUsername(String username);
}
