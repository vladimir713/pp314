package ru.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    User findByUsername(String user);

    List<User> index();

    boolean save(User user);

    void delete(int id);

    User show(int id);

    void update(User user);

}
