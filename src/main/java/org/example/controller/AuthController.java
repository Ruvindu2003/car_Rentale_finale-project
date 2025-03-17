package org.example.controller;

import lombok.RequiredArgsConstructor;

import org.example.dto.AuthoricationRespones;
import org.example.dto.AuthricationRequwest;

import org.example.dto.SingupRequwest;
import org.example.entity.UserEntity;
import org.example.repository.UserRepository;
import org.example.service.AuthService;
import org.example.service.jwt.UserService;

import org.example.utils.JwtUtill;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/auth")

public class AuthController {
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtUtill jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<?> createCustomer(@RequestBody SingupRequwest signupRequest) {
        System.out.println("Received signup request: " + signupRequest);

        if (authService.hasCustomeremails(signupRequest.getEmail())) {
            System.out.println("Email already exists: " + signupRequest.getEmail());
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body("Email already exists. Please use a different one.");
        }

        try {
            UserEntity userEntity = authService.createCustomer(signupRequest);
            System.out.println("User created successfully: " + userEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(userEntity);
        } catch (Exception e) {
            System.out.println("Error creating user: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating user: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthricationRequwest authenticationRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getEmail(),
                            authenticationRequest.getPassword()
                    )
            );
            System.out.println(authenticationRequest.getEmail());
            System.out.println(authenticationRequest.getPassword());

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect username or password.");
        } catch (DisabledException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is disabled.");
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found.");
        }

        final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());
        UserEntity optionalUser = (UserEntity) userRepository.findByEmail(userDetails.getUsername());

        final String jwt=jwtUtil.generateToken(userDetails);
        AuthoricationRespones authenticationResponse = new AuthoricationRespones();
        if (optionalUser.isEnabled()) {
            UserEntity user = optionalUser.get();
            if (user.isEnabled()) {
                authenticationResponse.setJwt(jwt);
                authenticationResponse.setUserid(user.getId());
                authenticationResponse.setUserRoles(user.getRoles());
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is disabled.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        return ResponseEntity.ok(authenticationResponse);
    }
}

















