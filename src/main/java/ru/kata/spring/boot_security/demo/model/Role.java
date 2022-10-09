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
@Table(name = "role")
public class Role implements GrantedAuthority {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public Role() {

    }

    @Override
    public String getAuthority() {
        return getName();
    }

    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
