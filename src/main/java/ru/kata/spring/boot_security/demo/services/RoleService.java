package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.model.Role;
import java.util.Collection;


public interface RoleService {

    Collection<Role> findAll();
}
