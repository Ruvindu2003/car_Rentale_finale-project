package org.example.config;

import lombok.RequiredArgsConstructor;
import org.example.enums.UserRoles;
import org.example.repository.UserRepository;
import org.example.service.jwt.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.HttpFirewall;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity


public class WebSecurity {
    private final JwtsAuthetcationFilter jwtAuthenticationFilter;
    private final UserRepository userRepository;
    private final UserService userService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/api/auth/signup").permitAll()
                        .requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers("/api/admin/car").permitAll()
                        .requestMatchers("/api/images/upload").permitAll()
                        .requestMatchers("/api/admin/getAll").permitAll()
                        .requestMatchers("api/admin/Delete/{id}").permitAll()
                        .requestMatchers("/api/admin/Search-By-Id/{id}").permitAll()
                        .requestMatchers("/api/admin/Update-By-Car/{id}").permitAll()
                        .requestMatchers("/**").permitAll()
                        .requestMatchers("/api/customer/car/Search").permitAll()
                        .requestMatchers("/api/admin/**").hasAnyAuthority(UserRoles.ADMIN.name())
                        .requestMatchers("/api/user/**").hasAnyAuthority(UserRoles.CUSTOMER.name())
                        .anyRequest().authenticated())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return bCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService.userDetailsService());
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder()); // Set the password encoder
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    public void httpFirewall(HttpFirewall httpFirewall) {

    }
}