package org.example.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.dto.SingupRequwest;
import org.example.entity.UserEntity;
import org.example.enums.UserRoles;
import org.example.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService {
    final UserRepository userRepository;
    final ModelMapper modelMapper;
    final BCryptPasswordEncoder passwordEncoder;

    @PostConstruct
    public void createAdminaccount() {
        Optional<UserEntity> adminAccount = userRepository.findByRoles(UserRoles.ADMIN);
        if (adminAccount.isEmpty()) {
            UserEntity newAdminAccount = new UserEntity();
            newAdminAccount.setName("ADMIN");
            newAdminAccount.setRoles(UserRoles.ADMIN);
            newAdminAccount.setEmail("ht['");
            newAdminAccount.setPassword(passwordEncoder.encode("12345678")); // Encode the password
            userRepository.save(newAdminAccount);
            System.out.println("Admin account created successfully.");
        } else {
            System.out.println("Admin account already exists.");
        }
    }

    @Override
    public UserEntity createCustomer(SingupRequwest signupRequest) {
        System.out.println("Creating user: " + signupRequest);
        UserEntity user = modelMapper.map(signupRequest, UserEntity.class);
        user.setRoles(UserRoles.CUSTOMER);
        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());
        user.setPassword(encodedPassword);

        try {
            UserEntity savedUser = userRepository.save(user);
            System.out.println("User saved: " + savedUser);
            return savedUser;
        } catch (Exception e) {
            System.out.println("Error saving user: " + e.getMessage());
            throw new RuntimeException("Error saving user: " + e.getMessage());
        }
    }

    @Override
    public boolean hasCustomeremails(String email) {
        UserEntity user = userRepository.findByEmail(email);
        return user != null && user.getName() != null && !user.getName().isEmpty();
    }
}

