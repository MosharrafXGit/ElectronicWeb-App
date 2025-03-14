package com.lcwd.electronicstore.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lcwd.electronicstore.dtos.UserDto;
import com.lcwd.electronicstore.entities.Role;
import com.lcwd.electronicstore.entities.User;
import com.lcwd.electronicstore.exception.ResourceNotFoundException;
import com.lcwd.electronicstore.repository.RoleRepo;
import com.lcwd.electronicstore.repository.UserRepo;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService, UserDetailsService {  // ✅ Implement UserDetailsService

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private ModelMapper mapper;

    @Value("${user.profile.image.path}")
    private String imagePath;

    @Override
    public UserDto createUser(UserDto userDto) {
        String userID = UUID.randomUUID().toString();
        userDto.setUserId(userID);

        User user = dtoToEntity(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role = roleRepo.findByName("ROLE_NORMAL")
                .orElseGet(() -> {
                    Role newRole = new Role(UUID.randomUUID().toString(), "ROLE_NORMAL", null);
                    return roleRepo.save(newRole);
                });

        user.setRoles(List.of(role));

        User savedUser = userRepository.save(user);
        return entityToDto(savedUser);
    }

    @Override
    public UserDto updateUser(String userId, UserDto userDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found !!"));

        user.setUserName(userDto.getUserName());
        user.setEmail(userDto.getEmail());
        user.setAbout(userDto.getAbout());
        user.setImageName(userDto.getImageName());

        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }

        User savedUser = userRepository.save(user);
        return mapper.map(savedUser, UserDto.class);
    }

    @Transactional
    @Override
    public void deleteUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        String fullPath = imagePath + user.getImageName();
        Path path = Paths.get(fullPath);
        try {
            if (Files.exists(path)) {
                Files.delete(path);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete profile image", e);
        }

        userRepository.delete(user);
    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> mapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserByID(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found !!"));
        return mapper.map(user, UserDto.class);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found !!"));
        return mapper.map(user, UserDto.class);
    }

    @Override
    public UserDto findByNameAndPassword(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Invalid Credentials");
        }

        return mapper.map(user, UserDto.class);
    }

    // ✅ Implement loadUserByUsername for authentication
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));

        List<SimpleGrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName())) // Role must be in "ROLE_" format
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

    public UserDto entityToDto(User user) {
        return mapper.map(user, UserDto.class);
    }

    public User dtoToEntity(UserDto userDto) {
        return mapper.map(userDto, User.class);
    }
}
