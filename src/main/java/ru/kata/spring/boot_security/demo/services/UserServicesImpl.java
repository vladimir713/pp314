package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

@Service
public class UserServicesImpl implements UserService {

    private UserRepository ur;

    @Autowired
    public void setUserRepository(UserRepository ur) {
        this.ur = ur;
    }

    RoleRepository roleRepository;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServicesImpl(UserRepository ur, @Lazy PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.ur = ur;
    }

    @Override
    public User findByUsername(String username) {
        return ur.findByUsername(username);
    }

    @Override
    public List<User> index() {
        return ur.findAll();
    }

    @Override
    public boolean save(User user) {
        User userFromDB = ur.findByUsername(user.getUsername());
        if (userFromDB != null) {
            return false;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        ur.saveAndFlush(user);
        return true;
    }

    @Override
    public void delete(int id) {
        ur.deleteById(id);
    }

    @Override
    public User show(int id) {
        return ur.findById(id).orElse(null);
    }

    @Override
    public void update(User user) {

        if(user.getUsername() == null | user.getUsername().equals("")) {
            user.setUsername(show(user.getId()).getUsername());
        }
        if(user.getPassword() == null | user.getPassword().equals("")) {
            user.setPassword(show(user.getId()).getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        if(user.getRoles() == null) {
            user.setRoles(show(user.getId()).getRoles());
        }

        ur.saveAndFlush(user);
    }

    @Override
    public List<Role> listRoles() {
        return roleRepository.findAll();
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }
}
