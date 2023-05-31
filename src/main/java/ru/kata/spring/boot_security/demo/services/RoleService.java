package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.entitys.Role;

import java.util.Set;

public interface RoleService {

    Set<Role> findAll();

    Role findByName(String name);

    void save(Role role);

}