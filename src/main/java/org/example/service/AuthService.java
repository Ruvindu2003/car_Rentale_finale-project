package org.example.service;

 // Ensure this import is correct and the class exists in the specified package
import org.example.dto.SingupRequwest;
import org.example.entity.UserEntity;

public interface AuthService {
    UserEntity createCustomer(SingupRequwest signupRequest);
    boolean hasCustomeremails(String email);
}
