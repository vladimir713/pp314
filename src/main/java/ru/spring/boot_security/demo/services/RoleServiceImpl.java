package ru.spring.boot_security.demo.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spring.boot_security.demo.model.Role;
import ru.spring.boot_security.demo.repository.RoleRepository;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional
    public Collection<Role> findAll() {
        return new ArrayList<>(roleRepository.findAll());
    }

}
