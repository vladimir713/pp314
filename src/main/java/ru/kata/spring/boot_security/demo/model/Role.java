package ru.kata.spring.boot_security.demo.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import javax.persistence.*;

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
