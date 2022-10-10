package ru.kata.spring.boot_security.demo.model;

import lombok.Data;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Vladimir Chugunov
 */
@Data
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

//    @Transient
//    @ManyToMany(mappedBy = "roles")
//    private Set<User> users;

    public Role() {

    }

    public Role(String name) {
        this.name = name;
    }
    @Override
    public String getAuthority() {
        return getName();
    }

    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Role(int id) {
        this.id = id;
    }
}
