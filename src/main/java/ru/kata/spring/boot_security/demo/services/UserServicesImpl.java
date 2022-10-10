package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Service
public class UserServicesImpl implements UserService {

    //@PersistenceContext
    private UserRepository ur;

    @Autowired
    public void setUr(UserRepository ur) {
        this.ur = ur;
    }

    RoleRepository roleRepository;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    //@Autowired
    private PasswordEncoder bCryptPasswordEncoder;

//    @Autowired
//    public void setBCryptPasswordEncoder(PasswordEncoder bCryptPasswordEncoder) {
//        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//    }

    @Autowired
    public UserServicesImpl(UserRepository ur) {
        this.ur = ur;
    }

    public User findByUsername(String username) {
        return ur.findByUsername(username);
    }

    @Override
    public List<User> index() {
        return ur.findAll();
    }

//    @Transactional
    @Override
    public boolean save(User user) {
        User userFromDB = ur.findByUsername(user.getUsername());
        if (userFromDB != null) {
            return false;
        }
        user.setRoles(Collections.singleton(new Role(2, "USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        ur.save(user);
        return true;
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

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                mapRolesToAutorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAutorities(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }
}
