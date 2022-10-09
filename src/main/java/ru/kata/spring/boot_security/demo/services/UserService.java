package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    public List<User> index();

    public boolean save(User user);

    public void delete(int id);

    public User show(int id);

//    public void update(int id, User user);
}
