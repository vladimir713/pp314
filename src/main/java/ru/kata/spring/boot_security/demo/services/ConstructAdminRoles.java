package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import javax.annotation.PostConstruct;

@Service
public class ConstructAdminRoles {

   final private UserRepository ur;

    final private RoleRepository rr;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ConstructAdminRoles(@Lazy PasswordEncoder passwordEncoder, UserRepository ur, RoleRepository rr) {
        this.passwordEncoder = passwordEncoder;
        this.ur = ur;
        this.rr = rr;
    }

    @PostConstruct
    public void postConstruct() {

        Role role1 = new Role(1, "ADMIN");
        rr.save(role1);
        Role role2 = new Role(2, "USER");
        rr.save(role2);
        rr.flush();

        ur.deleteAll();
        ur.flush();
        User admin = new User();
        admin.setUsername("admin@mail.ru");
        admin.setFirstName("admin");
        admin.setLastName("admin");
        admin.setAge(51);
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.getRoles().add(role1);
        admin.getRoles().add(role2);
        ur.saveAndFlush(admin);
    }
}
