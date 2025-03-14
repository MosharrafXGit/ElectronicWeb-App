package com.lcwd.electronicstore;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.lcwd.electronicstore.entities.Role;
import com.lcwd.electronicstore.entities.User;
import com.lcwd.electronicstore.repository.RoleRepo;
import com.lcwd.electronicstore.repository.UserRepo;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@EnableWebMvc
public class ElectronicStoreApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ElectronicStoreApplication.class, args);
    }

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private UserRepo userrepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        System.out.println("UserRepo initialized: " + (userrepo != null));
    }

    @Override
    public void run(String... args) throws Exception {
        // Ensure ROLE_ADMIN exists
        Role roleAdmin = roleRepo.findByName("ROLE_ADMIN").orElse(null);
        if (roleAdmin == null) {
            Role role1 = new Role();
            role1.setRoleId(UUID.randomUUID().toString());
            role1.setName("ROLE_ADMIN");
            roleAdmin = roleRepo.save(role1);
        }

        // Ensure ROLE_NORMAL exists (Fixed the issue where roleAdmin was checked again)
        Role roleNormal = roleRepo.findByName("ROLE_NORMAL").orElse(null);
        if (roleNormal == null) {
            Role role2 = new Role();
            role2.setRoleId(UUID.randomUUID().toString());
            role2.setName("ROLE_NORMAL");
            roleNormal = roleRepo.save(role2);
        }

        // Check if the user already exists
        User user = userrepo.findByEmail("syed@gmail.com").orElse(null);
        if (user == null) {
            user = new User();
            user.setUserId(UUID.randomUUID().toString());
            user.setUserName("Syed");
            user.setEmail("syed@gmail.com");
            user.setPassword(passwordEncoder.encode("syed"));

            // Ensure at least one role is assigned to the user
            if (roleAdmin != null) {
                user.setRoles(List.of(roleAdmin));
            } else if (roleNormal != null) {
                user.setRoles(List.of(roleNormal));
            } else {
                user.setRoles(List.of()); // Avoid null pointer issues
            }

            userrepo.save(user);
        }
    }
}
