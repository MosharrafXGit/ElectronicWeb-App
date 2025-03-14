package com.lcwd.electronicstore.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User implements UserDetails {

    @Id
    @Column(name = "id")
    private String userId;

    @Column
    private String userName;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String gender;

    @Column
    private String about;

    @Column
    private String imageName;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Order> orders = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        // Implement your logic here
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Implement your logic here
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Implement your logic here
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Implement your logic here
        return true;
    }
}
