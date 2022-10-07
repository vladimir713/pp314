package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import java.util.List;

@Service
public class UserServicesImpl implements UserService {

    private final UserRepository ur;

    @Autowired
    public UserServicesImpl(UserRepository ur) {
        this.ur = ur;
    }

    @Override
    public List<User> index() {
        return (List<User>) ur.findAll();
    }

//    @Transactional
    @Override
    public void save(User user) {
        ur.save(user);
    }

//    @Transactional
    @Override
    public void delete(int id) {
        ur.deleteById(id);
    }

//    @Transactional
    @Override
    public User show(int id) {
        return ur.findById(id).orElse(null);
    }

}
