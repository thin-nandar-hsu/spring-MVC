package com.example.mvccrud.service;

import com.example.mvccrud.dao.AdminDao;
import com.example.mvccrud.dao.RoleDao;
import com.example.mvccrud.entity.Admin;
import com.example.mvccrud.entity.Role;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {

    private final AdminDao adminDao;
    private final RoleDao roleDao;
    private final PasswordEncoder passwordEncoder;

    public AdminService(AdminDao adminDao, RoleDao roleDao, PasswordEncoder passwordEncoder) {
        this.adminDao = adminDao;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void signUp(Admin admin){
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        Role roleAdmin = roleDao.findRoleByName("ROLE_ADMIN")
                .orElse(role);
        admin.addRole(roleAdmin);

        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
         adminDao.save(admin);

    }
}
