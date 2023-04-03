package com.example.mvccrud.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER )
    private List<Role> roles = new ArrayList<>();

    public void addRole(Role role){
         roles.add(role);
         role.getAdmins().add(this);
    }
}
